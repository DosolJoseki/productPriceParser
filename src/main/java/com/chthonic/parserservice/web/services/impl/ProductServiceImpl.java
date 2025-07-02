package com.chthonic.parserservice.web.services.impl;

import com.chthonic.parserservice.web.dto.Product;
import com.chthonic.parserservice.web.services.ProductService;
import com.chthonic.parserservice.web.shops.Shop;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class ProductServiceImpl implements ProductService {
    List<Shop> shops;

    public ProductServiceImpl(List<Shop> shops) {
        this.shops = shops;
    }

    @Override
    public List<Product> getProductsByName(String name) {
        List<CompletableFuture<List<Product>>> futures =
                shops.stream()
                        .map(shop -> shop.getProducts(name))
                        .toList();

        CompletableFuture<Void> allDone = CompletableFuture.allOf(
                futures.toArray(new CompletableFuture[0])
        );

        allDone.join();

        return futures.stream()
                .flatMap(f -> f.join().stream()) // уже безопасно, все завершены
                .toList();
    }
}

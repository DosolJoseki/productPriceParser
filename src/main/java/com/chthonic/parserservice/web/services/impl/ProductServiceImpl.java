package com.chthonic.parserservice.web.services.impl;

import com.chthonic.parserservice.web.dto.Product;
import com.chthonic.parserservice.web.services.ProductService;
import com.chthonic.parserservice.web.shops.Shop;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

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

        List<Product> products = futures.stream()
                .flatMap(f -> f.join().stream())
                .collect(Collectors.toList());

        return products;
    }

    @Override
    public Map<String, List<Product>> getProductsByNameGroupedByName(String name) {
        List<CompletableFuture<List<Product>>> futures =
                shops.stream()
                        .map(shop -> shop.getProducts(name))
                        .toList();

        CompletableFuture<Void> allDone = CompletableFuture.allOf(
                futures.toArray(new CompletableFuture[0])
        );

        allDone.join();

        List<Product> products = futures.stream()
                .flatMap(f -> f.join().stream())
                .collect(Collectors.toList());

        Map<String, List<Product>> productsGroupedByName =
                products.stream().collect(Collectors.groupingBy(Product::getDenormalizedTitle));

        //for testing
        Map<String, List<Product>> aaaa =
                productsGroupedByName.entrySet().stream()
                        .filter(e -> e.getValue().size() > 1)
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        productsGroupedByName.entrySet()
                .forEach(e -> e.setValue(e.getValue().stream()
                        .filter(Product::isAvailable)
                        .sorted(Comparator.comparing(Product::getPrice))
                        .collect(Collectors.toList())
                ));

        productsGroupedByName.entrySet()
                .forEach(e -> e.setValue(
                        e.getValue().stream()
                                .sorted(Comparator.comparing(Product::getPrice))
                                .collect(Collectors.toList())
                ));

        Map<String, List<Product>> sorted = productsGroupedByName.entrySet().stream()
                .filter(e -> e.getValue().size() > 1)
                .sorted(Comparator.comparing(
                        entry -> entry.getValue().getFirst().getPrice()
                ))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1, // merge function (not used here)
                        LinkedHashMap::new // сохраняем порядок сортировки
                ));

//        Map<String, List<Product>> sorted2 =
//                sorted.entrySet()
//                        .stream()
//                        .sorted(Comparator.comparing(e -> e.getValue().size()))
//                        .collect(Collectors.toMap(
//                                Map.Entry::getKey,
//                                Map.Entry::getValue,
//                                (e1, e2) -> e1,
//                                LinkedHashMap::new
//                        ));

        return sorted;
    }
}

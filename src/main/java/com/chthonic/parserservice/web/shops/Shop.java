package com.chthonic.parserservice.web.shops;

import com.chthonic.parserservice.web.dto.Product;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface Shop {
    CompletableFuture<List<Product>> getProducts(String name);
}

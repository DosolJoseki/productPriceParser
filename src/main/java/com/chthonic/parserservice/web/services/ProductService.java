package com.chthonic.parserservice.web.services;

import com.chthonic.parserservice.web.dto.Product;

import java.util.List;
import java.util.Map;

public interface ProductService {
    List<Product> getProductsByName(String name);
    Map<String, List<Product>> getProductsByNameGroupedByName(String name);
}

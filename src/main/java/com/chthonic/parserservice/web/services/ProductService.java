package com.chthonic.parserservice.web.services;

import com.chthonic.parserservice.web.dto.Product;

import java.util.List;

public interface ProductService {
    List<Product> getProductsByName(String name);
}

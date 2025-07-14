package com.chthonic.parserservice.web.rest;

import com.chthonic.parserservice.web.dto.Product;
import com.chthonic.parserservice.web.services.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ProductRestController {
    public ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "get-product")
    public List<Product> getProduct(@RequestParam String productName) {
        return productService.getProductsByName(productName);
    }

    @GetMapping(value = "get-product-grouped-by-name")
    public Map<String, List<Product>> getProductGroupedByName(@RequestParam String productName) {
        return productService.getProductsByNameGroupedByName(productName);
    }
}

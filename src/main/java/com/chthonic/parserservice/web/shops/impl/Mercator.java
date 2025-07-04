package com.chthonic.parserservice.web.shops.impl;

import com.chthonic.parserservice.web.dto.Product;
import com.chthonic.parserservice.web.dto.shops.mercator.Data;
import com.chthonic.parserservice.web.dto.shops.mercator.ProductsDto;
import com.chthonic.parserservice.web.dto.shops.spar.Hit;
import com.chthonic.parserservice.web.dto.shops.spar.ProductResponse;
import com.chthonic.parserservice.web.shops.Shop;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.net.URL;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class Mercator implements Shop {
    private static final String SHOP_NAME = "Mercator";
    private static final URI PRODUCTS_URL = URI.create("https://mercatoronline.si/products/browseProducts/getProducts");

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public Mercator(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
        this.objectMapper = new ObjectMapper();

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public CompletableFuture<List<Product>> getProducts(String name) {
        try {
            return CompletableFuture.supplyAsync(() -> {
                String baseUrl = "https://mercatoronline.si/products/browseProducts/getProducts";
                String url = String.format(
                        "%s?limit=%d&offset=%d&filterData%%5Bsearch%%5D=%s&from=%d",
                        baseUrl,
                        20,
                        1,
                        URLEncoder.encode(name, StandardCharsets.UTF_8),
                        20
                );

                URI uri = URI.create(url);

//                URI uri = UriComponentsBuilder.fromUri(PRODUCTS_URL)
//                        .queryParam("filterData[search]", name)
//                        .queryParam("offset", 1)
//                        .queryParam("limit", 20)
//                        .queryParam("from", 20)
//                        .build(true)
//                        .toUri();

                HttpHeaders headers = new HttpHeaders();
                headers.set("User-Agent", "Mozilla/5.0");
                headers.set("Accept", "application/json");
//                headers.set("Origin", "https://www.spar.si");

                HttpEntity<String> entity = new HttpEntity<>(headers);

                ResponseEntity<String> response =
                        restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

                if (response.getStatusCode() != HttpStatus.OK) {
                    throw new RuntimeException("Failed : HTTP error code : " + response.getStatusCode());
                }
                if (response.getBody() == null || response.getBody().isEmpty()) {
                    throw new RuntimeException("failed: empty body");
                }

                ProductsDto productResponse;
                try {
                    productResponse = objectMapper.readValue(response.getBody(), ProductsDto.class);
                } catch (Exception e) {
                    throw new RuntimeException("Failed : Parsing JSON response : " + response.getBody());
                }

                if (productResponse == null) {
                    throw new RuntimeException("productResponse is null");
                }

                List<Product> products = new ArrayList<>();

                for (com.chthonic.parserservice.web.dto.shops.mercator.Product mercatorProduct: productResponse.products) {
                    products.add(productFromHit(mercatorProduct));
                }

                return products;
            });
        } catch (Exception e) {
            log.error("failed to get products from {}. message: {}", SHOP_NAME, e.getMessage());
            return null;
        }
    }

    Product productFromHit(com.chthonic.parserservice.web.dto.shops.mercator.Product mercatorProduct) {
        Product product = new Product();
        product.setShopName(SHOP_NAME);
        product.setName(mercatorProduct.getData().getName());
        product.setTitle(mercatorProduct.getData().getName());
        product.setPrice(Double.parseDouble(mercatorProduct.getData().getCurrent_price()));
        product.setBrand(mercatorProduct.getData().getBrand_name());
        product.setImage(mercatorProduct.getMainImageSrc());

        return product;
    }
}

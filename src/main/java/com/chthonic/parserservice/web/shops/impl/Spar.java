package com.chthonic.parserservice.web.shops.impl;

import com.chthonic.parserservice.web.dto.shops.spar.Hit;
import com.chthonic.parserservice.web.dto.Product;
import com.chthonic.parserservice.web.dto.shops.spar.ProductResponse;
import com.chthonic.parserservice.web.shops.Shop;
import com.chthonic.parserservice.web.utils.NameNormalizer;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@Slf4j
public class Spar implements Shop {
    private static final String SHOP_NAME = "Spar";
    private static final URI PRODUCTS_URL = URI.create("https://search-spar.spar-ics.com/fact-finder/rest/v4/search/products_lmos_si");

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public Spar(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
        this.objectMapper = new ObjectMapper();

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public CompletableFuture<List<Product>> getProducts(String name) {
        try {
            return CompletableFuture.supplyAsync(() -> {
                URI uri = UriComponentsBuilder.fromUri(PRODUCTS_URL)
                        .queryParam("query", name)
                        .queryParam("page", 1)
                        .queryParam("hitsPerPage", 1000)
                        .queryParam("useAns", "false")
                        .queryParam("substringFilter", "title:!product-number")
                        .queryParam("useAsn", "false")
                        .build()
                        .toUri();

                HttpHeaders headers = new HttpHeaders();
                headers.set("User-Agent", "Mozilla/5.0");
                headers.set("Accept", "application/json");
                headers.set("Origin", "https://www.spar.si");

                HttpEntity<String> entity = new HttpEntity<>(headers);

                ResponseEntity<String> response =
                        restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

                if (response.getStatusCode() != HttpStatus.OK) {
                    throw new RuntimeException("Failed : HTTP error code : " + response.getStatusCode());
                }
                if (response.getBody() == null || response.getBody().isEmpty()) {
                    throw new RuntimeException("failed: empty body");
                }

                ProductResponse productResponse;
                try {
                    productResponse = objectMapper.readValue(response.getBody(), ProductResponse.class);
                } catch (Exception e) {
                    throw new RuntimeException("Failed : Parsing JSON response : " + response.getBody());
                }

                if (productResponse == null) {
                    throw new RuntimeException("productResponse is null");
                }

                List<Product> products = new ArrayList<>();

                //for tests
//                var sdfsdfsdf =
//                        productResponse.hits.stream().filter(e->e.masterValues.name != null).filter(e -> e.masterValues.name.equals("SUN KISS MLEKO KIDS ZF50 200ML")).collect(Collectors.toSet());

                for (Hit hit: productResponse.hits) {
                    products.add(productFromHit(hit));
                }

                return products;
            });
        } catch (Exception e) {
            log.error("failed to get products from {}. message: {}", SHOP_NAME, e.getMessage());
            return null;
        }
    }

    Product productFromHit(Hit hit) {
        Product product = new Product();
        product.setShopName(SHOP_NAME);
        product.setName(hit.masterValues.name);
        product.setTitle(hit.masterValues.title);
        product.setPrice(hit.masterValues.price);
        product.setBrand(hit.masterValues.ecrBrand);
        product.setImage(hit.masterValues.imageUrl);
        product.setAvailable(Objects.equals(hit.masterValues.stockStatus, "inStock"));
        NameNormalizer.updateProductName(product);

        return product;
    }
}

package com.chthonic.parserservice.web.dto.shops.spar;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;

@Data
public class MasterValues implements Serializable {
    @JsonProperty("is-on-promotion")
    public boolean isOnPromotion;

    @JsonProperty("category-names")
    public String categoryNames;

    @JsonProperty("badge-icon")
    public String badgeIcon;

    @JsonProperty("description")
    public String description;

    @JsonProperty("sales-unit")
    public String salesUnit;

    @JsonProperty("title")
    public String title;

    @JsonProperty("badge-names")
    public String badgeNames;

    @JsonProperty("item-type")
    public String itemType;

    @JsonProperty("code-internal")
    public String codeInternal;

    @JsonProperty("category-name")
    public String categoryName;

    @JsonProperty("allergens-filter")
    public ArrayList<String> allergensFilter;

    @JsonProperty("price")
    public double price;

    @JsonProperty("badge-short-name")
    public String badgeShortName;

    @JsonProperty("created-at")
    public String createdAt;

    @JsonProperty("categories")
    public ArrayList<String> categories;

    @JsonProperty("fat-content-filter")
    public String fatContentFilter;

    @JsonProperty("best-price")
    public double bestPrice;

    @JsonProperty("category-path")
    public ArrayList<ArrayList<String>> categoryPath;

    @JsonProperty("short-description-2")
    public String shortDescription2;

    @JsonProperty("ecr-category-number")
    public String ecrCategoryNumber;

    @JsonProperty("stock-status")
    public String stockStatus;

    @JsonProperty("is-new")
    public String isNew;

    @JsonProperty("image-url")
    public URL imageUrl;

    @JsonProperty("category-id")
    public String categoryId;

    @JsonProperty("short-description")
    public String shortDescription;

    @JsonProperty("approx-weight-product")
    public String approxWeightProduct;

    @JsonProperty("url")
    public String url;

    @JsonProperty("ecr-brand")
    public String ecrBrand;

    @JsonProperty("pos-visible")
    public ArrayList<String> posVisible;

    @JsonProperty("name")
    public String name;

    @JsonProperty("__FFCampaign__")
    public String ffCampaign;

    @JsonProperty("product-number")
    public String productNumber;

    @JsonProperty("price-per-unit")
    public String pricePerUnit;

    @JsonProperty("regular-price")
    public double regularPrice;

    @JsonProperty("price-per-unit-number")
    public double pricePerUnitNumber;

    @JsonProperty("pos-purchasable")
    public String posPurchasable;
}

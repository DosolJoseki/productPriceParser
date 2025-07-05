package com.chthonic.parserservice.web.dto.shops.mercator;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@lombok.Data
public class Data {
    private String cinv;
    private String code;
    private String codewz;
    private String name;
    private String unit_quantity;
    private String invoice_unit;
    private String invoice_unit_type;
    private double average_weight;
    private Object normal_price;
    private String current_price;
    private String pc30_price;
    private double price_per_unit;
    private String price_per_unit_base;
    private String eko;
    private String has_recipes;
    private String brand_name;
    private ArrayList<Gtin> gtins;
    private ArrayList<Allergen> allergens;
    private ArrayList<Discount> discounts;
    private String ratings_sum;
    private String ratings_num;
    private int rating;
    @JsonProperty("package")
    private int mypackage;
    private String offer_expires_on;
    private String category1;
    private String category2;
    private String category3;
    private String analyticsObject;
    private int personal_offer_recommendation_id;


    public static class Allergen{
        public String value;
        public String hover_text;
    }

    public static class Discount{
        public String discount_id;
        public String group_type;
        public String value;
        public String marker_code;
        public String valid_from;
        public String valid_to;
        public double discount_price;
        public String clubs;
        public Object clubs_array;
        public String coupon;
        public String hover_text;
        public Object sort;
    }

    public static class Gtin{
        public String gtin;
    }

    public static class Meta{
        public double es_score;
        public String mc_score;
    }
}

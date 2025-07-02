package com.chthonic.parserservice.web.dto.shops.mercator;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Data {
    public String cinv;
    public String code;
    public String codewz;
    public String name;
    public String unit_quantity;
    public String invoice_unit;
    public String invoice_unit_type;
    public int average_weight;
    public Object normal_price;
    public String current_price;
    public String pc30_price;
    public double price_per_unit;
    public String price_per_unit_base;
    public String eko;
    public String has_recipes;
    public String brand_name;
    public ArrayList<Gtin> gtins;
    public ArrayList<Allergen> allergens;
    public ArrayList<Discount> discounts;
    public String ratings_sum;
    public String ratings_num;
    public int rating;
    @JsonProperty("package")
    public int mypackage;
    public String offer_expires_on;
    public String category1;
    public String category2;
    public String category3;
    public String analyticsObject;
    public int personal_offer_recommendation_id;


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

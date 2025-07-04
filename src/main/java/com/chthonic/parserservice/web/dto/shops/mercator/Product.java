package com.chthonic.parserservice.web.dto.shops.mercator;

import java.net.URL;

@lombok.Data
public class Product{
    private Data data;
    private String itemId;
    private String className;
    private String type;
    private URL mainImageSrc;
    private Data.Meta _meta;
    private String url;
    private String short_name;
    private int total;
    private int ordNum;
}

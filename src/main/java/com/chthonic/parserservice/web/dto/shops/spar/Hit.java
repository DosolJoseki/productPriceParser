package com.chthonic.parserservice.web.dto.shops.spar;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Hit {
    @SerializedName("masterValues")
    public MasterValues masterValues;
    public ArrayList<Object> variantValues;
    public String id;
    public double score;
    public int position;
    public ArrayList<Object> foundWords;
}

package com.chthonic.parserservice.web.dto.shops.spar;

import java.io.Serializable;
import java.util.ArrayList;


public class ProductResponse implements Serializable {
    public SearchControlParams searchControlParams;
    public int totalHits;
    public boolean articleNumberSearch;
    public double scoreFirstHit;
    public double scoreLastHit;
    public ArrayList<Hit> hits;
    public ArrayList<Filter> filters;
    public int tookTotal;
    public int tookWorldmatch;
    public int tookLoop54;
    public int tookAtlasAi;
    public int tookGPTSynonyms;
    public boolean timedOut;
    public boolean splitDocuments;
    public FieldRoles fieldRoles;
    public ArrayList<Object> answers;
    public ArrayList<BreadCrumbTrail> breadCrumbTrail;
    public ArrayList<Campaign> campaigns;
    public ArrayList<Object> facets;
    public Geo geo;
    public Paging paging;
    public SearchParams searchParams;
    public ArrayList<Object> singleWordResults;
    public ArrayList<SortItem> sortItems;



    public static class SearchControlParams {
        public boolean useAtlasAi;
        public boolean useAsn;
    }

    public static class SearchParams {
        public String query;
        public int hitsPerPage;
        public boolean showPermutedSearchParams;
        public String channel;
        public ArrayList<Filter> filters;
        public int page;
        public ArrayList<SortItem> sortItems;
    }

    public static class SortItem {
        public String order;
        public String name;
        public String description;
        public boolean selected;
        public SearchParams searchParams;
    }

    public static class Target {
        public String destination;
        public String name;
    }

    public static class Value {
        public String value;
        public String type;
        public boolean exclude;
    }

    public static class BreadCrumbTrail {
        public String type;
        public String text;
        public String value;
        public SearchParams searchParams;
    }

    public static class Campaign {
        public String id;
        public Target target;
        public String name;
        public String category;
        public String flavour;
        public boolean excludeProductsNotInMarkets;
        public boolean excludeProductsNotInRange;
        public ArrayList<FeedbackText> feedbackTexts;
        public boolean ad;
        public ArrayList<Object> activeQuestions;
        public ArrayList<Object> advisorTree;
        public ArrayList<Object> hits;
    }

    public static class FeedbackText {
        public String text;
        public String label;
        public boolean html;
        public boolean teaser;
        public int position;
    }

    public static class FieldRoles {
        public String brand;
        public String deeplink;
        public String description;
        public String imageUrl;
        public String price;
        public String productName;
        public String productNumber;
    }

    public static class Filter {
        public String name;
        public ArrayList<Value> values;
        public boolean substring;
    }

    public static class Geo {
        public ArrayList<Object> markets;
        public ArrayList<Object> selectedMarkets;
        public boolean showDistance;
    }


    public static class NextLink {
        public int number;
        public boolean currentPage;
        public SearchParams searchParams;
    }

    public static class Paging {
        public int currentPage;
        public int pageCount;
        public int hitsPerPage;
        public int defaultHitsPerPage;
        public NextLink nextLink;
    }
}










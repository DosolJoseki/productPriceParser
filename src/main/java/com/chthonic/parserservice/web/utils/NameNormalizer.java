package com.chthonic.parserservice.web.utils;

import com.chthonic.parserservice.web.dto.Product;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NameNormalizer {
    public static void updateProductName(Product product) {
        String title = product.getTitle();
        title = title.replaceAll("_", "");
        title = title.replaceAll("(?<=\\p{L}),", "");
        title = updateWithRegex(title, "(\\d+)\\s+[xX]\\s+(\\d+)\\s+[lL]", "$1x$2L");
        title = updateWithRegex(title, "(\\d+)\\s+[lL]", "$1L");
        title = updateWithRegex(title, "(\\d+)\\s+%", "$1%");
        title = updateWithRegex(title,"(\\d+)\\s+(g|ml|l)", "$1$2");

        product.setTitle(title);
        normalizeProductName(product);
    }

    private static String updateWithRegex(String title, String regex, String replacement) {
        Pattern r = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = r.matcher(title);

        return m.replaceAll(replacement);
    }

    private static void normalizeProductName(Product product) {
        String name = product.getTitle();
        name = name.toLowerCase();
        String[] words = name.trim().split("\\s+");
        Arrays.sort(words);
        product.setDenormalizedTitle(String.join(" ", words));
    }
}

package com.bloomingbread.core.nlp.utils;

import edu.stanford.nlp.ling.CoreLabel;

public class CoreLabelUtils {
    public static void printClasses(CoreLabel token) {
        token.keySet().forEach(c -> System.out.println(c.toString()));
    }
}
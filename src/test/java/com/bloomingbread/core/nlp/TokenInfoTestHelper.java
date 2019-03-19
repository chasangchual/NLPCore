package com.bloomingbread.core.nlp;

import edu.stanford.nlp.ling.CoreLabel;

public class TokenInfoTestHelper {
    public static void showTokenInfo(CoreLabel token) {
        System.out.println(String.format("[%d] %s, lemma: %s, tag: %s, ner: %s\n", token.index(), token.value(), token.lemma(), token.tag(), token.ner()));
    }
}

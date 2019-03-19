package com.bloomingbread.core.nlp.pipeline;

import edu.stanford.nlp.pipeline.Annotator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public interface CustomAnnotator extends Annotator {
    String CUSTOM_STOPWORD = "stopword";

    Map<String, Set<String>> CUSTOM_REQUIREMENTS = new HashMap<String, Set<String>>(){{
        put(CUSTOM_STOPWORD, new LinkedHashSet<>(Arrays.asList(STANFORD_TOKENIZE, STANFORD_SSPLIT, STANFORD_POS, STANFORD_LEMMA, STANFORD_NER)));
    }};
}

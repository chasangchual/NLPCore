package com.bloomingbread.core.nlp.tools.ngram;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * manages ngrams for each ngrms
 */
public class NGramSet {
    Map<Integer, NGram> ngrams = Maps.newHashMap();

    public NGramSet() {

    }

    public boolean hasNGram(final int n) {
        return ngrams.containsKey(n);
    }

    public void add(final NGram ngram) {
        ngrams.compute(ngram.getSize(), (k, v) -> v == null ? ngram : appendTo(v));
    }

    public NGram getNGram(final int n) {
        if(ngrams.containsKey(n)) {
            return ngrams.get(n);
        } else {
            throw new IllegalArgumentException(String.format("requested ngram, %d, does not exists."));
        }
    }

    private NGram appendTo(final NGram ngram) {
        Preconditions.checkArgument(ngrams.containsKey(ngram.getSize()), "specified ngram size does not exist");

        NGram found = ngrams.get(ngram.getSize());
        found.add(ngram);
        ngrams.put(ngram.getSize(), found);
        return found;
    }
}
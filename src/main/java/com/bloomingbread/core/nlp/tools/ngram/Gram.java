package com.bloomingbread.core.nlp.tools.ngram;

import com.google.common.base.Preconditions;

import java.util.List;
import java.util.Vector;

/**
 * Basically Gram represents words set in the sentence. In accordance with the word count, it could be called 2 gram,
 * 3 gram, 4 gram, etc.
 */
public class Gram {
    private List<String> grams = new Vector<>();
    private String gramStr = "";

    public Gram() {
    }

    /**
     * create Gram object with the first word
     * @param first first word
     */
    public Gram(final String first) {
        grams.add(first);
        gramStr = first;
    }

    public int size() {
        return grams.size();
    }

    public void add(final String word) {
        grams.add(word);
    }

    public String getWord(final int pos) {
        Preconditions.checkPositionIndexes();
        if(pos >= 0 && pos < size()) {
            return grams.get(pos);
        } else {
            throw new IllegalArgumentException(String.format(
                    "given position is out of the range[%d (inclusive), %d (exclusive)]",0, size()));
        }
    }

    @Override
    public String toString() {
        return toString(" ");
    }

    public String toString(final CharSequence delimiter) {
        return String.join(delimiter, grams);
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof Gram)) return false;
        Gram gram = (Gram)obj;
        return gram.hashCode() == this.hashCode();
    }
}

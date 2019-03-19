package com.bloomingbread.core.nlp.tools.ngram;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Spliterator;
import java.util.Vector;
import java.util.function.Consumer;

public class NGram implements Iterable<ImmutablePair<Gram, Integer>> {
    private static Integer MAX_N = 10;

    private Map<Gram, Integer> ngram = new HashMap<>();
    private int n = 0;

    public NGram(final int n) {
        Preconditions.checkArgument(n > 0 && n < MAX_N);
        this.n = n;
    }

    public Integer getSize() {
        return n;
    }

    public Integer add(final Gram gram) {
        if(gram.size() == n) {
            ngram.compute(gram, (k, v) -> v != null ? v+1 : 1);
            return ngram.get(gram);
        } else {
            throw new IllegalArgumentException("gram size does not match.");
        }
    }

    public Integer add(final ImmutablePair<Gram, Integer> pair) {
        if(pair.left.size() == n) {
            ngram.put(pair.left, pair.right);
            return pair.right;
        } else {
            throw new IllegalArgumentException("gram size does not match.");
        }
    }

    public void add(final NGram ngram) {
        Iterator<ImmutablePair<Gram, Integer>> itr = ngram.iterator();
        while(itr.hasNext()) {
            add(itr.next());
        }
    }

    public void add(final List<Gram> grams) {
        grams.forEach(gram -> add(gram));
    }

    public Integer get(final Gram gram) {
        Preconditions.checkArgument(ngram.containsKey(gram), "specified gram is not found.");
        return ngram.get(gram);
    }

    @Override
    public Iterator<ImmutablePair<Gram, Integer>> iterator() {
        return new GramIterator(ngram);
    }

    @Override
    public void forEach(Consumer<? super ImmutablePair<Gram, Integer>> action) {
        ngram.entrySet().forEach(e -> action.accept(ImmutablePair.of(e.getKey(), e.getValue())));
    }

    @Override
    public Spliterator<ImmutablePair<Gram, Integer>> spliterator() {
        return null;
    }

    class GramIterator implements Iterator<ImmutablePair<Gram, Integer>> {
        Vector<ImmutablePair<Gram, Integer>> grams = new Vector<>();
        int ptr = 0;

        public GramIterator(final Map<Gram, Integer> ngram) {
            ngram.entrySet().forEach(e -> grams.add(ImmutablePair.of(e.getKey(), e.getValue())));
            ptr = 0;
        }

        @Override
        public boolean hasNext() {
            return grams != null && ptr < grams.size();
        }

        @Override
        public ImmutablePair<Gram, Integer> next() {
            if(ptr < grams.size()) {
                return grams.get(ptr++);
            }
            return null;
        }
    }
}

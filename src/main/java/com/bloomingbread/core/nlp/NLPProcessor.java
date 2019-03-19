package com.bloomingbread.core.nlp;

import com.bloomingbread.core.nlp.annotator.StopWordAnnotator;
import com.bloomingbread.core.nlp.pipeline.PipelineFactory;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.Annotator;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

public class NLPProcessor {
    protected final StanfordCoreNLP nlpPipeline;

    private NLPProcessor(final StanfordCoreNLP nlpPipeline, Collection<Annotator> customAnnotators) {
        this.nlpPipeline = nlpPipeline;
        for(Annotator annotator : customAnnotators) {
            this.nlpPipeline.addAnnotator(annotator);
        }
    }

    public final StanfordCoreNLP getPipeline() {
        return nlpPipeline;
    }

    public static Collection<Annotator> getEmptyAnnotatorCollection() {
        return Collections.EMPTY_SET;
    }

    public static Collection<Annotator> newAnnotatorCollection(Annotator ... annotators) {
        return new HashSet<Annotator>(Arrays.asList(annotators));
    }

    public static Collection<Annotator> newAnnotatorCollection(final Collection appendToAnnotatorCollection,
                                                               final Annotator ... annotators) {
        Collection<Annotator> annotatorCollection =  new HashSet<Annotator>(appendToAnnotatorCollection);
        annotatorCollection.addAll(Arrays.asList(annotators));
        return annotatorCollection;
    }

    public static StopWordAnnotator newStopWordAnnotator() {
        return new StopWordAnnotator();
    }

    public static StopWordAnnotator newStopWordAnnotator(final String stopWords, final boolean ignoreCase) {
        Properties properties = new Properties();
        properties.setProperty(StopWordAnnotator.STOPWORDS_LIST, stopWords);
        properties.setProperty(StopWordAnnotator.IGNORE_STOPWORD_CASE, String.valueOf(ignoreCase));
        return new StopWordAnnotator(properties);
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static class Builder {
        String annotator = Annotator.STANFORD_POS;
        boolean applyStopWord = false;

        public Builder withAnnotator(final String annotator) {
            if(!Annotator.DEFAULT_REQUIREMENTS.containsKey(annotator)) {
                throw new IllegalArgumentException(String.format("given annotator %s is invalid.", annotator));
            }
            this.annotator = annotator;
            return this;
        }

        public Builder applyStopWord() {
            return applyStopWord(true);
        }

        public Builder applyStopWord(final boolean applyStopWord) {
            this.applyStopWord = applyStopWord;
            return this;
        }

        public NLPProcessor build() {
            NLPProcessor processor = new NLPProcessor(PipelineFactory.newPileLine(annotator),
                    applyStopWord ? newAnnotatorCollection(newStopWordAnnotator()) : newAnnotatorCollection());
            return processor;
        }
    }
}
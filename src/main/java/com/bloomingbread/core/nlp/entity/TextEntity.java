package com.bloomingbread.core.nlp.entity;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.util.CoreMap;

import java.util.List;

public class TextEntity {
    private final Annotation textAnnotation;
    private List<CoreMap> sentences = null;
    /**
     * applicable textAnnotation class
     *
     * edu.stanford.nlp.ling.CoreAnnotations$TextEntity
     * edu.stanford.nlp.ling.CoreAnnotations$SentencesAnnotation
     * edu.stanford.nlp.ling.CoreAnnotations$TokensAnnotation
     * @param textAnnotation
     */
    public TextEntity(final Annotation textAnnotation) {
        this.textAnnotation = textAnnotation;
    }

    public String getText() {
        return textAnnotation.get(CoreAnnotations.TextAnnotation.class);
    }

    public int sentenceCount() {
        loadCoreMapsIfNot();
        return sentences.size();
    }

    public SentenceEntity getSentence(final int sentenceIdx) {
        loadCoreMapsIfNot();
        validateSentenceIndex(sentenceIdx);
        return new SentenceEntity(sentences.get(sentenceIdx));
    }

    private synchronized void loadCoreMapsIfNot() {
        if(sentences == null) {
            sentences = textAnnotation.get(CoreAnnotations.SentencesAnnotation.class);
        }
    }

    private void validateSentenceIndex(final int sentenceIdx) {
        if(sentenceIdx < 0 || sentenceIdx >= sentences.size())
            throw new IndexOutOfBoundsException(String.format("sentenceIdx is out of range. sentenceIdx: %d, " +
                    "range: (%d %d)",sentenceIdx, 0, sentences.size()-1));
    }

//    public List<CoreMap> getSentenceMap() {
//        return textAnnotation.get(CoreAnnotations.SentencesAnnotation.class);
//    }
//
//    public List<CoreLabel> getTokenMap() {
//        return textAnnotation.get(CoreAnnotations.TokensAnnotation.class);
//    }
}

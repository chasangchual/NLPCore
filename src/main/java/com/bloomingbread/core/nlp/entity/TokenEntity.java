package com.bloomingbread.core.nlp.entity;

import com.bloomingbread.core.nlp.annotator.coreannotator.StopWordCoreAnnotator;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.util.Pair;

public class TokenEntity {
    final CoreLabel coreLabel;

    /**
     *
     * available annotation classes
     *
     * edu.stanford.nlp.ling.CoreAnnotations$ValueAnnotation
     * edu.stanford.nlp.ling.CoreAnnotations$TextEntity
     * edu.stanford.nlp.ling.CoreAnnotations$OriginalTextAnnotation
     * edu.stanford.nlp.ling.CoreAnnotations$CharacterOffsetBeginAnnotation
     * edu.stanford.nlp.ling.CoreAnnotations$CharacterOffsetEndAnnotation
     * edu.stanford.nlp.ling.CoreAnnotations$BeforeAnnotation
     * edu.stanford.nlp.ling.CoreAnnotations$AfterAnnotation
     * edu.stanford.nlp.ling.CoreAnnotations$IsNewlineAnnotation
     * edu.stanford.nlp.ling.CoreAnnotations$TokenBeginAnnotation
     * edu.stanford.nlp.ling.CoreAnnotations$TokenEndAnnotation
     * edu.stanford.nlp.ling.CoreAnnotations$IndexAnnotation
     * edu.stanford.nlp.ling.CoreAnnotations$SentenceIndexAnnotation
     * edu.stanford.nlp.ling.CoreAnnotations$PartOfSpeechAnnotation
     * edu.stanford.nlp.ling.CoreAnnotations$LemmaAnnotation
     *
     * @param coreLabel
     */
    public TokenEntity(final CoreLabel coreLabel) {
        this.coreLabel = coreLabel;
    }

    protected CoreLabel getCoreLabel() {
        return coreLabel;
    }

    public String getWord() {
        return coreLabel.word();
    }

    public String getLemma() {
        return coreLabel.lemma();
    }

    public String getNer() {
        return coreLabel.ner();
    }

    public String getPos() {
        return coreLabel.get(CoreAnnotations.PartOfSpeechAnnotation.class);
    }

    public String getTag() {
        return coreLabel.tag();
    }

    public boolean isStopWord() {
        Pair<Boolean, Boolean> isStopWord = coreLabel.get(StopWordCoreAnnotator.class);
        return isStopWord.first();
    }

    public boolean isStopLemma() {
        Pair<Boolean, Boolean> isStopWord = coreLabel.get(StopWordCoreAnnotator.class);
        return isStopWord.second();
    }
}

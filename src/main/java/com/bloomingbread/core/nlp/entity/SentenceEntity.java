package com.bloomingbread.core.nlp.entity;

import com.bloomingbread.core.nlp.NLPConfig;
import com.bloomingbread.core.nlp.annotator.coreannotator.StopWordCoreAnnotator;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SentenceEntity {
    /**
     * available annotations fore CoreMap
     *
     * edu.stanford.nlp.ling.CoreAnnotations$TextEntity
     * edu.stanford.nlp.ling.CoreAnnotations$CharacterOffsetBeginAnnotation
     * edu.stanford.nlp.ling.CoreAnnotations$CharacterOffsetEndAnnotation
     * edu.stanford.nlp.ling.CoreAnnotations$TokensAnnotation
     * edu.stanford.nlp.ling.CoreAnnotations$SentenceIndexAnnotation
     * edu.stanford.nlp.ling.CoreAnnotations$TokenBeginAnnotation
     * edu.stanford.nlp.ling.CoreAnnotations$TokenEndAnnotation
     * @param coreMap
     */

    private final CoreMap coreMap;
    private List<CoreLabel> tokens = null;
    private edu.stanford.nlp.simple.Sentence stanfordSentence = null;
//    private Tree parseTree = null;

    public SentenceEntity(final CoreMap coreMap) {
        this.coreMap = coreMap;
//        sentence = new edu.stanford.nlp.simple.Sentence(getString(), getDefaultSentenceProperties());
    }

//    public CoreMap getCoreMap() {
//        return coreMap;
//    }

    public String getString() {
        return coreMap.get(TextAnnotation.class);
    }

    public int getSentenceIndex() {
        return coreMap.get(CoreAnnotations.SentenceIndexAnnotation.class);
    }

    public int getTokenCount() {
        loadCoreMapsIfNot();
        return tokens.size();
    }

    public List<TokenEntity> getTokenEntities() {
        List<TokenEntity> entities = new ArrayList<>();
        loadCoreMapsIfNot();

        tokens.forEach(token -> entities.add(new TokenEntity(token)));
        return entities;
    }

    public List<TokenEntity> getTokenEntitiesEmitStopWord() {
        List<TokenEntity> entities = new ArrayList<>();
        loadCoreMapsIfNot();

        try {
            tokens.forEach(token -> {
                Pair<Boolean, Boolean> isStopWord = token.get(StopWordCoreAnnotator.class);
                if(!isStopWord.first()) {
                    entities.add(new TokenEntity(token));
                }
            });
        } catch (NullPointerException ex) {
            throw new IllegalStateException("failed to load stop word annotator.");
        }

        return entities;
    }

    public List<TokenEntity> getTokenEntitiesEmitStopLemma() {
        List<TokenEntity> entities = new ArrayList<>();
        loadCoreMapsIfNot();

        try {
            tokens.forEach(token -> {
                Pair<Boolean, Boolean> isStopWord = token.get(StopWordCoreAnnotator.class);
                if(!isStopWord.second()) {
                    entities.add(new TokenEntity(token));
                }
            });
        } catch (NullPointerException ex) {
            throw new IllegalStateException("failed to load stop word annotator.");
        }

        return entities;
    }

    public TokenEntity getTokenEntity(final int tokenIndex) {
        loadCoreMapsIfNot();
        validateTokenIndex(tokenIndex);
        return new TokenEntity(tokens.get(tokenIndex));
    }

    private synchronized void loadCoreMapsIfNot() {
        if(tokens == null) {
            tokens = coreMap.get(CoreAnnotations.TokensAnnotation.class);
        }
    }

    private void validateTokenIndex(final int tokenIndex) {
        if(tokenIndex < 0 || tokenIndex >= tokens.size())
            throw new IndexOutOfBoundsException(String.format("tokenIndex is out of range. tokenIndex: %d, " +
                    "range: (%d %d)",tokenIndex, 0, tokens.size()-1));

    }

    public Tree getParseTree() {
        Tree parseTree = null;
        synchronized (coreMap) {
            if(stanfordSentence == null) {
                stanfordSentence = new edu.stanford.nlp.simple.Sentence(getString(), getDefaultSentenceProperties());
            }
            parseTree = stanfordSentence.parse();
        }
        return parseTree;
    }

    private Properties getDefaultSentenceProperties() {
        Properties properties = new Properties();
        properties.setProperty("parse.maxlen", String.valueOf(NLPConfig.getConfig().getParseWordCount()));
        return properties;
    }
}

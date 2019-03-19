package com.bloomingbread.core.nlp.entity;

import com.bloomingbread.core.nlp.NLPProcessor;
import com.bloomingbread.core.nlp.TestCorpusProvider;
import edu.stanford.nlp.pipeline.Annotator;
import edu.stanford.nlp.trees.Tree;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class SentenceEntityTest {
    TextEntity textEntity;

    @Before
    public void setUp() {
        textEntity = TextEntityBuilder.builder()
                .withNLPTool(NLPProcessor.getBuilder().withAnnotator(Annotator.STANFORD_POS).build())
                .withText(TestCorpusProvider.getStackOverflowSentence())
                .build();
    }

    @Test
    /**
     * Expecting below 9 words from getTokenEntity()
     * You
     * could
     * create
     * a
     * custom
     * serializer
     * for
     * serializing
     * Foo
     * objects
     * .
     */
    public void getTokenEntity() throws Exception {
        SentenceEntity sentenceEntity = textEntity.getSentence(0);
        assertEquals(11, sentenceEntity.getTokenCount());
        assertEquals("could", sentenceEntity.getTokenEntity(1).getWord());
    }

    @Test
    public void getTokenEntities() throws Exception {
        SentenceEntity sentenceEntity = textEntity.getSentence(0);
        List<TokenEntity> tokenEntities = sentenceEntity.getTokenEntities();

        assertEquals(tokenEntities.size(), sentenceEntity.getTokenCount());
        assertEquals(tokenEntities.get(1).getWord(), sentenceEntity.getTokenEntity(1).getWord());
    }

    @Test
    /**
     * Expecting below 9 words from getTokenEntitiesEmitStopWord()
     * You
     * could
     * create
     * custom
     * serializer
     * serializing
     * Foo
     * objects
     * .
     */
    public void getTokenEntitiesEmitStopWord() throws Exception {
        TextEntity textEntity = TextEntityBuilder.builder()
                .withNLPTool(NLPProcessor.getBuilder()
                        .withAnnotator(Annotator.STANFORD_POS)
                        .applyStopWord(true)
                        .build())
                .withText(TestCorpusProvider.getStackOverflowSentence())
                .build();

        SentenceEntity sentenceEntity = textEntity.getSentence(0);
        List<TokenEntity> tokenEntities = sentenceEntity.getTokenEntitiesEmitStopWord();
//        tokenEntities.forEach(e -> System.out.println(e.getWord()));
        assertEquals(9, tokenEntities.size());
        assertEquals("could", tokenEntities.get(1).getWord());
    }

    @Test
    public void getTokenEntitiesEmitStopWord_AllStopWord() throws Exception {
        TextEntity textEntity = TextEntityBuilder.builder()
                .withNLPTool(NLPProcessor.getBuilder()
                        .withAnnotator(Annotator.STANFORD_POS)
                        .applyStopWord(true)
                        .build())
                .withText(TestCorpusProvider.getAllStopwordString())
                .build();

        SentenceEntity sentenceEntity = textEntity.getSentence(0);
        List<TokenEntity> tokenEntities = sentenceEntity.getTokenEntitiesEmitStopWord();
        assertEquals(0, tokenEntities.size());
    }

    @Test(expected = IllegalStateException.class)
    public void getTokenEntitiesEmitStopWord_IllegalStateException() throws Exception {
        SentenceEntity sentenceEntity = textEntity.getSentence(0);
        List<TokenEntity> tokenEntities = sentenceEntity.getTokenEntitiesEmitStopWord();
        fail("it should throw IllegalStateException exception.");
    }

    @Test
    /**
     * Expecting below 9 words from getTokenEntitiesEmitStopLemma()
     * You
     * could
     * create
     * custom
     * serializer
     * serializing
     * Foo
     * objects
     * .
     */
    public void getTokenEntitiesEmitStopLemma() throws Exception {
        TextEntity textEntity = TextEntityBuilder.builder()
                .withNLPTool(NLPProcessor.getBuilder()
                        .withAnnotator(Annotator.STANFORD_POS)
                        .applyStopWord(true)
                        .build())
                .withText(TestCorpusProvider.getStackOverflowSentence())
                .build();

        SentenceEntity sentenceEntity = textEntity.getSentence(0);
        List<TokenEntity> tokenEntities = sentenceEntity.getTokenEntitiesEmitStopLemma();
//        tokenEntities.forEach(e -> System.out.println(e.getWord()));
        assertEquals(9, tokenEntities.size());
        assertEquals("could", tokenEntities.get(1).getWord());
    }

    @Test
    public void getTokenEntitiesEmitStopLemma_AllStopWord() throws Exception {
        TextEntity textEntity = TextEntityBuilder.builder()
                .withNLPTool(NLPProcessor.getBuilder()
                        .withAnnotator(Annotator.STANFORD_POS)
                        .applyStopWord(true)
                        .build())
                .withText(TestCorpusProvider.getAllStopwordString())
                .build();

        SentenceEntity sentenceEntity = textEntity.getSentence(0);
        List<TokenEntity> tokenEntities = sentenceEntity.getTokenEntitiesEmitStopLemma();
        assertEquals(0, tokenEntities.size());
    }

    @Test
    public void getParseTree() throws Exception {
        SentenceEntity sentenceEntity = textEntity.getSentence(0);
        printTree(sentenceEntity.getParseTree());
    }

    private void printTree(final Tree tree) {
        System.out.println(tree);
        for(Tree child : tree.children()) {
            printTree(child);
        }
    }
}
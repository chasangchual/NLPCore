package com.bloomingbread.core.nlp.entity;

import com.bloomingbread.core.nlp.NLPProcessor;
import com.bloomingbread.core.nlp.TestCorpusProvider;
import edu.stanford.nlp.pipeline.Annotator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TextEntityTest {
    TextEntity textEntity;

    @Before
    public void setUp() {
        textEntity = TextEntityBuilder.builder()
                .withNLPTool(NLPProcessor.getBuilder().withAnnotator(Annotator.STANFORD_POS).build())
                .withText(TestCorpusProvider.getNYTimesArticle())
                .build();
    }

    @Test
    public void getText() throws Exception {
        assertEquals(TestCorpusProvider.getNYTimesArticle().length(), textEntity.getText().length());
    }

    @Test
    public void getSentenceMap() throws Exception {
        assertEquals(26, textEntity.sentenceCount());
    }

    @Test
    public void getSentence() throws Exception {
        for(int i = 0 ; i < textEntity.sentenceCount(); i++) {
            assertNotNull(textEntity.getSentence(i).getString());
        }
    }
}
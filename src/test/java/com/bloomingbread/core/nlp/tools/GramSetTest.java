package com.bloomingbread.core.nlp.tools;

import com.bloomingbread.core.nlp.NLPProcessor;
import com.bloomingbread.core.nlp.entity.TextEntity;
import com.bloomingbread.core.nlp.entity.TextEntityBuilder;
import com.bloomingbread.core.nlp.tools.ngram.Gram;
import com.bloomingbread.core.nlp.tools.ngram.NGram;
import com.bloomingbread.core.nlp.tools.ngram.NGramSet;
import com.bloomingbread.core.nlp.tools.ngram.NGramTool;
import edu.stanford.nlp.pipeline.Annotator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GramSetTest {
    String text = "x y x y x y";
    TextEntity textEntity = null ;

    @Before
    public void setUp() {
        textEntity = TextEntityBuilder.builder()
                        .withNLPTool(NLPProcessor.getBuilder().withAnnotator(Annotator.STANFORD_POS).applyStopWord().build())
                        .withText(text)
                        .build();;
    }

    @Test
    public void testAdd() {
        NGramSet nGramSet = new NGramSet();

        for (int i = 0; i < textEntity.sentenceCount(); i++) {
            NGram ngram = new NGram(2);
            ngram.add(NGramTool.getNGram(textEntity.getSentence(i), 2));
            nGramSet.add(ngram);
        }
        // represent "x y"
        Gram gramXY = new Gram("x");
        gramXY.add("y");

        // represent "y x"
        Gram gramYX = new Gram("y");
        gramYX.add("x");

        assertEquals(3, nGramSet.getNGram(2).get(gramXY).intValue());
        assertEquals(2, nGramSet.getNGram(2).get(gramYX).intValue());
    }

    @Test
    public void testAddToNGramSet() {
        NGramSet nGramSet = new NGramSet();

        for (int i = 0; i < textEntity.sentenceCount(); i++) {
            NGramTool.getNGram(textEntity.getSentence(i), 2, nGramSet);
        }
        // represnet "x y"
        Gram gramXY = new Gram("x");
        gramXY.add("y");

        // represnet "y x"
        Gram gramYX = new Gram("y");
        gramYX.add("x");

        assertEquals(3, nGramSet.getNGram(2).get(gramXY).intValue());
        assertEquals(2, nGramSet.getNGram(2).get(gramYX).intValue());
    }

    @Test
    public void hasNGram() {
    }

    @Test
    public void getGrams() {
    }
}
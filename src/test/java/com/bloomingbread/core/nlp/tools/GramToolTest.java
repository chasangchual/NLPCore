package com.bloomingbread.core.nlp.tools;

import com.bloomingbread.core.nlp.NLPProcessor;
import com.bloomingbread.core.nlp.TestCorpusProvider;
import com.bloomingbread.core.nlp.entity.TextEntity;
import com.bloomingbread.core.nlp.entity.TextEntityBuilder;
import com.bloomingbread.core.nlp.tools.ngram.NGram;
import com.bloomingbread.core.nlp.tools.ngram.NGramSet;
import com.bloomingbread.core.nlp.tools.ngram.NGramTool;
import edu.stanford.nlp.pipeline.Annotator;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GramToolTest {

    TextEntity textEntity;

    @Before
    public void setUp() throws Exception {
        textEntity = TextEntityBuilder.builder()
                .withNLPTool(NLPProcessor.getBuilder().withAnnotator(Annotator.STANFORD_POS).applyStopWord().build())
                .withText(TestCorpusProvider.getNYTimesArticle())
                .build();
    }

    @Test
    public void getNGramInString() {
        Map<String, Integer> ngramFrequency = new HashMap<>();
        Map<String, Integer> ngrams = new HashMap<>();

        for (int i = 0; i < textEntity.sentenceCount(); i++) {
            List<String> grams = NGramTool.getNGramInString(textEntity.getSentence(i), 2);
            grams.forEach(gram -> {
                if (ngramFrequency.containsKey(gram)) {
                    ngramFrequency.put(gram, ngramFrequency.get(gram) + 1);
                } else {
                    ngramFrequency.put(gram, 1);
                }
            });
        }
        ngramFrequency.entrySet().forEach(entiry -> {
            System.out.println(entiry.getKey() + " : " + entiry.getValue());
        });
    }

    @Test
    public void getNGrams() {
        for (int i = 0; i < textEntity.sentenceCount(); i++) {
            NGram ngram = NGramTool.getNGram(textEntity.getSentence(i), 2);
            ngram.forEach(gram -> {
                System.out.println(gram.toString());
            });
        }
    }

    @Test
    public void getNGram() {
    }

    @Test
    public void getNGramSet() {
    }

    @Test
    public void getNGramSet1() {
        NGramSet nGramSet = new NGramSet();
        for (int i = 0; i < textEntity.sentenceCount(); i++) {
            NGramTool.getNGramSet(nGramSet, textEntity.getSentence(i), 2, 5);
        }
        nGramSet.getNGram(2).forEach(k -> System.out.println(k.toString() + " "  + Integer.valueOf(k.right)));
    }
}
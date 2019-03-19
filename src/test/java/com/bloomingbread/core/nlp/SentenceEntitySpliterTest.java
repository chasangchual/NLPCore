package com.bloomingbread.core.nlp;

import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class SentenceEntitySpliterTest {
/*
    @Test
    public void getSentenceSize() {
        SentenceSpliter sentenceTokenizer = new SentenceSpliter(TestCorpusProvider.getNYTimesArticle());
        assertEquals(26, sentenceTokenizer.getSentenceSize());
    }

    @Test
    public void getSentence() {
        SentenceSpliter sentenceTokenizer = new SentenceSpliter(TestCorpusProvider.getNYTimesArticle());
        assertEquals(
            "HONG KONG — China is undertaking a diplomatic and public relations blitz to rally support for its sweeping maritime claims in the South China Sea ahead of a decision by an international court that may rule against Beijing.",
            sentenceTokenizer.getSentenceString(0));
    }

    @Test
    public void getTokenStrings() {
        SentenceSpliter sentenceTokenizer = new SentenceSpliter(TestCorpusProvider.getNYTimesArticle());
        List<String> tokenStrings = sentenceTokenizer.getTokenWords(0);

        assertEquals(39, tokenStrings.size());
        assertEquals("HONG", tokenStrings.get(0));
    }

    @Test
    public void getTokens() {
        SentenceSpliter sentenceTokenizer = new SentenceSpliter(TestCorpusProvider.getNYTimesArticle());
        assertEquals("HONG", sentenceTokenizer.getTokenWord(0,0));
    }

    @Test
    public void getTokensStopwordDropped() {
        SentenceSpliter sentenceTokenizer = new SentenceSpliter(TestCorpusProvider.getAllStopwordString(), true);
        List<String> words = sentenceTokenizer.getTokenWords(0);
        List<String> stopWordDropped = sentenceTokenizer.getValidTokenWords(0);

        assertEquals(0, stopWordDropped.size());
        assertNotEquals(0, words.size());
    }

*/
}
package com.bloomingbread.core.nlp.tools.ngram;

import com.bloomingbread.core.nlp.entity.SentenceEntity;
import com.bloomingbread.core.nlp.entity.TokenEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * CREATE TABLE [2gram] (
 *     id        INTEGER PRIMARY KEY AUTOINCREMENT,
 *     gram1     STRING (64),
 *     gram2     STRING (64),
 *     ngrams INTEGER
 * );
 *
 * CREATE TABLE [3gram] (
 *     id        INTEGER PRIMARY KEY AUTOINCREMENT,
 *     gram1     STRING (64),
 *     gram2     STRING (64),
 *     gram3     STRING (64),
 *     ngrams INTEGER
 * );
 *
 * CREATE TABLE [4gram] (
 *     id        INTEGER PRIMARY KEY AUTOINCREMENT,
 *     gram1     STRING (64),
 *     gram2     STRING (64),
 *     gram3     STRING (64),
 *     gram4     STRING (64),
 *     ngrams INTEGER
 * );
 *
 * CREATE TABLE [5gram] (
 *     id        INTEGER PRIMARY KEY AUTOINCREMENT,
 *     gram1     STRING (64),
 *     gram2     STRING (64),
 *     gram3     STRING (64),
 *     gram4     STRING (64),
 *     gram5     STRING (64),
 *     ngrams INTEGER
 * );
 */
public class NGramTool {
    public static List<String> getNGramInString(final SentenceEntity sentence, final int gram) throws IndexOutOfBoundsException {
        List<String> ret = new ArrayList<String>();
        List<TokenEntity> tokens = sentence.getTokenEntitiesEmitStopWord();

        for(int i = 0 ; i < tokens.size() - gram; i++) {
            String ngram = "";
            for(int j = i ; j < i + gram; j++) {
                // 특정 품사만 포함 시킨다.
                // if (token.getPOS().startsWith("NN") || token.getPOS().startsWith("JJ") || token.getPOS().startsWith("V"))) ) {
                ngram += tokens.get(j).getWord() + " ";
                // }
            }
            ret.add(ngram);
        }
        return ret;
    }

    public static NGram getNGram(final SentenceEntity sentence, final int n) throws IndexOutOfBoundsException {
        if (n <= 0) throw new IllegalArgumentException("should be greater than 0.");

        List<TokenEntity> tokens = sentence.getTokenEntitiesEmitStopWord();
        NGram ngram = new NGram(n);

        for(int i = 0 ; i <= tokens.size() - n; i++) {
            Gram gram = new Gram();
            for(int j = i ; j < i + n; j++) {
                // 특정 품사만 포함 시킨다.
                // if (token.getPOS().startsWith("NN") || token.getPOS().startsWith("JJ") || token.getPOS().startsWith("V"))) ) {
                gram.add(tokens.get(j).getWord());
                // }
            }
            ngram.add(gram);
        }
        return ngram;
    }

    public static void getNGram(final SentenceEntity sentence, final int n, final NGramSet nGramSet) throws IndexOutOfBoundsException {
        if (n <= 0) throw new IllegalArgumentException("should be greater than 0.");

        List<TokenEntity> tokens = sentence.getTokenEntitiesEmitStopWord();
        NGram ngram = new NGram(n);

        for(int i = 0 ; i <= tokens.size() - n; i++) {
            Gram gram = new Gram();
            for(int j = i ; j < i + n; j++) {
                // 특정 품사만 포함 시킨다.
                // if (token.getPOS().startsWith("NN") || token.getPOS().startsWith("JJ") || token.getPOS().startsWith("V"))) ) {
                gram.add(tokens.get(j).getWord());
                // }
            }
            ngram.add(gram);
            nGramSet.add(ngram);
        }
    }

    public static NGramSet getNGramSet(final SentenceEntity sentence,
                                       final int fromGram, final int toGram) {
        NGramSet nGramSet  = new NGramSet();
        IntStream.rangeClosed(fromGram, toGram).forEach(n->nGramSet.add(getNGram(sentence, n)));
        return nGramSet;
    }

    public static void getNGramSet(final NGramSet nGramSet, final SentenceEntity sentence,
                                   final int fromGram, final int toGram) {
        IntStream.rangeClosed(fromGram, toGram).forEach(n->nGramSet.add(getNGram(sentence, n)));
    }
}

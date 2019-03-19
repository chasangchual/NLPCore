package com.bloomingbread.core.nlp.annotator;

import com.bloomingbread.core.nlp.annotator.coreannotator.StopWordCoreAnnotator;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotator;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import edu.stanford.nlp.ling.CoreAnnotation;
import org.apache.lucene.analysis.CharArraySet;

import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.util.Pair;
import org.apache.lucene.analysis.en.EnglishAnalyzer;

/**
 * StopWordAnnotator implements edu.stanford.nlp.pipeline.Annotator interface in order to take part in the nlp processing
 * pipeline. Mainly it loads the stop load list and store the to stopwords.
 * And it checks out the given work is a stop word or not while the nlp processing is going on in the pipeline.
 * StopWordAnnotator accepts the customer comma separated stop word list with "stopword" property.
 * If the list does not come in through, it uses org.apache.lucene.analysis.en.EnglishAnalyzer as default.
 *
 * <br>Default stop words list <br>
 * but, be, with, such, then, for, no, will, not, are, and, their, if, this, on, into, a, or, there, in, that, they,
 * was, is, it, an, the, as, at, these, by, to, of
 *
 * by Sangchual Cha (sangchual.cha@gmail.com)
 *
 * inspired by https://jar-download.com/artifacts/com.zensols/stopword-annotator/2.2/source-code/intoxicant/analytics/corenlp/StopwordAnnotator.java
 *
 */
public class StopWordAnnotator implements Annotator {
    public static final String ANNOTATOR_CLASS = "stopword";
    public static final String STOPWORDS_LIST = "stopword-list";

    public static final String IGNORE_STOPWORD_CASE = "ignore-stopword-case";

    private CharArraySet stopwords;
    private Properties properties;

    public StopWordAnnotator() {
        this(new Properties());
    }

    public StopWordAnnotator(String stopwordList, Properties properties) {
        boolean ignoreCase = Boolean.parseBoolean(properties.getProperty(IGNORE_STOPWORD_CASE, "false"));
        this.stopwords = getStopWordList(stopwordList, ignoreCase);
    }

    public StopWordAnnotator(Properties properties) {
        this.properties = properties;

        if (this.properties.containsKey(STOPWORDS_LIST)) {
            String stopwordList = properties.getProperty(STOPWORDS_LIST);
            boolean ignoreCase = Boolean.parseBoolean(properties.getProperty(IGNORE_STOPWORD_CASE, "false"));
            this.stopwords = getStopWordList(stopwordList, ignoreCase);
        } else {
            this.stopwords = (CharArraySet) EnglishAnalyzer.ENGLISH_STOP_WORDS_SET;
        }
    }

    /**
     * applies the stop word processing. Basically it sets two results.
     * com.bloomingbread.core.nlp.annotator.coreannotator.StopWordCoreAnnotator maintains two boolean variables that
     * represent "is the given word is stop word" and "is the given lemma is stop word".
     *
     * @param annotation set of Tokens object
     */
    @Override
    public void annotate(Annotation annotation) {
        if (stopwords != null && stopwords.size() > 0 && annotation.containsKey(TokensAnnotation.class)) {
            List<CoreLabel> tokens = annotation.get(TokensAnnotation.class);
            for (CoreLabel token : tokens) {
                boolean isWordStopword = stopwords.contains(token.word().toLowerCase());
                boolean isLemmaStopword = stopwords.contains(token.word().toLowerCase());
                Pair<Boolean, Boolean> pair = Pair.makePair(isWordStopword, isLemmaStopword);
                token.set(StopWordCoreAnnotator.class, pair);
            }
        }
    }

    @Override
    public Set<Class<? extends CoreAnnotation>> requirementsSatisfied() {
        return Collections.singleton(StopWordCoreAnnotator.class);
    }

    @Override
    public Set<Class<? extends CoreAnnotation>> requires() {
        return Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
                CoreAnnotations.TextAnnotation.class,
                CoreAnnotations.TokensAnnotation.class,
                CoreAnnotations.LemmaAnnotation.class,
                CoreAnnotations.PartOfSpeechAnnotation.class
        )));
    }

    public static CharArraySet getStopWordList(String stopwordList, boolean ignoreCase) {
        String[] terms = stopwordList.split(",");
        return CharArraySet.unmodifiableSet(new CharArraySet(Arrays.asList(terms), ignoreCase));
    }
}
package com.bloomingbread.core.nlp.pipeline;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.ArrayList;

public interface Pipeline {
    public void initializeStopwords(String stopFile) throws Exception ;
    public ArrayList<String> getStopWords() ;
    public StanfordCoreNLP getStanfordCoreNLP() ;
    public Porter getPorter();
}

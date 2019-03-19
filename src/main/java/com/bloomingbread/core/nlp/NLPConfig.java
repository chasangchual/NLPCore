package com.bloomingbread.core.nlp;


public class NLPConfig {
    private static NLPConfig config = null ;

    public synchronized static NLPConfig getConfig() {
        if(config == null) {
            config = new NLPConfig();
        }
        return config;
    }

    private NLPConfig() {

    }

    public int getParseWordCount() {
        return 100;
    }
}

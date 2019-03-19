package com.bloomingbread.core.nlp.entity;

import com.bloomingbread.core.nlp.NLPProcessor;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

public class TextEntityBuilder {
    private static TextEntityBuilder builder = null;

    private NLPProcessor nlpProcessor = null;
    private StanfordCoreNLP nlpPileline = null;
    private String rawText = "";

    public synchronized static TextEntityBuilder builder() {
        if(builder == null) {
            builder = new TextEntityBuilder();
        }
        return builder;
    }

    private TextEntityBuilder() {

    }

    public TextEntity build() {
        return new TextEntity(nlpProcessor.getPipeline().process(rawText));
    }

    public TextEntityBuilder withText(final String text) {
        this.rawText = text;
        return this;
    }

    public TextEntityBuilder withNLPTool(final NLPProcessor nlpProcessor) {
        this.nlpProcessor = nlpProcessor;
        return this;
    }
}

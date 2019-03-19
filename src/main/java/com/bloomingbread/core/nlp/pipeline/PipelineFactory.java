package com.bloomingbread.core.nlp.pipeline;

import edu.stanford.nlp.pipeline.Annotator;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.Collection;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

public class PipelineFactory {
    private static StanfordCoreNLP pipeline = null;
    private static final Set<String> defaultPipelineProperties = AnnotatorPropertyProvider.getRequiredAnnotators(Annotator.STANFORD_PARSE);

    public static StanfordCoreNLP newPileLine() {
        return new StanfordCoreNLP(getDefaultAnnotationProperties());
    }

    public static StanfordCoreNLP newPileLine(final String annotatorName) {
        return new StanfordCoreNLP(getAnnotationProperties(AnnotatorPropertyProvider.getRequiredAnnotators(annotatorName)));
    }

    public static StanfordCoreNLP newPileLine(final String annotatorName, Collection<String> customAnnotatorNames) {
        return new StanfordCoreNLP(getAnnotationProperties(AnnotatorPropertyProvider.getRequiredAnnotators(annotatorName, customAnnotatorNames)));
    }

    public synchronized static StanfordCoreNLP getPileLine(final String annotatorName) {
        if(pipeline == null) {
            pipeline = new StanfordCoreNLP(getAnnotationProperties(AnnotatorPropertyProvider.getRequiredAnnotators(annotatorName)));
        }
        return pipeline;
    }

    public synchronized static StanfordCoreNLP getPileLine() {
        if(pipeline == null) {
            pipeline = new StanfordCoreNLP(getDefaultAnnotationProperties());
        }
        return pipeline;
    }

    private static Properties getDefaultAnnotationProperties() {
        return getAnnotationProperties(defaultPipelineProperties);
    }

    private static Properties getAnnotationProperties(final Set<String> annotators) {
        return getAnnotationProperties(annotators.stream().collect(Collectors.joining(",")));
    }

    private static Properties getAnnotationProperties(final String annotators) {
        Properties props = new Properties();
        props.put("annotators", annotators);
        return props;
    }
}

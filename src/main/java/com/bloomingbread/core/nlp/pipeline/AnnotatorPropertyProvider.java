package com.bloomingbread.core.nlp.pipeline;

import edu.stanford.nlp.ling.CoreAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotator;
import edu.stanford.nlp.pipeline.TokenizerAnnotator;
import edu.stanford.nlp.util.ArraySet;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * helper class for "annotators" pipeline properties
 */
public class AnnotatorPropertyProvider {
    /**
     * collect required annotators for the given annotator
     * @param annotator annotator name to procceed
     * @return annotator name collections
     */
    public static Set<String> getRequiredAnnotators(final String annotator) {
        // it covers only default annotators. it needs to handle separately for the custom annotators
        if (Annotator.DEFAULT_REQUIREMENTS.containsKey(annotator)) {
            // retrieves annotator dependencies list and add itself to the list.
            Set<String> requiredDependencies = Annotator.DEFAULT_REQUIREMENTS.get(annotator);
            requiredDependencies.add(annotator);
            // return comma separated annotator list
            return requiredDependencies;
        } else if(CustomAnnotator.CUSTOM_REQUIREMENTS.containsKey(annotator)) {
            // retrieves annotator dependencies list and add itself to the list.
            Set<String> requiredDependencies = CustomAnnotator.DEFAULT_REQUIREMENTS.get(annotator);
            requiredDependencies.add(annotator);
            // return comma separated annotator list
            return requiredDependencies;
        }
        else {
            throw new IllegalArgumentException(String.format("%s is not supported.", annotator));
        }
    }

    public static Set<String> getRequiredAnnotators(final Collection<String> annotators) {
        Set<String> dependentAnnotators = new HashSet<>();
        for(String annotator : annotators ) {
            if (Annotator.DEFAULT_REQUIREMENTS.containsKey(annotator)) {
                Set<String> requiredDependentAnnotators = Annotator.DEFAULT_REQUIREMENTS.get(annotator);
                dependentAnnotators.add(annotator);
                dependentAnnotators.addAll(requiredDependentAnnotators);
            } else if(CustomAnnotator.CUSTOM_REQUIREMENTS.containsKey(annotator)) {
                Set<String> requiredDependentAnnotators = CustomAnnotator.DEFAULT_REQUIREMENTS.get(annotator);
                dependentAnnotators.add(annotator);
                dependentAnnotators.addAll(requiredDependentAnnotators);
            } else {
                throw new IllegalArgumentException(String.format("%s is not supported.", annotator));
            }
        }
        return dependentAnnotators;
    }

    public static Set<String> getRequiredAnnotators(final String annotator, final Collection<String> annotators) {
        Set<String> dependentAnnotators = new HashSet<>();
        dependentAnnotators.addAll(getRequiredAnnotators(annotator));
        dependentAnnotators.addAll(getRequiredAnnotators(annotators));
        return dependentAnnotators;
    }

    public static Set<Class<? extends CoreAnnotation>> getCoreAnnotationsRequires(final Set<String> annotators) {
        Set<Class<? extends CoreAnnotation>> coreAnnotators = Collections.emptySet() ;

        for(String annotator : annotators) {
            coreAnnotators.addAll(getCoreAnnotationsRequires(annotator));
        }
        return coreAnnotators;
    }


    public static Set<Class<? extends CoreAnnotation>> getCoreAnnotationsRequires(final String annotatorPropertyName) {
        Set<Class<? extends CoreAnnotation>> coreAnnotators = Collections.emptySet() ;
        switch (annotatorPropertyName) {
            case Annotator.STANFORD_TOKENIZE:
                coreAnnotators = Collections.emptySet();
                break;
            case Annotator.STANFORD_CLEAN_XML:
                coreAnnotators = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
                        CoreAnnotations.TokensAnnotation.class
                )));
                break;
            case Annotator.STANFORD_SSPLIT:
                coreAnnotators = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
                        CoreAnnotations.TextAnnotation.class,
                        CoreAnnotations.TokensAnnotation.class,
                        CoreAnnotations.CharacterOffsetBeginAnnotation.class,
                        CoreAnnotations.CharacterOffsetEndAnnotation.class,
                        CoreAnnotations.IsNewlineAnnotation.class,
                        CoreAnnotations.TokenBeginAnnotation.class,
                        CoreAnnotations.TokenEndAnnotation.class,
                        CoreAnnotations.OriginalTextAnnotation.class
                )));
                break;
            case Annotator.STANFORD_POS:
                coreAnnotators = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
                        CoreAnnotations.TextAnnotation.class,
                        CoreAnnotations.TokensAnnotation.class,
                        CoreAnnotations.CharacterOffsetBeginAnnotation.class,
                        CoreAnnotations.CharacterOffsetEndAnnotation.class,
                        CoreAnnotations.SentencesAnnotation.class
                )));
                break;
            case Annotator.STANFORD_LEMMA:
                coreAnnotators = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
                        CoreAnnotations.TextAnnotation.class,
                        CoreAnnotations.TokensAnnotation.class,
                        CoreAnnotations.SentencesAnnotation.class,
                        CoreAnnotations.PartOfSpeechAnnotation.class
                )));
                break;
            case Annotator.STANFORD_NER:
                coreAnnotators = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
                )));
                break;
            case Annotator.STANFORD_PARSE:
                coreAnnotators = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
                )));
                break;
        }
        return coreAnnotators;
    }

    public static Set<Class<? extends CoreAnnotation>> getCoreAnnotationsrequirementsSatisfied(final String annotatorPropertyName) {
        Set<Class<? extends CoreAnnotation>> coreAnnotators = Collections.emptySet() ;
        switch (annotatorPropertyName) {
            case Annotator.STANFORD_TOKENIZE:
                coreAnnotators = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
                        CoreAnnotations.TextAnnotation.class,
                        CoreAnnotations.TokensAnnotation.class,
                        CoreAnnotations.CharacterOffsetBeginAnnotation.class,
                        CoreAnnotations.CharacterOffsetEndAnnotation.class,
                        CoreAnnotations.BeforeAnnotation.class,
                        CoreAnnotations.AfterAnnotation.class,
                        CoreAnnotations.TokenBeginAnnotation.class,
                        CoreAnnotations.TokenEndAnnotation.class,
                        CoreAnnotations.PositionAnnotation.class,
                        CoreAnnotations.IndexAnnotation.class,
                        CoreAnnotations.OriginalTextAnnotation.class,
                        CoreAnnotations.ValueAnnotation.class,
                        CoreAnnotations.IsNewlineAnnotation.class
                )));
                break;
            case Annotator.STANFORD_CLEAN_XML:
                coreAnnotators = Collections.emptySet();
                break;
            case Annotator.STANFORD_SSPLIT:
                coreAnnotators = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
                        CoreAnnotations.SentencesAnnotation.class,
                        CoreAnnotations.SentenceIndexAnnotation.class
                )));
                break;
            case Annotator.STANFORD_POS:
                coreAnnotators = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
                        CoreAnnotations.PartOfSpeechAnnotation.class
                )));
                break;
            case Annotator.STANFORD_LEMMA:
                coreAnnotators = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
                        CoreAnnotations.LemmaAnnotation.class
                )));
                break;
            case Annotator.STANFORD_NER:
                coreAnnotators = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
                )));
                break;
            case Annotator.STANFORD_PARSE:
                coreAnnotators = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
                )));
                break;
        }
        return coreAnnotators;
    }
}

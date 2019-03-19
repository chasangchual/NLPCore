package com.bloomingbread.core.nlp.annotator.coreannotator;

import edu.stanford.nlp.ling.CoreAnnotation;
import edu.stanford.nlp.util.Pair;

public class StopWordCoreAnnotator implements CoreAnnotation<Pair<Boolean, Boolean>>
{
    @Override
    @SuppressWarnings("unchecked")
    /**
     * returns processing result of Pair<isWordStopword, isLemmaStopword>
     */
    public Class<Pair<Boolean, Boolean>> getType() {
        return (Class<Pair<Boolean, Boolean>>) Pair.makePair(true, true).getClass();
    }
}
package com.bloomingbread.core.nlp.reader;

import com.bloomingbread.core.nlp.NLPProcessor;
import com.bloomingbread.core.nlp.entity.TextEntity;
import com.bloomingbread.core.nlp.entity.TextEntityBuilder;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;
import edu.stanford.nlp.pipeline.Annotator;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class TSVReader {
    public static void load(final String path) {
        TsvParserSettings settings = new TsvParserSettings();
        settings.getFormat().setLineSeparator("\n");
        settings.setMaxCharsPerColumn(4096 * 12);
        TsvParser parser = new TsvParser(settings);
        parser.beginParsing(new File("/Users/sangchua/dataset/aws_customer_review/amazon_reviews_us_Camera_v1_00.tsv"));
        Record record = null;

        NLPProcessor processor = NLPProcessor.getBuilder().withAnnotator(Annotator.STANFORD_POS).applyStopWord().build();
        Map<String, Integer> ngramFrequency = new HashMap<>();

        int count = 0 ;
        while((record = parser.parseNextRecord()) != null) {
            // [marketplace, customer_id, review_id, product_id, product_parent, product_title, product_category, star_rating, helpful_votes, total_votes, vine, verified_purchase, review_headline, review_body, review_date]
            // RecordMetaData metaData = record.getMetaData();

            if(record.getString("review_body") != null) {
                TextEntity textEntity = TextEntityBuilder.builder()
                        .withNLPTool(processor)
                        .withText(record.getString("review_body"))
                        .build();

/*
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
*/
            }
            System.out.println(++count);
        }
        parser.stopParsing();

        ngramFrequency.entrySet().forEach(entiry -> {
            System.out.println(entiry.getKey() + " : " + entiry.getValue());
        });
    }

    public static void main(String[] args) {
        TSVReader.load("");
    }
}

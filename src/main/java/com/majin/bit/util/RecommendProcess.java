package com.majin.bit.util;

import java.util.Collection;

import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RecommendProcess {
	private static Logger logger = LoggerFactory.getLogger(RecommendProcess.class);

    public Collection<String> recommender(String test) {
        Word2Vec word2Vec = WordVectorSerializer.readWord2VecModel("src/main/webapp/trainingFile/rbsks0302.txt");
        System.out.println(test);
        Collection<String> recommendItems = word2Vec.wordsNearestSum(test, 5);
        logger.info("Closest words : " + recommendItems);

        return recommendItems;
    }
}

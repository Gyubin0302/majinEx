package com.majin.bit.util;

import java.util.ArrayList;
import java.util.Collection;

import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RecommendProcess {
	private static Logger logger = LoggerFactory.getLogger(RecommendProcess.class);

    public Collection<String> recommender(String userId, String search) {
        Word2Vec word2Vec = WordVectorSerializer.readWord2VecModel("D:/final/userText/" + userId + "_training.txt");

        Collection<String> recommendItems = new ArrayList<>();
        recommendItems = word2Vec.wordsNearestSum(search, 5);
        if(recommendItems.size() != 0 ) {
        	logger.info("Closest words : " + recommendItems);
        	return recommendItems;
        } else {
        	return null;
        }

    }
}

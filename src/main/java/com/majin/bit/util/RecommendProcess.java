package com.majin.bit.util;

import java.util.ArrayList;
import java.util.Collection;

import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.Word2Vec;

public class RecommendProcess {

    public Collection<String> recommender(String userId, String search) {
        Word2Vec word2Vec = WordVectorSerializer.readWord2VecModel("D:/final/userText/" + userId + "_training.txt");

        Collection<String> recommendItems = new ArrayList<>();
        try {
        	recommendItems = word2Vec.wordsNearestSum(search, 5);
        	return recommendItems;
        } catch (Exception e) {
        	
        	return null;
		}
    }
}

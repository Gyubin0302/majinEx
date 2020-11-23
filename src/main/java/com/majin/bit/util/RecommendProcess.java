package com.majin.bit.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;

import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.Word2Vec;

public class RecommendProcess {

    public Collection<String> recommender(String userId, String search) throws FileNotFoundException {
    	
    	String path = "D:/final/userText/";
        Word2Vec word2Vec = new Word2Vec();
        File userTrainingFile = new File(path + userId + "_trainingModel.vec");
        if(!userTrainingFile.exists()) {
        	return null;
        }else {
        	if(userId.equals("anonymousUser")) {
            	word2Vec = WordVectorSerializer.readWord2VecModel("D:/final/userText/anonymousUser_trainingModel.vec");
            	//word2Vec = WordVectorSerializer.loadFullModel("D:/final/userText/anonymousUser_trainingModel.vec");
            }else {
            	word2Vec = WordVectorSerializer.readWord2VecModel(path + userId + "_trainingModel.vec");
            }
            Collection<String> recommendItems = new ArrayList<>();
            try {
            	recommendItems = word2Vec.wordsNearestSum(search, 5);
            	return recommendItems;
            } catch (Exception e) {
            	
            	return null;
    		}
        }
        
    }
}

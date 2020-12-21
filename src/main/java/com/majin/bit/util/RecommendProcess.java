package com.majin.bit.util;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;

import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.Word2Vec;

public class RecommendProcess {

	public Collection<String> recommender(String search) throws FileNotFoundException {

		String path = "src/main/webapp/resources/trainingWord2VecDummy/word2vec_trainingModel.vec";
		Word2Vec word2Vec = new Word2Vec();
		word2Vec = WordVectorSerializer.readWord2VecModel(path);
		
		try {
			Collection<String> recommendItems = new ArrayList<>();
			recommendItems = word2Vec.wordsNearestSum(search, 5);
			return recommendItems;
		} catch (Exception e) {
			return null;
		}
	}

}

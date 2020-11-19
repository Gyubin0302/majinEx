package com.majin.bit.word2vecTest;

import java.io.File;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.text.sentenceiterator.BasicLineIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Word2VecTest {

	private static final Logger logger = LoggerFactory.getLogger(Word2VecTest.class);

	public void training(String userId) throws Exception {
		
		File trainingFile = new File("D:/final/userText/" + userId + "_save.txt");
		if(trainingFile.length() != 0) {
//			String userId = "rbsks0302";
			logger.info("Load & Vectorize Sentences....");
			SentenceIterator iter = new BasicLineIterator(trainingFile);
			
	
			TokenizerFactory t = new DefaultTokenizerFactory();
			t.setTokenPreProcessor(new CommonPreprocessor() {
				@Override
				public String preProcess(String token) {
					
					return StringCleaning.stripPunct(token);
				}
			});
			
			logger.info("Building model....");
			Word2Vec vec = new Word2Vec.Builder().minWordFrequency(1).iterations(50).layerSize(300).seed(42).windowSize(3)
					.iterate(iter).tokenizerFactory(t).build();
	
			logger.info("Fitting Word2Vec model....");
			vec.fit();
	
			// Word Vectors.
			logger.info("Writing word vectors to text file....");
	
//			WordVectorSerializer.writeWordVectors(vec, new File("src/main/webapp/trainingFile/" + userId + ".txt"));
			WordVectorSerializer.writeWordVectors(vec, new File("D:/final/userText/" + userId + "_training.txt"));
	
			/*
			 * PLEASE NOTE: after model is restored, it's still required to set
			 * SentenceIterator and TokenizerFactory, if you're going to train this model
			 */
//			SentenceIterator iterator = new BasicLineIterator(new File("C:/Users/rbsks/dl4j-examples-data/dl4j-examples/nlp/raw_sentences.txt"));
//			TokenizerFactory tokenizerFactory = new DefaultTokenizerFactory();
//			tokenizerFactory.setTokenPreProcessor(new CommonPreprocessor() {
//				@Override
//				public String preProcess(String token) {
//					return StringCleaning.stripPunct(token).toUpperCase();
//				}
//			
//			});
//	
//			word2Vec.setTokenizerFactory(tokenizerFactory);
//			word2Vec.setSentenceIterator(iterator);
//	
//			logger.info("Word2vec uptraining...");
//	
//			word2Vec.fit();
//			System.out.println(word2Vec.toString());
//			
//			Collection<String> uptrainWords = word2Vec.wordsNearestSum("UUIIOOPP4-300100002", 5);
//			logger.info("Closest words to 'day' on 2nd run: " + uptrainWords);
//			System.out.println("-----------------------------------------------");
//			for(String word2 : uptrainWords) { 
//				System.out.println(word2);
//			}
	
		}
	}
}

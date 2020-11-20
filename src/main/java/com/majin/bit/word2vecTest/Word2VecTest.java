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
		
		// Java Word2Vec
		File trainingFile = new File("D:/final/userText/" + userId + "_save.txt");
		if(trainingFile.length() != 0) {
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
			Word2Vec vec = new Word2Vec.Builder()
					.batchSize(1000) // cpu나 gpu에서 iteration을 한번에 처리하는 단어의 양
					.useAdaGrad(false)
					.learningRate(0.025) // 학습속도
					.minLearningRate(1e-3) // 학습 속도의 하한선
					.minWordFrequency(1) // 말뭉치에서 유효한 단어로 인정받는데 필요한 최소 단어 개수\
					.iterations(50) // 전체 데이터에 몇 회의 학습을 할 것인지 정함
					.layerSize(300) // 단어의 백터 차원 300 차원
					.seed(42)
					.windowSize(3)
					.iterate(iter) // 데이터의 여러 배치(batch, 데이터를 쪼갠 단위)중 어떤 배치에서 현재 학습중인지를 알려 줌
					.tokenizerFactory(t) // 배치에 있는 단어를 학습 과정에 공급
					.build();
	
			logger.info("Fitting Word2Vec model....");
			vec.fit(); // 학습 시작
	
			// Word Vectors.
			logger.info("Writing word vectors to text file....");
	
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

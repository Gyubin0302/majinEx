package com.majin.bit.word2vecTest;

import java.io.File;

import org.deeplearning4j.models.embeddings.WeightLookupTable;
import org.deeplearning4j.models.embeddings.inmemory.InMemoryLookupTable;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.VocabWord;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.models.word2vec.wordstore.inmemory.InMemoryLookupCache;
import org.deeplearning4j.text.sentenceiterator.BasicLineIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.sentenceiterator.SentencePreProcessor;
import org.deeplearning4j.text.tokenization.tokenizer.TokenPreProcess;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Word2VecTest {

	private static final Logger logger = LoggerFactory.getLogger(Word2VecTest.class);

	public void training(String userId) throws Exception {
		
		File trainingFile = new File("D:/final/userText/" + userId + "_save2.txt");	
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
			
			InMemoryLookupCache cache = new InMemoryLookupCache();
			WeightLookupTable<VocabWord> table = new InMemoryLookupTable.Builder<VocabWord>()
			    .vectorLength(150)
			    .useAdaGrad(false)
			    .cache(cache)
			    .lr(0.025f).build();
			
			logger.info("Building model....");
			Word2Vec vec = new Word2Vec.Builder()
					.batchSize(1000) // cpu나 gpu에서 iteration을 한번에 처리하는 단어의 양
					.useAdaGrad(false)
					.learningRate(0.025) // 학습속도
					.minLearningRate(1e-3) // 학습 속도의 하한선
					.minWordFrequency(1) // 말뭉치에서 유효한 단어로 인정받는데 필요한 최소 단어 개수
					.iterations(100) // 전체 데이터에 몇 회의 학습을 할 것인지 정함
					.layerSize(150) // 단어의 백터 차원 150 차원
					.seed(42)
					.windowSize(1)
					.iterate(iter) // 데이터의 여러 배치(batch, 데이터를 쪼갠 단위)중 어떤 배치에서 현재 학습중인지를 알려 줌
					.tokenizerFactory(t) // 배치에 있는 단어를 학습 과정에 공급
					.epochs(10)
					.lookupTable(table)
					.vocabCache(cache)
					.build();
	
			logger.info("Fitting Word2Vec model....");
			vec.fit(); // 학습 시작
	
			// Word Vectors.
			logger.info("Writing word vectors to text file....");
			WordVectorSerializer.writeWord2VecModel(vec, new File("D:/final/userText/word2vec_trainingModel.vec"));
		}
	}
}

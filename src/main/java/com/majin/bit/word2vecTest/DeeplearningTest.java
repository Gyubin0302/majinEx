package com.majin.bit.word2vecTest;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.Updater;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DeeplearningTest {

	private static final int seed = 0;
	private static final String SAMPLE_CSV_FILE_PATH = "D:/final/userText/rbsks147_save.csv";
	
	@RequestMapping(value = "/DeepTest")
	public String Test() throws IOException
	{

		INDArray input = Nd4j.create(39897, 5); // 470, 17
		INDArray labels = Nd4j.create(39897, 1); // 470, 1
		
		try (Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH), Charset.forName("MS949"));

			CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);) {

			for (CSVRecord csvRecord : csvParser) {
				for (int i = 0; i < 5; i++) {
					String inputValue = csvRecord.get(i);
					input.putScalar(new int[] { (int) (csvRecord.getRecordNumber() - 1), i }, Integer.parseInt(inputValue));
				}
				String resultValue = csvRecord.get(17);

				labels.putScalar(new int[] { (int) (csvRecord.getRecordNumber() - 1), 0 }, Integer.parseInt(resultValue));
			}
		}

		DataSet ds = new DataSet(input, labels);
		
		System.out.println(ds.toString());

		MultiLayerConfiguration conf = getMyFirstDeeplearning();
		MultiLayerNetwork net = new MultiLayerNetwork(conf);
		net.init();
		net.setListeners(new ScoreIterationListener(1));
		net.fit(ds);

		INDArray output = net.output(ds.getFeatures());
		Evaluation eval = new Evaluation(2);
		eval.eval(ds.getLabels(), output);
		System.out.println(eval.stats());
		
		return "training/Deep";
	}

	private static MultiLayerConfiguration getMyFirstDeeplearning() {
		return new NeuralNetConfiguration.Builder()
				.maxNumLineSearchIterations(30)
				.seed(seed)
				.optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
				.updater(Updater.ADAM)
				.list()
				.layer(0, new DenseLayer.Builder().nIn(5).nOut(30).activation(Activation.RELU).build())
				.layer(1, new OutputLayer.Builder(LossFunctions.LossFunction.MSE).activation(Activation.SIGMOID).nIn(30)
				.nOut(1).build())
				.build();
	}

}

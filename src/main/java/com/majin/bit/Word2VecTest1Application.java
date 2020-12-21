package com.majin.bit;

import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.ConvolutionLayer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan
public class Word2VecTest1Application {

	public static void main(String[] args) {
		SpringApplication.run(Word2VecTest1Application.class, args);
	}

}

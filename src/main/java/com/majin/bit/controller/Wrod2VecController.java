package com.majin.bit.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.majin.bit.word2vecTest.Word2VecTest;

@Controller
public class Wrod2VecController {
	
	// Python Word2Vec
	@RequestMapping(value="/w")
    public String training2(){
        return "/training/W2V_Python";
    }
	
	@ResponseBody
	@RequestMapping(value = "W2V", method = RequestMethod.POST, produces = "application/String;charset=utf-8")
	public String W2VTest(String search) {
		String textFile = "test.txt";
		String re = "";
		try {
			ProcessBuilder pb = new ProcessBuilder("python", "D:/workspace4.7/Word2VecTest-1/Word2Vec.py", search, textFile);
			Process p = pb.start();

			BufferedReader bfr = new BufferedReader(new InputStreamReader(p.getInputStream(), "MS949"));

			String line = "";
			while ((line = bfr.readLine()) != null) {
				re += line.trim();
			}
			bfr.close();
		} catch (Exception e) {
		}

		return re;
	}
	
	@ResponseBody
	@RequestMapping(value = "/nonUserWord2Vec", method = RequestMethod.POST)
	public String nonUserWord2Vec() throws Exception {
		Word2VecTest word2Vec = new Word2VecTest();
		word2Vec.training("anonymousUser");
		return "학습완료";
	}
	
	
}

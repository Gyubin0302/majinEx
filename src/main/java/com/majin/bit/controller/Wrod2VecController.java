package com.majin.bit.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.majin.bit.dto.HorseDto;
import com.majin.bit.dto.JkDto;
import com.majin.bit.dto.TrDto;
import com.majin.bit.service.HorseService;
import com.majin.bit.service.TrainerService;
import com.majin.bit.service.jockeyService;
import com.majin.bit.util.HorseCrawling;
import com.majin.bit.util.JockeyCrawling;
import com.majin.bit.util.RecommendProcess;
import com.majin.bit.util.TrainerCrawling;
import com.majin.bit.word2vecTest.Word2VecTest;

@Controller
public class Wrod2VecController {

//	@RequestMapping(value="/training")
//	public String Word2VecTestpage() throws Exception {
//		
//		Word2VecTest word2vec = new Word2VecTest();
//		word2vec.training();
//		
//		return "/training/w2v";
//	}
	
	@RequestMapping(value="/t")
	public String Word2VecTestpage2(){

		return "/training/W2V_Java";
	}
	
	@ResponseBody
	@RequestMapping(value="/trainingTest", method = RequestMethod.POST)
	public String Word2VecTestpage2(String search){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		RecommendProcess recommend = new RecommendProcess();
		Collection<String> recommendWord =  recommend.recommender(auth.getName(), search);
		if(recommendWord != null) {
			return recommendWord.toString();
		} else {
			return "추천단어가 없습니다.";
		}
		
	}
	
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
			
			System.out.println(search);

			ProcessBuilder pb = new ProcessBuilder("python", "D:/workspace4.7/Word2VecTest-1/Word2Vec.py", search, textFile);
			Process p = pb.start();

			BufferedReader bfr = new BufferedReader(new InputStreamReader(p.getInputStream(), "MS949"));

			System.out.println(".........start   process.........");
			String line = "";
			while ((line = bfr.readLine()) != null) {
				System.out.println("Python Output: " + line);
				re += line.trim();
			}
			bfr.close();
			System.out.println("........end   process.......");

		} catch (Exception e) {
			System.out.println(e);
		}

		return re;
	}
	
}

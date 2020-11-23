package com.majin.bit.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;



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

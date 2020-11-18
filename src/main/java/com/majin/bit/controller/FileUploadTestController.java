package com.majin.bit.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadTestController {

	@RequestMapping(value = "/up",method = RequestMethod.GET)
	public String imgTest(HttpServletRequest httpServletRequest) {
		String path = httpServletRequest.getSession().getServletContext().getRealPath("");
		System.out.println(path);
		return "test/UploadTest";
	}
	
	@RequestMapping( value = "/upTest" , method = RequestMethod.POST)
	public String imgUpTest(@RequestParam("file")MultipartFile file ) {
		try {
			byte[] bytes = file.getBytes();
			System.out.println(bytes);
			Path path = Paths.get(getClass().getClassLoader()+file.getOriginalFilename());
			System.out.println(path);
			Files.write(path, bytes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "test/UploadTest";
	}
	
	
}

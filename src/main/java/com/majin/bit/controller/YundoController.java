package com.majin.bit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.majin.bit.service.YundoService;

@Controller
public class YundoController {
	@Autowired
	YundoService yundoService;
	
	@GetMapping("/yundo/seoul")
	public void YundoCrawlSeoul() {
		
	}
	@GetMapping("/yundo/jeju")
	public void YundoCrawlJeju() {
		
	}
	@GetMapping("/yundo/busan")
	public void YundoCrawlBusan() {
		
	}	
}

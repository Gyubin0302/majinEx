package com.majin.bit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.majin.bit.dto.YundoDto;
import com.majin.bit.service.YundoService;
import com.majin.bit.util.YundoCrawling;

@Controller
public class YundoController {
	@Autowired
	YundoService yundoService;
	
	@GetMapping("/yundo/seoul")
	public String YundoCrawlSeoul() {
		yundoService.yundoCrawling();
		return "home";
	}
	@GetMapping("/yundo/jeju")
	public void YundoCrawlJeju() {
		
	}
	@GetMapping("/yundo/busan")
	public void YundoCrawlBusan() {
		
	}	
}

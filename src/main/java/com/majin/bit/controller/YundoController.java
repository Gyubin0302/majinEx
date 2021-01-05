package com.majin.bit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.majin.bit.dto.YundoDto;
import com.majin.bit.service.YundoService;

@Controller
public class YundoController {
	@Autowired
	YundoService yundoService;
	
	@GetMapping("/yundo/seoul")
	public String YundoCrawlSeoul() {
		yundoService.yundoCrawling("1");
		return "home";
	}
	@GetMapping("/yundo/jeju")
	public String YundoCrawlJeju() {
		yundoService.yundoCrawling("2");
		return "home";
	}
	@GetMapping("/yundo/busan")
	public String YundoCrawlBusan() {
		yundoService.yundoCrawling("3");
		return "home";
	}	
	@PostMapping("/yundo/view")
	public String ShowYundo(Model model) {
		List<YundoDto> yundos;
		/*
		 * 여기 실행하기 전에 db에서
		 * create view findname as select hrno as 'seq',hrname as 'name' from horseskinny union select jkno as 'seq',jkname as 'name' from jockeyskinny union select trno as 'seq',trname as 'name' from trainerskinny;
		 */
		yundos = yundoService.showYundo("1");
		model.addAttribute("seoul", yundos);
		model.addAttribute("seoulSeq", yundoService.splitFun(yundos,1));
		model.addAttribute("seoulName", yundoService.splitFun(yundos,2));
		model.addAttribute("seoulMeet", yundoService.splitFun(yundos,3));
		
		yundos = yundoService.showYundo("2");
		model.addAttribute("jeju", yundos);
		model.addAttribute("jejuSeq", yundoService.splitFun(yundos,1));
		model.addAttribute("jejuName", yundoService.splitFun(yundos,2));
		model.addAttribute("jejuMeet", yundoService.splitFun(yundos,3));
		
		yundos = yundoService.showYundo("3");
		model.addAttribute("busan", yundos);
		model.addAttribute("busanSeq", yundoService.splitFun(yundos,1));
		model.addAttribute("busanName", yundoService.splitFun(yundos,2));
		model.addAttribute("busanMeet", yundoService.splitFun(yundos,3));
		
		return "yundo";
	}
}

package com.majin.bit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.majin.bit.service.RaceService;

@Controller
public class PredictionController {
	@Autowired
	RaceService raceService;
	
	@GetMapping("/viewrace")
	public String viewrace(Model model) {
		model.addAttribute("races", raceService.allrace());
		return "viewRace";
	}
	
	@GetMapping("/viewdetailrace")
	public String viewdetailrace(@RequestParam String meet,@RequestParam int rcdate,@RequestParam int rcno,Model model) {
		model.addAttribute("races",raceService.viewrace(meet,rcdate,rcno));
		model.addAttribute("predict", raceService.read(meet,rcdate,rcno));
		return "viewdetailrace";
	}
}

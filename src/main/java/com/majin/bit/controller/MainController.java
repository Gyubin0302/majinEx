package com.majin.bit.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.majin.bit.dto.SearchTermsDto;
import com.majin.bit.service.SearchTermsService;

@Controller
public class MainController {

	// 인검 서비스
	@Autowired
	SearchTermsService searchtermsservice;

	// 실검 전체 컨트롤러
	@RequestMapping("/")
	public String SearchTermsList(Model model, HttpServletRequest request) {

		List<SearchTermsDto> SearchTermsList = new ArrayList<>();
		SearchTermsList = searchtermsservice.getSearchtermsList();
		System.out.println("통합 실검 :"+SearchTermsList);
		model.addAttribute("SearchTermsList", SearchTermsList);

		return "/index";
	}

	// 말 실검
	@RequestMapping("/SearchTermsHorses")
	public String SearchTermsHoreseList(Model model, HttpServletRequest request) {

		List<SearchTermsDto> SearchTermsList = new ArrayList<>();
		SearchTermsList = searchtermsservice.getSearchTermsHoreseList();
		System.out.println("말실검 나와라~" + SearchTermsList);
		model.addAttribute("SearchTermsList", SearchTermsList);

		return "index";
	}

	// 기수 실검
	@RequestMapping("/SearchTermsJokey")
	public String SearchTermsJockeyList(Model model, HttpServletRequest request) {

		List<SearchTermsDto> SearchTermsList = new ArrayList<>();
		SearchTermsList = searchtermsservice.getSearchTermsJockeyList();
		System.out.println("기수실검 나와라~" + SearchTermsList);
		model.addAttribute("SearchTermsList", SearchTermsList);

		return "index";
	}

	// 조교사 실검
	@RequestMapping("/SearchTermsTrainer")
	public String SearchTermsTrainerList(Model model, HttpServletRequest request) {
		List<SearchTermsDto> SearchTermsList = new ArrayList<>();
		SearchTermsList = searchtermsservice.getSearchTermsTrainerList();
		model.addAttribute("SearchTermsList", SearchTermsList);

		return "index";
	}

}

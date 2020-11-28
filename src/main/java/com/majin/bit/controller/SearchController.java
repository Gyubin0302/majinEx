package com.majin.bit.controller;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.majin.bit.dto.HorseDto;
import com.majin.bit.dto.JkDto;
import com.majin.bit.dto.Pagination;
import com.majin.bit.dto.SearchDto;
import com.majin.bit.dto.TrDto;
import com.majin.bit.service.HorseService;
import com.majin.bit.service.SearchService;
import com.majin.bit.service.TrainerService;
import com.majin.bit.service.jockeyService;
import com.majin.bit.util.RecommendProcess;

import oshi.jna.platform.linux.Libc.Sysinfo;

@Controller
public class SearchController {
	
	@Autowired
	private SearchService searchService;
	
	@Autowired 
	private HorseService horseService;
	
	@Autowired
	private jockeyService jockeyService;
	
	@Autowired
	private TrainerService trainerService;
	
//	
//	@RequestMapping(value="/mulSearch")
//    public String training(){
//        return "/MultiCheck";
//    }
//	
//	@RequestMapping(value = "check.do", method = RequestMethod.GET)
//	public String checkBoxTest(HttpServletRequest request) throws IOException, ServletException{
//		SearchDto bs = new SearchDto();
//		String[] attrList = request.getParameterValues("attract");
//		String[] areaList = request.getParameterValues("area");
//		String searchType = request.getParameter("searchType");
//		String searchWord = request.getParameter("searchWord");
//		
//		bs.setAttrList(attrList);
//		bs.setAreaList(areaList);
//		bs.setSearchType(searchType);
//		bs.setSearchWord(searchWord);
//		System.out.println("BoardSearch : "+ bs);
//
//		List<Board> searchList = boardSearchDao.boardSearchList(bs);
//		System.out.println("Board : "+ searchList);
//		
//		return "list";
//		
//	}
	
	@RequestMapping(value="/search/recommendWord", method = RequestMethod.POST)
	public String recommendWord(String search, Model model) throws FileNotFoundException{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		RecommendProcess recommend = new RecommendProcess();
		List<String> returnRecommend = new ArrayList<String>();
		Collection<String> recommendWord =  recommend.recommender(auth.getName(), search);
		
		if(recommendWord != null) {
			returnRecommend = recommendWord.stream().collect(Collectors.toList()); // String[] test = recommendWord.toArray(new String[0]);
			model.addAttribute("recommendWord", returnRecommend);	
		} else {
			model.addAttribute("recommendWord", "추천단어가 없습니다.");
		}
		
		return "/fragments/header :: #recommendWord";
	}
	
	@RequestMapping(value="/search", method = RequestMethod.POST)
    public String search(@RequestParam String search, @RequestParam int pageNo, Model model){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		FileWriter userText;
		try {
			if(auth.getName() != "anonymousUser") {
				userText = new FileWriter("D:/final/userText/" + auth.getName() + "_save.txt", true); // 파일 이어쓰기
				userText.write(search + "\n");
				userText.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		List<HorseDto> horseList = horseService.searchHorse(search);
//		Pagination horsePagination = new Pagination(horseList.size(), pageNo, search);
//		List<HorseDto> horsePaging = horseService.searchPagingHorse(horsePagination);
		
		List<TrDto> trainerList = trainerService.searchTrainer(search);
//		Pagination trainerPagination = new Pagination(trainerList.size(), pageNo, search);
//		List<TrDto> trainerPaging = trainerService.searchPagingTrainer(trainerPagination);
//		
		List<JkDto> jockeyList = jockeyService.searchJockey(search);
//		Pagination jockeyPagination = new Pagination(jockeyList.size(), pageNo, search);
//		List<JkDto> jockeyPaging = jockeyService.searchPagingJockey(jockeyPagination);
		
		model.addAttribute("searchHorse", horseList);
//		model.addAttribute("horsePagination", horsePagination);
		model.addAttribute("searchTrainer", trainerList);
//		model.addAttribute("trainerPagination", trainerPagination);
		model.addAttribute("searchJockey", jockeyList);
//		model.addAttribute("jockeyPagination", jockeyPagination);
		model.addAttribute("search", search);

        return "mainSearch";
    }
}

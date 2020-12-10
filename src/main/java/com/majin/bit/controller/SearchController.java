package com.majin.bit.controller;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.majin.bit.dto.HorseDto;
import com.majin.bit.dto.JkDto;
import com.majin.bit.dto.MultiSearchRaceDto;
import com.majin.bit.dto.Pagination;
import com.majin.bit.dto.RaceHorseDto;
import com.majin.bit.dto.TrDto;
import com.majin.bit.service.HorseService;
import com.majin.bit.service.SearchService;
import com.majin.bit.service.TrainerService;
import com.majin.bit.service.jockeyService;
import com.majin.bit.util.RecommendProcess;

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
	
	
	@RequestMapping(value="/search/mulSearch")
    public String training(@ModelAttribute MultiSearchRaceDto multiSearchRaceDto){
        return "MultiCheck";
    }
	
	@RequestMapping(value = "/search/check", method = RequestMethod.GET)
	public String checkBoxTest(MultiSearchRaceDto multiSearchRaceDto, HttpServletRequest request, Model model, @RequestParam(defaultValue = "1")int pageNo) throws IOException, ServletException{
		
		multiSearchRaceDto.setRcDateList(request.getParameterValues("rcDate"));
		multiSearchRaceDto.setRcDistList(request.getParameterValues("rcDist"));
		multiSearchRaceDto.setRanksList(request.getParameterValues("ranks"));
		multiSearchRaceDto.setWgBudamList(request.getParameterValues("wgBudam"));
		multiSearchRaceDto.setChulNoList(request.getParameterValues("chulNo"));
		
		System.out.println("BoardSearch : "+ multiSearchRaceDto);
		
		int searchCount = searchService.raceHorseSearchCount(multiSearchRaceDto);

		Pagination raceHorsePagination = new Pagination(searchCount, pageNo);
		
		Map<String, Object> raceMap = new HashMap<String, Object>();
		raceMap.put("meet", multiSearchRaceDto.getMeet());
		raceMap.put("rcDate", multiSearchRaceDto.getRcDate());
		raceMap.put("rcDateList", multiSearchRaceDto.getRcDateList());
		raceMap.put("hrName", multiSearchRaceDto.getHrName());
		raceMap.put("jkName", multiSearchRaceDto.getJkName());
		raceMap.put("trName", multiSearchRaceDto.getTrName());
		raceMap.put("rcDist", multiSearchRaceDto.getRcDist());
		raceMap.put("rcDistList", multiSearchRaceDto.getRcDistList());
		raceMap.put("ranks", multiSearchRaceDto.getRanks());
		raceMap.put("ranksList", multiSearchRaceDto.getRanksList());
		raceMap.put("wgBudam", multiSearchRaceDto.getWgBudam());
		raceMap.put("wgBudamList", multiSearchRaceDto.getWgBudamList());
		raceMap.put("chulNo", multiSearchRaceDto.getChulNo());
		raceMap.put("chulNoList", multiSearchRaceDto.getChulNoList());
		raceMap.put("startIndex", raceHorsePagination.getStartIndex());
		raceMap.put("pageSize", raceHorsePagination.getPageSize());

		List<RaceHorseDto> searchListPaging = searchService.multiSearchRace(raceMap);

		model.addAttribute("searchList", searchListPaging);
		model.addAttribute("raceHorsePagination", raceHorsePagination);

		return "MultiCheck";
		
	}
	
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
		List<TrDto> trainerList = trainerService.searchTrainer(search);
		List<JkDto> jockeyList = jockeyService.searchJockey(search);

		model.addAttribute("searchHorse", horseList);
		model.addAttribute("searchTrainer", trainerList);
		model.addAttribute("searchJockey", jockeyList);
		model.addAttribute("search", search);

        return "mainSearch";
    }
}

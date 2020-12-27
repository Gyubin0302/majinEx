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
import com.majin.bit.dto.SearchTermsDto;
import com.majin.bit.dto.TrDto;
import com.majin.bit.service.HorseService;
import com.majin.bit.service.SearchService;
import com.majin.bit.service.SearchTermsService;
import com.majin.bit.service.TrainerService;
import com.majin.bit.service.jockeyService;
import com.majin.bit.util.RecommendProcess;

@Controller
@RequestMapping("/search")
public class SearchController {

	@Autowired
	private SearchService searchService;

	@Autowired
	private HorseService horseService;

	@Autowired
	private jockeyService jockeyService;

	@Autowired
	private TrainerService trainerService;

	@Autowired
	private SearchTermsService searchtermsservice;
	
	@RequestMapping(value = "/mulSearch")
	public String training(@ModelAttribute MultiSearchRaceDto multiSearchRaceDto) {
		return "MultiCheck";
	}

	@RequestMapping(value = "/check", method = RequestMethod.POST)
	public String checkBoxTest(MultiSearchRaceDto multiSearchRaceDto, HttpServletRequest request, Model model,
			@RequestParam(defaultValue = "1") int pageNo) throws IOException, ServletException {

		multiSearchRaceDto.setRcDateList(request.getParameterValues("rcDate"));
		multiSearchRaceDto.setRcDistList(request.getParameterValues("rcDist"));
		multiSearchRaceDto.setRanksList(request.getParameterValues("ranks"));
		multiSearchRaceDto.setWgBudamList(request.getParameterValues("wgBudam"));
		multiSearchRaceDto.setChulNoList(request.getParameterValues("chulNo"));

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

	@RequestMapping(value = "/recommendWord", method = RequestMethod.POST)
	public String recommendWord(String search, Model model) throws FileNotFoundException {
		RecommendProcess recommend = new RecommendProcess();
		List<String> returnRecommend = new ArrayList<String>();
		Collection<String> recommendWord = recommend.recommender(search);

		if (recommendWord != null) {
			returnRecommend = recommendWord.stream().collect(Collectors.toList()); // String[] test =
																					// recommendWord.toArray(new
																					// String[0]);
		} else {
			returnRecommend.add("정확한 단어를 입력해주세요.");
		}
		model.addAttribute("recommendWord", returnRecommend);
		return "/fragments/header :: #recommendWord";
	}

	@RequestMapping(value = "/total", method = RequestMethod.POST)
	public String search(@RequestParam String search, @RequestParam int pageNo, Model model)
			throws FileNotFoundException {
		List<HorseDto> horseList = horseService.searchHorse(search);
		List<TrDto> trainerList = trainerService.searchTrainer(search);
		List<JkDto> jockeyList = jockeyService.searchJockey(search);

		// 실시간 검색어 부분
		SearchTermsDto TermsDto = new SearchTermsDto();
		TermsDto.setSearchword(search);

		if (searchtermsservice.getSearchTermsHorse(search) > 0) {
			TermsDto.setFale(0);
			searchtermsservice.totalSearchTerms(TermsDto);

		} else if (searchtermsservice.getSearchTermsJockey(search) > 0) {
			TermsDto.setFale(1);
			searchtermsservice.totalSearchTerms(TermsDto);

		} else if (searchtermsservice.getSearchTermsTrainer(search) > 0) {
			TermsDto.setFale(2);
			searchtermsservice.totalSearchTerms(TermsDto);
		}

		model.addAttribute("searchHorse", horseList);
		model.addAttribute("searchTrainer", trainerList);
		model.addAttribute("searchJockey", jockeyList);
		model.addAttribute("search", search);
		return "mainSearch";
	}

	/**
	 * 조교 상세 정보
	 * 
	 * @param trNo
	 * @param meet
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/detail/trainerDetail", method = RequestMethod.POST)
	public String trainerDetail(String trNo, String meet, Model model) {

		TrDto trDto = new TrDto();
		trDto.setTrNo(trNo);
		trDto.setMeet(meet);

		model.addAttribute("trainerDetail", trainerService.searchOneTrainer(trDto));

		return "trainerDetail";
	}

	/**
	 * 조교 페이징
	 * 
	 * @param model
	 * @param pageNo
	 * @param search
	 * @return
	 */
	@RequestMapping(value = "/trainerSearchPaging", method = RequestMethod.POST)
	public String trainseSearchPaging(Model model, int pageNo, String search) {

		List<TrDto> trainerList = trainerService.searchTrainer(search);
		Pagination trainerPagination = new Pagination(trainerList.size(), pageNo, search);
		List<TrDto> trainerPaging = trainerService.searchPagingTrainer(trainerPagination);

		model.addAttribute("searchTrainer", trainerPaging);
		model.addAttribute("trainerPagination", trainerPagination);

		return "trainerPaging";
	}

	/**
	 * 경주마 상세 정보
	 * 
	 * @param hrNo
	 * @param meet
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/detail/horseDetail", method = RequestMethod.POST)
	public String horseDetail(String hrNo, String meet, Model model) {

		HorseDto horseDto = new HorseDto();
		horseDto.setHrNo(hrNo);
		horseDto.setMeet(meet);

		model.addAttribute("horseDetail", horseService.searchOneHorse(horseDto));

		return "horseDetail";
	}

	/**
	 * 경주마 페이징
	 * 
	 * @param model
	 * @param pageNo
	 * @param search
	 * @return
	 */
	@RequestMapping(value = "/horseSearchPaging", method = RequestMethod.POST)
	public String horseSearchPaging(Model model, @RequestParam(defaultValue = "1") int pageNo, String search) {

		List<HorseDto> horseList = horseService.searchHorse(search);
		Pagination horsePagination = new Pagination(horseList.size(), pageNo, search);
		List<HorseDto> horsePaging = horseService.searchPagingHorse(horsePagination);

		model.addAttribute("searchHorse", horsePaging);
		model.addAttribute("horsePagination", horsePagination);

		return "horsePaging";
	}

	/**
	 * 기수 상세 정보
	 * 
	 * @param jkNo
	 * @param meet
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/detail/jockeyDetail", method = RequestMethod.POST)
	public String jockeyDetail(String jkNo, String meet, Model model) {

		JkDto jkDto = new JkDto();
		jkDto.setJkNo(jkNo);
		jkDto.setMeet(meet);

		model.addAttribute("jockeyDetail", jockeyService.searchOneJockey(jkDto));

		return "jockeyDetail";
	}

	/**
	 * 기수 페이징
	 * 
	 * @param model
	 * @param pageNo
	 * @param search
	 * @return
	 */
	@RequestMapping(value = "/jockeySearchPaging", method = RequestMethod.POST)
	public String jockeySearchPaging(Model model, int pageNo, String search) {

		List<JkDto> jockeyList = jockeyService.searchJockey(search);
		Pagination jockeyPagination = new Pagination(jockeyList.size(), pageNo, search);
		List<JkDto> jockeyPaging = jockeyService.searchPagingJockey(jockeyPagination);

		model.addAttribute("searchJockey", jockeyPaging);
		model.addAttribute("jockeyPagination", jockeyPagination);

		return "jockeyPaging";
	}

}

package com.majin.bit.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.majin.bit.dto.HorseDto;
import com.majin.bit.dto.JkDto;
import com.majin.bit.dto.Pagination;
import com.majin.bit.dto.TrDto;
import com.majin.bit.service.jockeyService;
import com.majin.bit.util.JockeyCrawling;

@Controller
public class JockeyController {
	
	@Autowired
	private jockeyService jockeyService;
	
	/**
	 * 기수 크롤링 후 MYSQL DB에 저장
	 * @param request
	 * @param model
	 * @param meet
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping(value = "/jkapi", method = {RequestMethod.GET, RequestMethod.POST})
	public String jk(HttpServletRequest request, Model model, String meet) throws IOException, ParseException {
		String inputLine;
		String buffer = "";
		
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B551015/API12/jockeyInfo");
		urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=YsfeAQ1K0KPH1fOqYRLcvfqOP2P6Mo2iQOiZSumF4bMSlfyWjdPg4NWPu7Y5ms%2Fql9n2oi4dQbNq2bISj%2Bi4Hg%3D%3D");
		urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8")); /*한 페이지 결과 수*/
		urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
		urlBuilder.append("&" + URLEncoder.encode("meet","UTF-8") + "=" + URLEncoder.encode(meet, "UTF-8")); /*시행경마장구분(1.서울,2.제주,3.부산)*/

		System.out.println(urlBuilder.toString());
		
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
		
		urlConnection.setDoInput(true);
		urlConnection.setDoOutput(true);
		urlConnection.setRequestMethod("GET");
		urlConnection.setRequestProperty("Accept", "application/json");
		System.out.println("Response code : " + urlConnection.getResponseCode());
		
		BufferedReader bufferedReader;
		if(urlConnection.getResponseCode() >= 200 && urlConnection.getResponseCode() <= 300) {
			bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"));
		}else {
			bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getErrorStream(), "utf-8"));
		}
		
		while((inputLine = bufferedReader.readLine()) != null) {
			buffer += inputLine.trim();
		}
		
		bufferedReader.close();
		urlConnection.disconnect();
		
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(buffer);
		
		List<JkDto> jkList = jockeyService.jockeyInsert(jsonObject, meet);

		model.addAttribute("jkList", jkList);
		
		return "admin/APIInsert :: #API";
		
	}
	
	/**
	 * 기수 상세 정보
	 * @param jkNo
	 * @param meet
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/search/jockeyDetail", method = RequestMethod.POST)
	public String jockeyDetail(String jkNo, String meet, Model model) {
		
		JkDto jkDto = new JkDto();
		jkDto.setJkNo(jkNo);
		jkDto.setMeet(meet);
		
		model.addAttribute("jockeyDetail",jockeyService.searchOneJockey(jkDto));
		
		return "jockeyDetail";
	}
	
	/**
	 * 기수 페이징
	 * @param model
	 * @param pageNo
	 * @param search
	 * @return
	 */
	@RequestMapping(value = "/search/jockeySearchPaging", method = RequestMethod.POST)
	public String horseSearchPaging(Model model, int pageNo, String search) {
		
		List<JkDto> jockeyList = jockeyService.searchJockey(search);
		Pagination jockeyPagination = new Pagination(jockeyList.size(), pageNo, search);
		List<JkDto> jockeyPaging = jockeyService.searchPagingJockey(jockeyPagination);
		
		model.addAttribute("searchJockey", jockeyPaging);
		model.addAttribute("jockeyPagination", jockeyPagination);

		return "jockeyPaging";
	}

}

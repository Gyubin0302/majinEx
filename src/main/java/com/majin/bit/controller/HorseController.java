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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.majin.bit.dto.HorseDto;
import com.majin.bit.dto.Pagination;
import com.majin.bit.dto.TrDto;
import com.majin.bit.service.HorseService;
import com.majin.bit.service.TrainerService;
import com.majin.bit.util.HorseCrawling;


@Controller
public class HorseController {
	
	@Autowired
	private HorseService horseService;

	@Autowired
	private TrainerService trainerService;
	
	@RequestMapping(value="/admin")
    public String apitest(){
        return "admin/APIInsert";
    }
	
	@RequestMapping(value = "/horseapi", method = {RequestMethod.GET, RequestMethod.POST})
	public String horse(HttpServletRequest request, Model model, String meet) throws IOException, ParseException {
		String inputLine;
		String buffer = "";
		
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B551015/API8/raceHorseInfo");
		urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=YsfeAQ1K0KPH1fOqYRLcvfqOP2P6Mo2iQOiZSumF4bMSlfyWjdPg4NWPu7Y5ms%2Fql9n2oi4dQbNq2bISj%2Bi4Hg%3D%3D");
		urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("30000", "UTF-8")); /*한 페이지 결과 수 25186 */
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
		
		List<HorseDto> horseList = horseService.horseInsert(jsonObject, meet);
		
		
		model.addAttribute("horseList", horseList);
		
		return "admin/APIInsert :: #API";
				
	}
	
	@RequestMapping(value = "/search/horseDetail", method = RequestMethod.POST)
	public String horseDetail(String hrNo, String meet, Model model) {
		
		HorseDto horseDto = new HorseDto();
		horseDto.setHrNo(hrNo);
		horseDto.setMeet(meet);

		model.addAttribute("horse", horseService.searchOneHorse(horseDto));
		
		return "horseDetail";
	}
	
	@RequestMapping(value = "/search/horseSearchPaging", method = RequestMethod.POST)
	public String horseSearchPaging(Model model, @RequestParam(defaultValue = "1")int pageNo, String search) {

		List<HorseDto> horseList = horseService.searchHorse(search);
		Pagination horsePagination = new Pagination(horseList.size(), pageNo, search);
		List<HorseDto> horsePaging = horseService.searchPagingHorse(horsePagination);

		model.addAttribute("searchHorse", horsePaging);
		model.addAttribute("horsePagination", horsePagination);

		return "index :: #information";
	}

}

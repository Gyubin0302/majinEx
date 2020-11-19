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

import com.majin.bit.dto.JkDto;
import com.majin.bit.service.jockeyService;
import com.majin.bit.util.JockeyCrawling;

@Controller
public class JockeyController {
	
	@Autowired
	private jockeyService jockeyService;
	
	@RequestMapping(value = "/jkapi", method = {RequestMethod.GET, RequestMethod.POST})
	public String jk(HttpServletRequest request, Model model, String meet) throws IOException, ParseException {
		String inputLine;
		String buffer = "";
		
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B551015/API12/jockeyInfo");
		urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=YsfeAQ1K0KPH1fOqYRLcvfqOP2P6Mo2iQOiZSumF4bMSlfyWjdPg4NWPu7Y5ms%2Fql9n2oi4dQbNq2bISj%2Bi4Hg%3D%3D");
		urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("582", "UTF-8")); /*한 페이지 결과 수*/
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
//		System.out.println(buffer.toString());
		
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(buffer);
		JSONObject jsonObject2 = (JSONObject) jsonObject.get("response");
		JSONObject jsonObject3 = (JSONObject) jsonObject2.get("body");
		JSONObject jsonObject4 = (JSONObject) jsonObject3.get("items");
		JSONArray jsonArray = (JSONArray) jsonObject4.get("item");
		

		List<JkDto> jkList = new ArrayList<>();
		JSONObject jsonItem = null;
		for (int i = 0; i < jsonArray.size(); i++) {
			jsonItem = (JSONObject)jsonArray.get(i);
			JkDto jkDto = new JkDto();
			JockeyCrawling jockeyCrawling = new JockeyCrawling();
			Map<String, Object> jokey = jockeyCrawling.javaCrawling(jsonItem.get("jkNo").toString(), meet);
			jkDto.setJkNo(jsonItem.get("jkNo").toString());
			jkDto.setJkName(jsonItem.get("jkName").toString());
			
			jkDto.setPart(jsonItem.get("part").toString());
			jkDto.setjRweight(jsonItem.get("wgPart").toString());
			jkDto.setDebut(jsonItem.get("debut").toString());
			jkDto.setjYearTotal(jokey.get("jYearTotal").toString());
			jkDto.setjAllTotal(jokey.get("jAllTotal").toString());
			jkDto.setjConsecutiveWinningP(jokey.get("jConsecutiveWinningP").toString());
			jkDto.setjComplementRyRate(jokey.get("jComplementRyRate").toString());
			jkDto.setjWinningP(jokey.get("jWinningP").toString());
			jkDto.setTrNo(jokey.get("trNo").toString()); 
			jkDto.setSpDate(jsonItem.get("spDate").toString());
			jkList.add(jkDto);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", jkList);
		
		boolean insertStatus = jockeyService.jockeyInsert(map);
		
		if(insertStatus == true) {
			System.out.println("insert 성공");
		}else {
			System.out.println("insert 실패");
		}

		model.addAttribute("jkList", jkList);
		
		return "admin/APIInsert :: #API";
		
	}
	
	@RequestMapping(value = "/jockeyDetail", method = RequestMethod.POST)
	public String jockeyDetail(String jkNo, Model model) {

		model.addAttribute("jockey",jockeyService.searchOneJockey(jkNo));
		
		return "jockeyDetail";
	}
}

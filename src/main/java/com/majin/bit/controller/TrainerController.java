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


import com.majin.bit.dto.TrDto;
import com.majin.bit.service.TrainerService;
import com.majin.bit.util.TrainerCrawling;

@Controller
public class TrainerController {

	@Autowired
	private TrainerService trainerService;
	
	@RequestMapping(value = "/trapi", method = {RequestMethod.GET, RequestMethod.POST})
	public String tr(HttpServletRequest request, Model model, String meet) throws IOException, ParseException {
		String inputLine;
		String buffer = "";
		System.out.println(meet);
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B551015/API19/trainerInfo");
		urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=YsfeAQ1K0KPH1fOqYRLcvfqOP2P6Mo2iQOiZSumF4bMSlfyWjdPg4NWPu7Y5ms%2Fql9n2oi4dQbNq2bISj%2Bi4Hg%3D%3D");
		urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("210", "UTF-8")); /*한 페이지 결과 수*/
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
		//System.out.println(buffer.toString());
		
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(buffer);
		JSONObject jsonObject2 = (JSONObject) jsonObject.get("response");
		JSONObject jsonObject3 = (JSONObject) jsonObject2.get("body");
		JSONObject jsonObject4 = (JSONObject) jsonObject3.get("items");
		JSONArray jsonArray = (JSONArray) jsonObject4.get("item");
		
		List<TrDto> trList = new ArrayList<>();
		JSONObject jsonItem = null;
		for (int i = 0; i < jsonArray.size(); i++) {
			jsonItem = (JSONObject)jsonArray.get(i);
			TrDto trDto = new TrDto();
			TrainerCrawling trainerCrawling = new TrainerCrawling(); 
			Map<String, Object> trainer = trainerCrawling.javaCrawling(jsonItem.get("trNo").toString(), meet);
			trDto.setTrName(jsonItem.get("trName").toString());
			trDto.setTrNo(jsonItem.get("trNo").toString());
			trDto.setPart(jsonItem.get("part").toString());
			trDto.setDebut(jsonItem.get("stDate").toString());
			trDto.settYearTotal(trainer.get("tYearTotal").toString());
			trDto.settAllTotal(trainer.get("tAllTotal").toString());
			trDto.settConsecutiveWinningP(trainer.get("ConsecutiveWinningP").toString());
			trDto.settComplementRyRate(trainer.get("ComplementaryRate").toString());
			trDto.settWinningP(trainer.get("WinningP").toString());
			trList.add(trDto);
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", trList);
		
		boolean insertStatus = trainerService.trainerInsert(map);
		
		if(insertStatus == true) {
			System.out.println("insert 성공");
		}else {
			System.out.println("insert 실패");
		}
	
		model.addAttribute("trList", trList);
		return "admin/APIInsert :: #API";
		
	}
	
	@RequestMapping(value = "/trainerDetail", method = RequestMethod.POST)
	public String trainerDetail(String trNo, Model model) {

		model.addAttribute("trainer",trainerService.searchOneTrainer(trNo));
		
		return "trainerDetail";
	}
	
}

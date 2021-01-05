package com.majin.bit.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.majin.bit.dto.HorseDto;
import com.majin.bit.dto.JkDto;
import com.majin.bit.dto.Member;
import com.majin.bit.dto.TrDto;
import com.majin.bit.service.HorseService;
import com.majin.bit.service.MemberService;
import com.majin.bit.service.RaceHorseService;
import com.majin.bit.service.TrainerService;
import com.majin.bit.service.jockeyService;
import com.majin.bit.util.RcDateCrawling;


@Controller
@RequestMapping("/admin")
public class AdminController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RaceHorseService raceHorseService;
	
	@Autowired
	private jockeyService jockeyService;
	
	@Autowired
	private HorseService horseService;
	
	@Autowired
	private TrainerService trainerService;
	
	@Autowired
	private MemberService memberService;
	
	@RequestMapping(value="/main")
    public String apitest(){
        return "admin/APIInsert";
    }
	
	@ResponseBody
	@RequestMapping(value = "/raceHorse", method = RequestMethod.POST)
	public String raceHorseInsert(String meet) throws IOException, ParseException {
		
		Calendar calendar = Calendar.getInstance();
		int nowYear = calendar.get(Calendar.YEAR);
		int startYear = 0;
		if ( meet.equals("1")) {
			startYear = 1985;
		} else if (meet.equals("3")) {
			startYear = 2004;
		} else {
			startYear = 1990;
		}

		List<Object> jsonObj = new ArrayList<>();
		while (startYear <= nowYear) {
			
			String inputLine;
			String buffer = "";
			String rc_Year = ""+startYear;
			
			StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B551015/API4/raceResult");
			urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=YsfeAQ1K0KPH1fOqYRLcvfqOP2P6Mo2iQOiZSumF4bMSlfyWjdPg4NWPu7Y5ms%2Fql9n2oi4dQbNq2bISj%2Bi4Hg%3D%3D");
			urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("50000", "UTF-8")); /*한 페이지 결과 수 25186 */
			urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
			urlBuilder.append("&" + URLEncoder.encode("meet","UTF-8") + "=" + URLEncoder.encode(meet, "UTF-8")); /*시행경마장구분(1.서울,2.제주,3.부산)*/
			urlBuilder.append("&" + URLEncoder.encode("rc_year","UTF-8") + "=" + URLEncoder.encode(rc_Year, "UTF-8"));
			
			URL url = new URL(urlBuilder.toString());
			HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
			
			urlConnection.setDoInput(true);
			urlConnection.setDoOutput(true);
			urlConnection.setRequestMethod("GET");
			urlConnection.setRequestProperty("Accept", "application/json");
			LOGGER.info("Response code : " + urlConnection.getResponseCode());
			
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
			JSONObject jsonObject2 = (JSONObject) jsonObject.get("response");
			JSONObject jsonObject3 = (JSONObject) jsonObject2.get("body");
			JSONObject jsonObject4 = (JSONObject) jsonObject3.get("items");
			JSONArray jsonArray = (JSONArray) jsonObject4.get("item");
			
			for (int i = 0; i < jsonArray.size(); i++) {
				jsonObj.add(jsonArray.get(i));
			}
			
			startYear++;
		}

		boolean result = raceHorseService.raceHorseInsert(jsonObj, meet, "raceRsult");
		
		if(result == true) {
			return "성공";
		} else {
			return "실패";
		}

	}
	
	@ResponseBody
	@RequestMapping(value = "/raceHorseNew", method = RequestMethod.POST)
	public String raceHorseInsertNew(String meet) throws IOException, ParseException {

		boolean result = false;
		
		RcDateCrawling rcDateCrawling = new RcDateCrawling();
		List<String> rc_date = rcDateCrawling.rcDateCrawling(meet);
		
		List<Object> jsonObj = new ArrayList<>();
		if(rc_date != null) {
			for (int i = 0; i < rc_date.size(); i++) {
				
				String inputLine;
				String buffer = "";
	
				LOGGER.info("now Date : " + rc_date.get(i));
				
				StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B551015/API26/entrySheet");
				urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=YsfeAQ1K0KPH1fOqYRLcvfqOP2P6Mo2iQOiZSumF4bMSlfyWjdPg4NWPu7Y5ms%2Fql9n2oi4dQbNq2bISj%2Bi4Hg%3D%3D");
				urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10000", "UTF-8"));
				urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/			
				urlBuilder.append("&" + URLEncoder.encode("meet","UTF-8") + "=" + URLEncoder.encode(meet, "UTF-8")); /*시행경마장구분(1.서울,2.제주,3.부산)*/
				urlBuilder.append("&" + URLEncoder.encode("rc_date","UTF-8") + "=" + URLEncoder.encode(rc_date.get(i), "UTF-8")); /* 경주 일자 */
					
				
				URL url = new URL(urlBuilder.toString());
				HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
				
				urlConnection.setDoInput(true);
				urlConnection.setDoOutput(true);
				urlConnection.setRequestMethod("GET");
				urlConnection.setRequestProperty("Accept", "application/json");
				LOGGER.info("Response code : " + urlConnection.getResponseCode());
					
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
				JSONObject jsonObject2 = (JSONObject) jsonObject.get("response");
				JSONObject jsonObject3 = (JSONObject) jsonObject2.get("body");
				JSONObject jsonObject4 = (JSONObject) jsonObject3.get("items");
				JSONArray jsonArray = (JSONArray) jsonObject4.get("item");
				for (int j = 0; j < jsonArray.size(); j++) {
					jsonObj.add(jsonArray.get(j));
				}
			}
			
			result = raceHorseService.raceHorseInsert(jsonObj, meet, "entrySheet");
			if(result == true) {
				return "성공";
			} else {
				return "실패";
			}
		} else {
			return "경기가 없습니다.";
		}

	}
	
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
		
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
		
		urlConnection.setDoInput(true);
		urlConnection.setDoOutput(true);
		urlConnection.setRequestMethod("GET");
		urlConnection.setRequestProperty("Accept", "application/json");
		
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
	 * 경주마 크롤링 후 MYSQL DB에 저장
	 * @param request
	 * @param model
	 * @param meet
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping(value = "/horseapi", method = {RequestMethod.GET, RequestMethod.POST})
	public String horse(HttpServletRequest request, Model model, String meet) throws IOException, ParseException {
		String inputLine;
		String buffer = "";
		
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B551015/API8/raceHorseInfo");
		urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=YsfeAQ1K0KPH1fOqYRLcvfqOP2P6Mo2iQOiZSumF4bMSlfyWjdPg4NWPu7Y5ms%2Fql9n2oi4dQbNq2bISj%2Bi4Hg%3D%3D");
		urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("30000", "UTF-8")); /*한 페이지 결과 수 25186 */
		urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
		urlBuilder.append("&" + URLEncoder.encode("meet","UTF-8") + "=" + URLEncoder.encode(meet, "UTF-8")); /*시행경마장구분(1.서울,2.제주,3.부산)*/
		
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
		
		urlConnection.setDoInput(true);
		urlConnection.setDoOutput(true);
		urlConnection.setRequestMethod("GET");
		urlConnection.setRequestProperty("Accept", "application/json");
		
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
	
	/**
	 * 조교 크롤링 후 MYSQL DB에 저장
	 * @param request
	 * @param model
	 * @param meet
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping(value = "/trapi", method = {RequestMethod.GET, RequestMethod.POST})
	public String tr(HttpServletRequest request, Model model, String meet) throws IOException, ParseException {
		String inputLine;
		String buffer = "";
	
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B551015/API19/trainerInfo");
		urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=YsfeAQ1K0KPH1fOqYRLcvfqOP2P6Mo2iQOiZSumF4bMSlfyWjdPg4NWPu7Y5ms%2Fql9n2oi4dQbNq2bISj%2Bi4Hg%3D%3D");
		urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8")); /*한 페이지 결과 수*/
		urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
		urlBuilder.append("&" + URLEncoder.encode("meet","UTF-8") + "=" + URLEncoder.encode(meet, "UTF-8")); /*시행경마장구분(1.서울,2.제주,3.부산)*/
		
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
		
		urlConnection.setDoInput(true);
		urlConnection.setDoOutput(true);
		urlConnection.setRequestMethod("GET");
		urlConnection.setRequestProperty("Accept", "application/json");
		
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
		
		List<TrDto> trList = trainerService.trainerInsert(jsonObject, meet);
		
		
		model.addAttribute("trList", trList);
		return "admin/APIInsert :: #API";
		
	}
	
	@RequestMapping(value="/memberBan", method = RequestMethod.GET)
	public String memberBan(Model model) {
		List<Member> members = memberService.findAll();
		model.addAttribute("members",members);
		return "memberBan";
	}
	
	@RequestMapping(value="/memberBan1", method = RequestMethod.GET)
	public String memberBanId(@RequestParam String id) {
		memberService.disableId(id);
		return "redirect:/admin/memberBan";
	}

}

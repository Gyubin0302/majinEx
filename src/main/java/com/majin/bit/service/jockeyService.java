package com.majin.bit.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.nd4j.nativeblas.Nd4jCpu.boolean_and;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.majin.bit.dao.JockeyDao;
import com.majin.bit.dto.JkDto;
import com.majin.bit.util.JockeyCrawling;

@Service
@Transactional
public class jockeyService {

	@Autowired
	JockeyDao jockeyDao;

	public List<JkDto> jockeyInsert(JSONObject jsonObject, String meet) {

		JSONObject jsonObject2 = (JSONObject) jsonObject.get("response");
		JSONObject jsonObject3 = (JSONObject) jsonObject2.get("body");
		JSONObject jsonObject4 = (JSONObject) jsonObject3.get("items");
		JSONArray jsonArray = (JSONArray) jsonObject4.get("item");

		List<JkDto> jkList = new ArrayList<>();
		JSONObject jsonItem = null;
		
		long startTime = System.currentTimeMillis();
		
		for (int i = 0; i < jsonArray.size(); i++) {
			jsonItem = (JSONObject) jsonArray.get(i);
			JkDto jkDto = new JkDto();
			JockeyCrawling jockeyCrawling = new JockeyCrawling();
			Map<String, Object> jokey = jockeyCrawling.javaCrawling(jsonItem.get("jkNo").toString(), meet);
			jkDto.setJkNo(jsonItem.get("jkNo").toString());
			jkDto.setJkName(jsonItem.get("jkName").toString());

			jkDto.setPart(jsonItem.get("part").toString());
			jkDto.setJRweight(jsonItem.get("wgPart").toString());
			jkDto.setDebut(jsonItem.get("debut").toString());
			jkDto.setJYearTotal(jokey.get("jYearTotal").toString());
			jkDto.setJAllTotal(jokey.get("jAllTotal").toString());
			jkDto.setJConsecutiveWinningP(jokey.get("jConsecutiveWinningP").toString());
			jkDto.setJComplementRyRate(jokey.get("jComplementRyRate").toString());
			jkDto.setJWinningP(jokey.get("jWinningP").toString());
			jkDto.setTrNo(jokey.get("trNo").toString());
			jkDto.setSpDate(jsonItem.get("spDate").toString());
			jkDto.setMeet(jokey.get("meet").toString());
			jkDto.setAct("09");
			jkList.add(jkDto);
			System.out.println(jkDto);
		}
		
		long endTime = System.currentTimeMillis();
		long completeTime = (endTime - startTime) / 1000;
		System.out.println("걸린 시간 : " + completeTime);

		System.out.println("가져온 기수의 사이즈 : " + jkList.size());

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", jkList);

		boolean insertStatus = jockeyDao.jockeyInsert(map);

		if (insertStatus == true) {
			System.out.println("insert 성공");
		} else {
			System.out.println("insert 실패");
		}
		return jkList;
	}

	public List<JkDto> searchJockey(String search) {
		return jockeyDao.searchJockey(search);
	}

	public JkDto searchOneJockey(String jkNo) {
		return jockeyDao.searchOneJockey(jkNo);
	}
}

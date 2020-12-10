package com.majin.bit.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.majin.bit.dao.RaceHorseDao;
import com.majin.bit.dto.RaceHorseDto;

@Service
@Transactional
public class RaceHorseService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	RaceHorseDao raceHorseDao;

	public boolean raceHorseInsert(List<Object> jsonObj, String meet, String code) {

		LOGGER.info("트랜젝션 sqlSession 배치 테스트 시작");
		int perPageNum = 1000;

		LOGGER.info("트랜젝션 배치 전체 데이터 수 : " + jsonObj.size());
		LOGGER.info("트랜젝션 배치 루프 당 데이터 수 : " + perPageNum);

		boolean result = false;
		JSONObject jsonItem = null;

		List<Map<String, Object>> raceHorseList = new ArrayList<>();
		int j = 0;
		
		String nation;
		
		if(code.equals("entrySheet")) { // api에서 국적 key 값이 달라서 넣어 줌
			nation = "prd";
		} else {
			nation = "name";
		}
		
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < jsonObj.size(); i++) {
			
			jsonItem = (JSONObject) jsonObj.get(i);
			Map<String, Object> map = new HashMap<>();
			if (jsonItem.containsKey("meet") && jsonItem.containsKey("rcDate") && jsonItem.containsKey("rcDay") // 지역, 경주일자, 경주요일
					&& jsonItem.containsKey("rcNo") &&  jsonItem.containsKey("rcDist") && jsonItem.containsKey("rank") // 경주번호, 경주거리, 등급조건
					&& jsonItem.containsKey("wgBudam") && jsonItem.containsKey("age") && jsonItem.containsKey("sex") // 부담중량, 나이, 성별
					&& jsonItem.containsKey("rating") && jsonItem.containsKey(nation) && jsonItem.containsKey("chulNo") // 레이팅, 산지, 출주번호
					&& jsonItem.containsKey("hrName") && jsonItem.containsKey("hrNo") && jsonItem.containsKey("trName") // 마명, 마번, 조교명
					&& jsonItem.containsKey("trNo") && jsonItem.containsKey("jkNo") && jsonItem.containsKey("jkName")) // 조교번호, 기수번호, 기수명
			{
				
				map.put("rcDate", jsonItem.get("rcDate").toString());
				map.put("rcDay", jsonItem.get("rcDay").toString());
				map.put("rcNo", jsonItem.get("rcNo").toString());
				map.put("rcDist", jsonItem.get("rcDist").toString());
				map.put("ranks", jsonItem.get("rank").toString());
				map.put("wgBudam", jsonItem.get("wgBudam").toString());
				map.put("age", jsonItem.get("age").toString());
				map.put("sex", jsonItem.get("sex").toString());
				map.put("rating", jsonItem.get("rating").toString());
				map.put("nation", jsonItem.get(nation).toString());
				map.put("chulNo", jsonItem.get("chulNo").toString());
				map.put("hrName", jsonItem.get("hrName").toString());
				map.put("hrNo", jsonItem.get("hrNo").toString());
				map.put("trName", jsonItem.get("trName").toString());
				map.put("trNo", jsonItem.get("trNo").toString());
				map.put("jkName", jsonItem.get("jkName").toString());
				map.put("jkNo", jsonItem.get("jkNo").toString());
				
				if(jsonItem.get("meet").toString().equals("서울") && jsonItem.get("hrName").toString().substring(0,1).equals("[")) {
					map.put("meet", "3");
				} else if(jsonItem.get("meet").toString().equals("부산경남") && jsonItem.get("hrName").toString().substring(0,1).equals("[")) {
					map.put("meet", "1");
				} else {
					map.put("meet", meet);
				}
				raceHorseList.add(map);
				j++;	
			}
			
			if (j == 999 || i == jsonObj.size() - 1) {
				try {
					Map<String, Object> params = new HashMap<>();
					params.put("list", raceHorseList);
					result = raceHorseDao.raceHorseInsert(params);
				} catch (Exception e) {
					e.printStackTrace();
				}
				raceHorseList.clear();
				j = 0;
			}

		}
		
		long endTime = System.currentTimeMillis();
		long resultTime = endTime - startTime;
		LOGGER.info("트랜젝션 sqlSession 배치 테스트 종료");
		LOGGER.info("트랜젝션 sqlSession 배치" + " 소요시간  : " + resultTime / 1000 + "초(" + resultTime + ")");

		return result;
	}

}

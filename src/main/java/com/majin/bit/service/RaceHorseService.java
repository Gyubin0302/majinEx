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

	// Sql SessionFactory ( 배치 처리 )
	private final SqlSessionFactory sqlSessionFactory;

	public RaceHorseService(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	@Autowired
	RaceHorseDao raceHorseDao;

	public boolean raceHorseInsert(List<Object> jsonObj, String meet) {

		LOGGER.info("트랜젝션 sqlSession 배치 테스트 시작");
		int totalCount = jsonObj.size();
		int perPageNum = 1000;
		int maxPageNum = (int) Math.ceil((double) totalCount / (double) perPageNum);
		maxPageNum = maxPageNum == 0 ? 1 : maxPageNum;

		LOGGER.info("트랜젝션 배치 전체 데이터 수 : " + totalCount);
		LOGGER.info("트랜젝션 배치 루프 당 데이터 수 : " + perPageNum);

		boolean result = false;
		JSONObject jsonItem = null;

		List<Map<String, Object>> raceHorseList = new ArrayList<>();
		int j = 0;
		
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < jsonObj.size(); i++) {
			
			jsonItem = (JSONObject) jsonObj.get(i);
			Map<String, Object> map = new HashMap<>();
			if (jsonItem.containsKey("trNo")) {
				map.put("hrNo", jsonItem.get("hrNo").toString());
				map.put("jkNo", jsonItem.get("jkNo").toString());
				map.put("trNo", jsonItem.get("trNo").toString());
				map.put("rcDate", jsonItem.get("rcDate").toString());
				if(jsonItem.get("meet").toString().equals("서울") && jsonItem.get("hrName").toString().substring(0,1).equals("[")) {
					map.put("meet", "3");
				} else {
					map.put("meet", meet);
				}
				raceHorseList.add(map);
//				LOGGER.info("list : " + raceHorseList.get(j));
				j++;	
			}
			
			if (j == 9999 || i == jsonObj.size() - 1) {
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

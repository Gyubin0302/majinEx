package com.majin.bit.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.majin.bit.dao.HorseDao;
import com.majin.bit.dto.HorseDto;
import com.majin.bit.dto.Pagination;
import com.majin.bit.util.HorseCrawling;

@Service
@Transactional
public class HorseService {

	@Autowired
	HorseDao horseDao;

	public List<HorseDto> horseInsert(JSONObject jsonObject, String meet) {

		JSONObject jsonObject2 = (JSONObject) jsonObject.get("response");
		JSONObject jsonObject3 = (JSONObject) jsonObject2.get("body");
		JSONObject jsonObject4 = (JSONObject) jsonObject3.get("items");
		JSONArray jsonArray = (JSONArray) jsonObject4.get("item");

		List<HorseDto> horseList = new ArrayList<>();
		JSONObject jsonItem = null;

		long startTime = System.currentTimeMillis();

		for (int i = 0; i < jsonArray.size(); i++) {
			HorseDto horseDto = new HorseDto();
			jsonItem = (JSONObject) jsonArray.get(i);
			HorseCrawling javaCrawling = new HorseCrawling();
			Map<String, Object> horse = javaCrawling.javaCrawling(jsonItem.get("hrNo").toString(), meet);

			horseDto.setHrNo(horse.get("hrNo").toString());
			horseDto.setTrNo(horse.get("trNo").toString());
			horseDto.setHrName(horse.get("hrName").toString());
			horseDto.setSex(horse.get("sex").toString());
			horseDto.setNation(horse.get("nation").toString());
			horseDto.setBirthDay(horse.get("birthDay").toString());
			horseDto.setRating(horse.get("rating").toString());
			horseDto.setTotalRecords(horse.get("totalRecords").toString());
			horseDto.setConsecutiveWinningP(horse.get("ConsecutiveWinningP").toString());
			horseDto.setComplementaryRate(horse.get("ComplementaryRate").toString());
			horseDto.setWinningP(horse.get("WinningP").toString());
			horseDto.setMeet(horse.get("meet").toString());
			horseDto.setRanks(horse.get("ranks").toString());
			horseDto.setAct("08");

			if (!horseDto.getHrNo().equals("")) {
				horseList.add(horseDto);
			}

		}
		
		long endTime = System.currentTimeMillis();
		long completeTime = (endTime - startTime) / 1000;

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", horseList);

		boolean insertStatus = horseDao.horseInsert(map);

		return horseList;
	}

	public List<HorseDto> searchHorse(String search) {
		return horseDao.searchHorse(search);
	}

	public HorseDto searchOneHorse(HorseDto horseDto) {
		return horseDao.searchOneHorse(horseDto);
	}

	public List<HorseDto> searchPagingHorse(Pagination pagination) {
		return horseDao.searchPagingHorse(pagination);
	}

}

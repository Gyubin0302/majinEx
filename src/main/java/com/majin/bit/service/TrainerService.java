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

import com.majin.bit.dao.TrainerDao;
import com.majin.bit.dto.Pagination;
import com.majin.bit.dto.TrDto;
import com.majin.bit.util.TrainerCrawling;

@Service
@Transactional
public class TrainerService {

	@Autowired
	TrainerDao trainerDao;

	public List<TrDto> trainerInsert(JSONObject jsonObject, String meet) {
		
		JSONObject jsonObject2 = (JSONObject) jsonObject.get("response");
		JSONObject jsonObject3 = (JSONObject) jsonObject2.get("body");
		JSONObject jsonObject4 = (JSONObject) jsonObject3.get("items");
		JSONArray jsonArray = (JSONArray) jsonObject4.get("item");

		List<TrDto> trList = new ArrayList<>();
		JSONObject jsonItem = null;
		
		long startTime = System.currentTimeMillis();
		
		for (int i = 0; i < jsonArray.size(); i++) {
			jsonItem = (JSONObject) jsonArray.get(i);
			TrDto trDto = new TrDto();
			TrainerCrawling trainerCrawling = new TrainerCrawling();
			Map<String, Object> trainer = trainerCrawling.javaCrawling(jsonItem.get("trNo").toString(), meet);
			trDto.setTrName(jsonItem.get("trName").toString());
			trDto.setTrNo(jsonItem.get("trNo").toString());
			trDto.setPart(jsonItem.get("part").toString());
			trDto.setDebut(jsonItem.get("stDate").toString());
			trDto.setTYearTotal(trainer.get("tYearTotal").toString());
			trDto.setTAllTotal(trainer.get("tAllTotal").toString());
			trDto.setTConsecutiveWinningP(trainer.get("ConsecutiveWinningP").toString());
			trDto.setTComplementRyRate(trainer.get("ComplementaryRate").toString());
			trDto.setTWinningP(trainer.get("WinningP").toString());
			trDto.setMeet(trainer.get("meet").toString());
			trDto.setAct("10");
			trList.add(trDto);
		}
		
		long endTime = System.currentTimeMillis();
		long completeTime = (endTime - startTime) / 1000;

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", trList);

		boolean insertStatus = trainerDao.trainerInsert(map);

		return trList;
	}

	public List<TrDto> searchTrainer(String search) {
		return trainerDao.searchTrainer(search);
	}

	public TrDto searchOneTrainer(TrDto trDto) {
		return trainerDao.searchOneTrainer(trDto);
	}
	
	public List<TrDto> searchPagingTrainer(Pagination pagination) {
		return trainerDao.searchPagingTrainer(pagination);
	}
}

package com.majin.bit.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.majin.bit.dao.SearchDao;
import com.majin.bit.dto.MultiSearchRaceDto;
import com.majin.bit.dto.RaceHorseDto;

@Service
@Transactional
public class SearchService {
	
	@Autowired
	private SearchDao searchDao;
	
	public int raceHorseSearchCount(MultiSearchRaceDto multiSearchRaceDto) {
		return searchDao.raceHorseSearchCount(multiSearchRaceDto);
	}
	
	public List<RaceHorseDto> multiSearchRace(Map<String, Object> raceMap){
		return searchDao.multiSearchRace(raceMap);
	}

}

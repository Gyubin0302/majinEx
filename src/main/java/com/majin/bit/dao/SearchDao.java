package com.majin.bit.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.majin.bit.dto.MultiSearchRaceDto;
import com.majin.bit.dto.RaceHorseDto;

@Mapper
@Repository
public interface SearchDao {
	public int raceHorseSearchCount(MultiSearchRaceDto multiSearchRaceDto);
	public List<RaceHorseDto> multiSearchRace(Map<String, Object> raceMap);
}

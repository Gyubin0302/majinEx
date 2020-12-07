package com.majin.bit.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.majin.bit.dto.RaceHorseDto;

@Mapper
@Repository
public interface RaceHorseDao {
	public boolean raceHorseInsert(Map<String, Object> map);
	public boolean raceHorseInsertBatch(RaceHorseDto raceHorseDto);
}

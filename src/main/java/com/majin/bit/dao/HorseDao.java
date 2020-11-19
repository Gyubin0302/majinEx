package com.majin.bit.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.majin.bit.dto.HorseDto;

@Mapper
@Repository
public interface HorseDao {
	
	public boolean horseInsert(Map<String, Object> map);
	public List<HorseDto> searchHorse(String search);
	public HorseDto searchOneHorse(String hrNo);
}

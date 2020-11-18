package com.majin.bit.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.majin.bit.dto.JkDto;

@Mapper
@Repository
public interface JockeyDao {
	
	public boolean jockeyInsert(Map<String, Object> map);
	public List<JkDto> searchJockey(String search);
}

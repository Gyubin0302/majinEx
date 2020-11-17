package com.majin.bit.dao;


import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface JockeyDao {
	
	public boolean jockeyInsert(Map<String, Object> map);

}

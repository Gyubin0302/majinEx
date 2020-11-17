package com.majin.bit.dao;


import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface HorseDao {
	
	public boolean horseInsert(Map<String, Object> map);

}

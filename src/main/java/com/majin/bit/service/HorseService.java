package com.majin.bit.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.majin.bit.dao.HorseDao;

@Service
@Transactional
public class HorseService {
	
	@Autowired
	HorseDao horseDao;
	
	public boolean horseInsert(Map<String, Object> map){
		return horseDao.horseInsert(map);
	}

}

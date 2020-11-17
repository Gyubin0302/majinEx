package com.majin.bit.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.majin.bit.dao.JockeyDao;


@Service
@Transactional
public class jockeyService {
	
	@Autowired
	JockeyDao jockeyDao;
	
	public boolean jockeyInsert(Map<String, Object> map){
		return jockeyDao.jockeyInsert(map);
	}

}

package com.majin.bit.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.majin.bit.dao.TrainerDao;
import com.majin.bit.dto.TrDto;

@Service
@Transactional
public class TrainerService {
	
	@Autowired
	TrainerDao trainerDao;
	
	public boolean trainerInsert(Map<String, Object> map){
		return trainerDao.trainerInsert(map);
	}
	
	public List<TrDto> searchTrainer(String search){
		return trainerDao.searchTrainer(search);
	}
}

package com.majin.bit.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.majin.bit.dto.TrDto;

@Mapper
@Repository
public interface TrainerDao {
	
	public boolean trainerInsert(Map<String, Object> map);
	public List<TrDto> searchTrainer(String search);
	public TrDto searchOneTrainer(TrDto trDto);
}

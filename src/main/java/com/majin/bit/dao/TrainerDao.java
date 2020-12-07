package com.majin.bit.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.majin.bit.dto.Pagination;
import com.majin.bit.dto.TrDto;

@Mapper
@Repository
public interface TrainerDao {
	
	public boolean trainerInsert(Map<String, Object> map); // 크롤링한 데이터 DB에 저장
	public List<TrDto> searchTrainer(String search); // 검색어가 포함된 조교 전부 가져오기
	public TrDto searchOneTrainer(TrDto trDto); // 조교 한명에 대한 정보 가져오기
	public List<TrDto> searchPagingTrainer(Pagination pagination); // 조교 리스트 페이징
}

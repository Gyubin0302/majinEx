package com.majin.bit.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.majin.bit.dto.HorseDto;
import com.majin.bit.dto.Pagination;

@Mapper
@Repository
public interface HorseDao {
	
	public boolean horseInsert(Map<String, Object> map); // 크롤링한 데이터 DB에 저장
	public List<HorseDto> searchHorse(String search); // 검색어가 포함된 말 전부 가져오기
	public HorseDto searchOneHorse(HorseDto horseDto); // 말 한마리에 대한 정보 가져오기
	public List<HorseDto> searchPagingHorse(Pagination pagination); // 말 리스트 페이징
	
}

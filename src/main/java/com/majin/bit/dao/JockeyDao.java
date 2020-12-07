package com.majin.bit.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.majin.bit.dto.JkDto;
import com.majin.bit.dto.Pagination;

@Mapper
@Repository
public interface JockeyDao {
	
	public boolean jockeyInsert(Map<String, Object> map); // 크롤링한 데이터 DB에 저장
	public List<JkDto> searchJockey(String search); // 검색어가 포함된 기수 전부 가져오기
	public JkDto searchOneJockey(JkDto jkDto); // 기수 한명에 대한 정보 가져오기
	public List<JkDto> searchPagingJockey(Pagination pagination); // 기수 리스트 페이징
}

package com.majin.bit.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.majin.bit.dto.SearchTermsDto;

@Mapper
@Repository
public interface SearchTermsDao {

	public List<SearchTermsDto> getSearchTermsList(); // 전체 실검 리스트

	public List<SearchTermsDto> getSearchTermsHoreseList();// 말 실검 리스트

	public List<SearchTermsDto> getSearchTermsJockeyList();// 기수 실검 리스트

	public List<SearchTermsDto> getSearchTermsTrainerList();// 조교사 실검 리스트

	public int getSearchTermsHorse(String searchword); // 말

	public int getSearchTermsJockey(String searchword); // 기수

	public int getSearchTermsTrainer(String searchword); // 조교사

	public void totalSearchTerms(SearchTermsDto searchTermsDto); // 검색어 등록

}

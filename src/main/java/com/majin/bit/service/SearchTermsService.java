package com.majin.bit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.majin.bit.dao.SearchTermsDao;
import com.majin.bit.dto.GuideDto;
import com.majin.bit.dto.Pagination;
import com.majin.bit.dto.SearchTermsDto;

@Service
@Transactional
public class SearchTermsService {

	@Autowired
	SearchTermsDao searchtermsdao;

	// 인검 전체 리스트
	public List<SearchTermsDto> getSearchtermsList() {
		return searchtermsdao.getSearchTermsList();
	}

	// 말 실검 리스트
	public List<SearchTermsDto> getSearchTermsHoreseList() {
		return searchtermsdao.getSearchTermsHoreseList();
	}

	// 기수 실검 리스트
	public List<SearchTermsDto> getSearchTermsJockeyList() {
		return searchtermsdao.getSearchTermsJockeyList();
	}

	// 조교사 실검 리스트
	public List<SearchTermsDto> getSearchTermsTrainerList() {
		return searchtermsdao.getSearchTermsTrainerList();
	}

	// 말검색
	public int getSearchTermsHorse(String searchword) {
		return searchtermsdao.getSearchTermsHorse(searchword);
	}

	// 기수 검색
	public int getSearchTermsJockey(String searchword) {
		return searchtermsdao.getSearchTermsJockey(searchword);
	}

	// 조교사 검색
	public int getSearchTermsTrainer(String searchword) {
		return searchtermsdao.getSearchTermsTrainer(searchword);
	}

	// 검색어 DB저장용
	public void totalSearchTerms(SearchTermsDto searchTermsDto) {
		searchtermsdao.totalSearchTerms(searchTermsDto);
	}

}

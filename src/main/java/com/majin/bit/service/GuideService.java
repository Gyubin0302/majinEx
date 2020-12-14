package com.majin.bit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.majin.bit.dao.GuideDao;
import com.majin.bit.dto.GuideDto;
import com.majin.bit.dto.Pagination;

@Service
@Transactional
public class GuideService {

	@Autowired
	GuideDao guidedao;

	// 사이즈 구하기
	public int getGuideBoardSize() {

		return guidedao.getGuideBoardSize();
	}

	// 검색
	public int SelectGuideBoard(String search) {

		return guidedao.SelectGuideBoard(search);
	}

	// 전체 가이드 리스트
	public List<GuideDto> getGuideBoardList(Pagination pagination) {

		return guidedao.getGuideBoardList(pagination);
	}

	// 가이드 상세보기
	public GuideDto GuideBoardDetail(int g_no) {

		return guidedao.GuideBoardDetail(g_no);
	}

	// 가이드 게시판 상세보기
	public int GuideBoardInsert(GuideDto guideBoard) {

		return guidedao.GuideBoardInsert(guideBoard);
	}

	// 가이드 게시판 게시물 업데이트
	public int GuideBoardUpdate(GuideDto guideBoard) {

		return guidedao.GuideBoardUpdate(guideBoard);
	}

	// 가이드 게시판 게시물 삭제
	public int GuideBoardDelete(int g_no) {

		return guidedao.GuideBoardDelete(g_no);
	}

	// 가이드 게시판 페이징징이
	public List<GuideDto> PagingGuideBoard(Pagination pagination) {

		return guidedao.PagingGuideBoard(pagination);
	}

}

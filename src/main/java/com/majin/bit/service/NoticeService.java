package com.majin.bit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.majin.bit.dao.NoticeDao;

import com.majin.bit.dto.NoticeDto;
import com.majin.bit.dto.Pagination;

@Service
@Transactional
public class NoticeService {
	@Autowired
	NoticeDao noticedao;

	// 사이즈
	public int getNoticeBoardSize() {

		return noticedao.getNoticeBoardSize();
	}

	// 공지사항 검색
	public int SelectNoticeBoard(String search) {

		return noticedao.SelectNoticeBoard(search);
	}

	// 전체 공지사항 리스트
	public List<NoticeDto> getNoticeBoardList(Pagination pagination) {

		return noticedao.getNoticeBoardList(pagination);
	}

	// 공지사항 상세보기
	public NoticeDto NoticeBoardDetail(int boardNo) {

		return noticedao.NoticeBoardDetail(boardNo);
	}

	// 공지사항 게시판 상세보기
	public int NoticeBoardInsert(NoticeDto noticeBoard) {

		return noticedao.NoticeBoardInsert(noticeBoard);
	}

	// 공지사항 게시판 게시물 업데이트
	public int NoticeBoardUpdate(NoticeDto noticeBoard) {

		return noticedao.NoticeBoardUpdate(noticeBoard);
	}

	// 공지사항 게시판 게시물 삭제
	public int NoticeBoardDelete(int boardNo) {

		return noticedao.NoticeBoardDelete(boardNo);
	}
	
	// 공지사항 게시판 페이징
	public List<NoticeDto> PagingNoticeBoard(Pagination pagination) {

		return noticedao.PagingNoticeBoard(pagination);
	}

}
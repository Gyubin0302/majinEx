package com.majin.bit.dao;

import java.util.List;

import com.majin.bit.dto.NoticeDto;
import com.majin.bit.dto.Pagination;

public interface NoticeDao {

	public List<NoticeDto> getNoticeBoardList(Pagination pagination);

	NoticeDto NoticeBoardDetail(int boardNo);

	int NoticeBoardInsert(NoticeDto NoticeBoard);

	int NoticeBoardUpdate(NoticeDto NoticeBoard);

	int NoticeBoardDelete(int boardNo);

	public int getNoticeBoardSize();

	public List<NoticeDto> PagingNoticeBoard(Pagination pagination); // 페이징

	public int SelectNoticeBoard(String search); // 검색

}

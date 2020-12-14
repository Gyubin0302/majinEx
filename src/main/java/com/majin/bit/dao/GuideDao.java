package com.majin.bit.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.majin.bit.dto.GuideDto;
import com.majin.bit.dto.Pagination;

@Mapper
@Repository
public interface GuideDao {
	
	public int getGuideBoardSize();//사이즈
	
	public List<GuideDto> getGuideBoardList(Pagination pagination); // 전체보기 
	
	public GuideDto GuideBoardDetail(int g_no); // 상세보기
	
	public int GuideBoardInsert(GuideDto GuideBoard); // 등록
	
	public int GuideBoardUpdate(GuideDto guideBoard); // 수정
	
	public int GuideBoardDelete(int g_no); // 삭제
	
	public List<GuideDto> PagingGuideBoard(Pagination pagination); // 페이징

	public int SelectGuideBoard(String search); // 검색

}

package com.majin.bit.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.majin.bit.dto.GuideDto;

@Mapper
@Repository
public interface GuideDao {
	
	List<GuideDto> getGuideBoardList();
	
	GuideDto GuideBoardDetail(int g_no);
	
	int GuideBoardInsert(GuideDto GuideBoard);
	
	int GuideBoardUpdate(GuideDto guideBoard);
	
	int GuideBoardDelete(int g_no);

}

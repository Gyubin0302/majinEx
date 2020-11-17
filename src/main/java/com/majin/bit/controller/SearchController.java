package com.majin.bit.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.majin.bit.dto.SearchDto;
import com.majin.bit.service.SearchService;

@Controller
public class SearchController {
	
	@Autowired
	private SearchService searchService;
	
	@RequestMapping(value="/search")
    public String training(){
        return "/MultiCheck";
   }
	
	
	@RequestMapping(value = "check.do", method = RequestMethod.GET)
	public String checkBoxTest(HttpServletRequest request) throws IOException, ServletException{
		SearchDto bs = new SearchDto();
		String[] attrList = request.getParameterValues("attract");
		String[] areaList = request.getParameterValues("area");
//		String searchType = request.getParameter("searchType");
//		String searchWord = request.getParameter("searchWord");
		
		bs.setAttrList(attrList);
		bs.setAreaList(areaList);
//		bs.setSearchType(searchType);
//		bs.setSearchWord(searchWord);
		System.out.println("BoardSearch : "+ bs);

//		List<Board> searchList = boardSearchDao.boardSearchList(bs);
//		System.out.println("Board : "+ searchList);
		
		return "list";
		
	}
}

package com.majin.bit.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.majin.bit.dto.GuideDto;
import com.majin.bit.service.GuideService;

@Controller
//@RequestMapping("/guideBoard")
public class GuideController {
	
	@Autowired
	GuideService guideservice;
	
	@RequestMapping("/guideList")
	private String GuideBoardList(Model model, HttpServletRequest request) {
		
		List<GuideDto> guideList = new ArrayList<>();
		guideList = guideservice.getGuideBoardList();
		System.out.println(guideList);
		model.addAttribute("guideList",guideList);
		
		return "guideList";
	}
	
	@RequestMapping("/guideDetail/{g_no}")
	private String GuideBoardDetail(@PathVariable("g_no") int g_no, Model model) {
		System.out.println(g_no);
		model.addAttribute("detail", guideservice.GuideBoardDetail(g_no));
		
		return "guideDetail";
	}
	
	@RequestMapping(value="/admin/guideInsert")
	private String GuideBoardInsert(@ModelAttribute GuideDto guideDto) {
		
		return "admin/guideInsert";
	}
	
	@RequestMapping(value="/admin/guideInsertProc")
	private String GuideBoardInsertProc(@ModelAttribute GuideDto guideBoard, HttpServletRequest request) {
		guideservice.GuideBoardInsert(guideBoard);
		
		return "forward:/guideList";
	}
	
	@RequestMapping(value="/admin/guideUpdate/{g_no}")
	private String GuideBoardUpdateForm(@PathVariable("g_no") int g_no, Model model ) {
		model.addAttribute("detail", guideservice.GuideBoardDetail(g_no));
		System.out.println("돌아돌아돌림판: "+g_no);
		return"/admin/guideUpdate";
	}
	
	@RequestMapping(value="/admin/guideUpdateProc")
	private String GuideBoardUpdateProc(@ModelAttribute GuideDto guideBoard) {
		
		System.out.println("업대이트 되니? ");
		guideservice.GuideBoardUpdate(guideBoard);
		System.out.println("업대이트 망할것 : "+guideBoard);
		int guideNo = guideBoard.getGuideNo();
		String g_no = Integer.toString(guideNo);
		
		return "redirect:/guideDetail/"+g_no;
	}
	
	@RequestMapping(value="/admin/guideDelete/{g_no}")
	private String GuideBoardDelete(@PathVariable("g_no") int g_no) {
		guideservice.GuideBoardDelete(g_no);
		return "redirect:/guideList";
	}
	
}

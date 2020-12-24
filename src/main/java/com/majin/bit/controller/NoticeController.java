package com.majin.bit.controller;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.majin.bit.dto.GuideDto;
import com.majin.bit.dto.NoticeDto;
import com.majin.bit.dto.Pagination;
import com.majin.bit.service.NoticeService;

@Controller
public class NoticeController {

	// 공지사항 서비스
	@Autowired
	NoticeService noticeservice;

	// 공지사항 메인용 페이징
	public Pagination NoticeSize(int pageNo) {
		int noticeList = noticeservice.getNoticeBoardSize();
		Pagination noticePagination = new Pagination(noticeList, pageNo);
		return noticePagination;
	}

	// 공지사항 메인용 페이징
	public List<NoticeDto> NoticePag(Pagination noticePagination) {
		List<NoticeDto> noticePaging = noticeservice.getNoticeBoardList(noticePagination);
		return noticePaging;
	}

	// 공지사항 페이징 컨트롤러
	@RequestMapping(value = "/noticeList", method = { RequestMethod.GET, RequestMethod.POST })
	private String NoticeBoardPaging(Model model, @RequestParam(defaultValue = "1") int pageNo) {

		Pagination noticePagination = NoticeSize(pageNo);
		List<NoticeDto> noticePaging = NoticePag(noticePagination);
		model.addAttribute("noticeList", noticePaging);
		model.addAttribute("noticePagination", noticePagination);

		return "/noticeList";
	}

	// 여기부터 작업
	// 가이드 검색용 페이징
	public Pagination NoticePagination(int pageNo, String search) {
		System.out.println("검색어 : " + search);
		int SelectNotice = noticeservice.SelectNoticeBoard(search);
		Pagination noticePagination = new Pagination(SelectNotice, pageNo, search);
		System.out.println("DB에서 가져온 데이터 전체 :" + SelectNotice + "개");
		return noticePagination;
	}

	// 가이드 검색용 페이징
	public List<NoticeDto> NoticePaging(Pagination noticePagination) {
		List<NoticeDto> noticePaging = noticeservice.PagingNoticeBoard(noticePagination);
		return noticePaging;
	}

	// 검색 깐트롤러
	@RequestMapping(value = "/noticeSelect", method = RequestMethod.GET)
	public String NoticeBoardSelect(Model model, @RequestParam(defaultValue = "1") int pageNo, String search) {

		Pagination NoticePagination = NoticePagination(pageNo, search);
		List<NoticeDto> noticePaging = NoticePaging(NoticePagination);
		model.addAttribute("searchnotice", noticePaging);
		model.addAttribute("noticePagination", NoticePagination);

		return "/noticeSelect";
	}

	// 여기까지 끝

	// 공지사항 게시물 상세보기 컨트롤러
	@RequestMapping("/noticeDetail/{boardNo}")
	private String NoticeBoardDetail(@PathVariable("boardNo") int boardNo, Model model) {
		model.addAttribute("detail", noticeservice.NoticeBoardDetail(boardNo));

		return "noticeDetail";
	}

	// 공지사항 게시물 등록 컨트롤러
	@RequestMapping(value = "/noticeInsert")
	private String NoticeBoardInsert(@ModelAttribute NoticeDto noticeDto) {

		return "admin/noticeInsert";
	}

	// 공지사항 게시물 등록 폼
	@RequestMapping(value = "/noticeInsertProc")
	private String NoticeBoardInsertProc(@ModelAttribute NoticeDto noticeBoard, HttpServletRequest request) {
		noticeservice.NoticeBoardInsert(noticeBoard);
		return "forward:/noticeList";
	}

	// 공지사항 엄데이트 컨트롤러
	@RequestMapping(value = "/noticeUpdate/{boardNo}")
	private String NoticeBoardUpdateForm(@PathVariable("boardNo") int boardNo, Model model) {
		model.addAttribute("detail", noticeservice.NoticeBoardDetail(boardNo));
		return "admin/noticeUpdate";
	}

	// 공지사항 업데이트 폼
	@RequestMapping(value = "/noticeUpdateProc")
	private String NoticeBoardUpdateProc(@ModelAttribute NoticeDto noticeBoard) {

		noticeservice.NoticeBoardUpdate(noticeBoard);
		int boardNumber = noticeBoard.getBoardNo();
		String boardNo = Integer.toString(boardNumber);

		return "redirect:/noticeDetail/" + boardNo;
	}

	// 공지사항 삭제 컨트롤러
	@RequestMapping(value = "/noticeDelete/{boardNo}")
	private String NoticeBoardDelete(@PathVariable("boardNo") int boardNo) {
		noticeservice.NoticeBoardDelete(boardNo);
		return "redirect:/noticeList";
	}

}
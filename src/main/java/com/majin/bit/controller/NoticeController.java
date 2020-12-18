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

import com.majin.bit.dto.NoticeDto;
import com.majin.bit.dto.Pagination;
import com.majin.bit.service.NoticeService;

@Controller
public class NoticeController {

	// 가이드 서비스
		@Autowired
		NoticeService noticeservice;

		// 페이징 깐트롤러
		@RequestMapping(value = "/noticeList", method = RequestMethod.POST)
		private String NoticeBoardPaging(Model model, @RequestParam(defaultValue = "1") int pageNo) {

			int noticeList = noticeservice.getNoticeBoardSize();
			Pagination noticePagination = new Pagination(noticeList, pageNo);
			List<NoticeDto> noticePaging = noticeservice.getNoticeBoardList(noticePagination);

			model.addAttribute("noticeList", noticePaging);
			model.addAttribute("noticePagination", noticePagination);

			return "/noticeList";
		}
		
		/*
		 * // 게시물 전체 컨트롤러
		 * 
		 * @RequestMapping("/noticeList") private String NoticeBoardList(Model model,
		 * HttpServletRequest request) {
		 * 
		 * List<NoticeDto> noticeList = new ArrayList<>(); noticeList =
		 * noticeservice.getNoticeBoardList(); model.addAttribute("noticeList",
		 * noticeList);
		 * 
		 * return "noticeList"; }
		 */

		// 게시물 상세보기 컨트롤러
		@RequestMapping("/noticeDetail/{boardNo}")
		private String NoticeBoardDetail(@PathVariable("boardNo") int boardNo, Model model) {
			model.addAttribute("detail", noticeservice.NoticeBoardDetail(boardNo));

			return "noticeDetail";
		}

		// 게시물 등록 컨트롤러
		@RequestMapping(value = "/noticeInsert")
		private String NoticeBoardInsert(@ModelAttribute NoticeDto noticeDto) {

			return "admin/noticeInsert";
		}

		// 게시물 등록 폼
		@RequestMapping(value = "/noticeInsertProc" )
		private String NoticeBoardInsertProc(@ModelAttribute NoticeDto noticeBoard, HttpServletRequest request) {
			noticeservice.NoticeBoardInsert(noticeBoard);
			return "forward:/noticeList";
		}

		// 엄데이트 컨트롤러
		@RequestMapping(value = "/noticeUpdate/{boardNo}")
		private String NoticeBoardUpdateForm(@PathVariable("boardNo") int boardNo, Model model) {
			model.addAttribute("detail", noticeservice.NoticeBoardDetail(boardNo));
			return "admin/noticeUpdate";
		}

		// 업데이트 폼
		@RequestMapping(value = "/noticeUpdateProc")
		private String NoticeBoardUpdateProc(@ModelAttribute NoticeDto noticeBoard) {

			noticeservice.NoticeBoardUpdate(noticeBoard);
			int boardNumber = noticeBoard.getBoardNo();
			String boardNo = Integer.toString(boardNumber);

			return "redirect:/noticeDetail/" + boardNo;
		}

		// 삭제 컨트롤러
		@RequestMapping(value = "/noticeDelete/{boardNo}")
		private String NoticeBoardDelete(@PathVariable("boardNo") int boardNo) {
			noticeservice.NoticeBoardDelete(boardNo);
			return "redirect:/noticeList";
		}
		
		@RequestMapping(value = "/noticeSelect")
		   public String NoticeBoardSelect(Model model, @RequestParam(defaultValue = "1") int pageNo, String search) {

		      System.out.println("-----------------" + search);
		      int SelectN = noticeservice.SelectNoticeBoard(search);
		      Pagination noticePagination = new Pagination(SelectN, pageNo, search);
		      System.out.println("searchN=="+ SelectN);
		      List<NoticeDto> noticePaging = noticeservice.PagingNoticeBoard(noticePagination);

		      model.addAttribute("searchnotice", noticePaging);
		      model.addAttribute("noticePagination", noticePagination);
		      return "/noticeSelect";
		}
//		@RequestMapping(value = "/noticeSelect", method = RequestMethod.POST)
//		public String NoticeBoardSelect(Model model, @RequestParam(defaultValue = "1") int pageNo, String search) {
//			
//			System.out.println("-----------------"+search);
//			 int SelectN = noticeservice.SelectNoticeBoard(search); Pagination
//			 noticePagination = new Pagination(SelectN, pageNo, search); 
//			 List<NoticeDto> noticePaging = noticeservice.PagingNoticeBoard(noticePagination);
//			 
//			 model.addAttribute("searchnotice", noticePaging);
//			 model.addAttribute("noticePagination", noticePagination);
//			
//			return "/noticeSelect";
//		}
		

	/*
	 * // 다중파일업로드 에디터
	 * 
	 * @ResponseBody
	 * 
	 * @RequestMapping(value = "/file_uploader_DEXT", method = RequestMethod.POST)
	 * public String multiplePhotoUpload(HttpServletRequest request) { // 파일정보
	 * StringBuffer sb = new StringBuffer(); try { // 파일명을 받는다- 일반 원본파일명 String
	 * oldName = request.getHeader("file-name"); System.out.println(oldName); // 파일
	 * 기본경로_ 상세경로 //String filePath =
	 * request.getSession().getServletContext().getRealPath(
	 * "/resources/photoUpload/"); //String filePath =
	 * "D:/spring/work/majinEx/src/main/webapp/resources/photoUpload/"; String
	 * filePath = "D:/spring/work/majinEx/src/main/resources/static/fileupload/not";
	 * 
	 * System.err.println(filePath); String saveName = sb.append(new
	 * SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis()))
	 * .append(UUID.randomUUID().toString())
	 * .append(oldName.substring(oldName.lastIndexOf("."))).toString();
	 * System.err.println("=======>"+filePath + saveName); InputStream is =
	 * request.getInputStream();
	 * 
	 * OutputStream os = new FileOutputStream(filePath + saveName); int numRead;
	 * byte b[] = new byte[Integer.parseInt(request.getHeader("file-size"))]; while
	 * ((numRead = is.read(b, 0, b.length)) != -1) { os.write(b, 0, numRead); }
	 * os.flush(); os.close(); //정보출력 sb = new StringBuffer();
	 * sb.append("&bNewLine=true") .append("&sFileName=").append(oldName)
	 * .append("&sFileURL=").append("").append(filePath).append(saveName);
	 * System.out.println(sb); } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * return sb.toString(); }
	 * 
	 * 
	 * }
	 */

}

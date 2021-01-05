package com.majin.bit.controller;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.majin.bit.dto.GuideDto;
import com.majin.bit.dto.Pagination;
import com.majin.bit.service.GuideService;

@Controller
//@RequestMapping("/guideBoard")
public class GuideController {

	// 가이드 서비스
	@Autowired
	GuideService guideservice;

	// 가이드 메인용 페이징
	public Pagination GuideSize(int pageNo) {
		int guideList = guideservice.getGuideBoardSize();
		Pagination guidePagination = new Pagination(guideList, pageNo);
		return guidePagination;
	}

	// 가이드 메인용 페이징
	public List<GuideDto> GuidePag(Pagination guidePagination) {
		List<GuideDto> guidePaging = guideservice.getGuideBoardList(guidePagination);
		return guidePaging;
	}

	// 페이징 메잉용 컨트롤러
	@RequestMapping(value = "/guideList", method = RequestMethod.GET)
	private String GuideBoardPaging(Model model, @RequestParam(defaultValue = "1") int pageNo) {

		Pagination guidePagination = GuideSize(pageNo);
		List<GuideDto> guidePaging = GuidePag(guidePagination);
		model.addAttribute("guideList", guidePaging);
		model.addAttribute("guidePagination", guidePagination);

		return "/guideList";
	}

	// 가이드 검색용 페이징
	public Pagination GuidePagination(int pageNo, String search) {
		int SelectG = guideservice.SelectGuideBoard(search);
		Pagination guidePagination = new Pagination(SelectG, pageNo, search);
		return guidePagination;
	}

	// 가이드 검색용 페이징
	public List<GuideDto> GuidePaging(Pagination guidePagination) {
		List<GuideDto> guidePaging = guideservice.PagingGuideBoard(guidePagination);
		return guidePaging;
	}

	// 가이드 검색용 컨트롤러
	@RequestMapping(value = "/guideSelect", method = RequestMethod.GET)
	public String GuideBoardSelect(Model model, @RequestParam(defaultValue = "1") int pageNo, String search) {

		Pagination guidePagination = GuidePagination(pageNo, search);
		List<GuideDto> guidePaging = GuidePaging(guidePagination);
		model.addAttribute("searchguide", guidePaging);
		model.addAttribute("guidePagination", guidePagination);

		return "/guideSelect";
	}

	// 게시물 상세보기 컨트롤러
	@RequestMapping("/guideDetail/{g_no}")
	private String GuideBoardDetail(@PathVariable("g_no") int g_no, Model model) {
		model.addAttribute("detail", guideservice.GuideBoardDetail(g_no));

		return "guideDetail";
	}

	// 게시물 등록 컨트롤러
	@RequestMapping(value = "/guideInsert")
	private String GuideBoardInsert(@ModelAttribute GuideDto guideDto) {

		return "admin/guideInsert";
	}

	// 게시물 등록 폼
	@RequestMapping(value = "/guideInsertProc", method = RequestMethod.POST)
	private String GuideBoardInsertProc(@ModelAttribute GuideDto guideBoard, HttpServletRequest request) {
		guideservice.GuideBoardInsert(guideBoard);
		return "forward:/guideList";
	}

	// 엄데이트 컨트롤러
	@RequestMapping(value = "/guideUpdate/{g_no}")
	private String GuideBoardUpdateForm(@PathVariable("g_no") int g_no, Model model) {
		model.addAttribute("detail", guideservice.GuideBoardDetail(g_no));
		return "admin/guideUpdate";
	}

	// 업데이트 폼
	@RequestMapping(value = "/guideUpdateProc")
	private String GuideBoardUpdateProc(@ModelAttribute GuideDto guideBoard) {

		guideservice.GuideBoardUpdate(guideBoard);
		int guideNo = guideBoard.getGuideNo();
		String g_no = Integer.toString(guideNo);

		return "redirect:/guideDetail/" + g_no;
	}

	// 삭제 컨트롤러
	@RequestMapping(value = "/guideDelete/{g_no}", method = RequestMethod.POST)
	private String GuideBoardDelete(@PathVariable("g_no") int g_no, RedirectAttributes red,
			HttpServletResponse response) throws Exception {
		guideservice.GuideBoardDelete(g_no);

		return "redirect:/guideList";
	}

	// 다중파일업로드 에디터(네이버 스마트 에디터 2.8)
	@ResponseBody
	@RequestMapping(value = "/file_uploader_DEXT", method = RequestMethod.POST)
	public String multiplePhotoUpload(HttpServletRequest request) {
		// 파일정보
		StringBuffer sb = new StringBuffer();
		try {
			// 파일명을 받는다- 일반 원본파일명
			String oldName = request.getHeader("file-name");
			// 파일 기본경로_ 상세경로
			String filePath = request.getSession().getServletContext().getRealPath("/resources/photoUpload/");
			String saveName = sb.append(new SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis()))
					.append(UUID.randomUUID().toString()).append(oldName.substring(oldName.lastIndexOf(".")))
					.toString();
			InputStream is = request.getInputStream();

			OutputStream os = new FileOutputStream(filePath + saveName);
			int numRead;
			byte b[] = new byte[Integer.parseInt(request.getHeader("file-size"))];
			while ((numRead = is.read(b, 0, b.length)) != -1) {
				os.write(b, 0, numRead);
			}
			os.flush();
			os.close();
			// 정보출력
			sb = new StringBuffer();
			sb.append("&bNewLine=true").append("&sFileName=").append(oldName).append("&sFileURL=").append("")
					.append("/static/resources/photoUpload/").append(saveName);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sb.toString();
	}

}
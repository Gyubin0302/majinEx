package com.majin.bit.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.majin.bit.dto.GuideDto;
import com.majin.bit.service.GuideService;

@Controller
//@RequestMapping("/guideBoard")
public class GuideController {

	// 가이드 서비스
	@Autowired
	GuideService guideservice;

	// 게시물 전체 컨트롤러
	@RequestMapping("/guideList")
	private String GuideBoardList(Model model, HttpServletRequest request) {

		List<GuideDto> guideList = new ArrayList<>();
		guideList = guideservice.getGuideBoardList();
		System.out.println(guideList);
		model.addAttribute("guideList", guideList);

		return "guideList";
	}

	// 게시물 상세보기 컨트롤러
	@RequestMapping("/guideDetail/{g_no}")
	private String GuideBoardDetail(@PathVariable("g_no") int g_no, Model model) {
		System.out.println(g_no);
		model.addAttribute("detail", guideservice.GuideBoardDetail(g_no));

		return "guideDetail";
	}

	// 게시물 등록 컨트롤러
	@RequestMapping(value = "/admin/guideInsert")
	private String GuideBoardInsert(@ModelAttribute GuideDto guideDto) {

		return "admin/guideInsert";
	}

	// 게시물 등록 폼
	@RequestMapping(value = "/admin/guideInsertProc")
	private String GuideBoardInsertProc(@ModelAttribute GuideDto guideBoard, HttpServletRequest request) {
		System.out.println(guideBoard);
		guideservice.GuideBoardInsert(guideBoard);

		return "forward:/guideList";
	}

	// 엄데이트 컨트롤러
	@RequestMapping(value = "/admin/guideUpdate/{g_no}")
	private String GuideBoardUpdateForm(@PathVariable("g_no") int g_no, Model model) {
		model.addAttribute("detail", guideservice.GuideBoardDetail(g_no));
		System.out.println("돌아돌아돌림판: " + g_no);
		return "admin/guideUpdate";
	}

	// 업데이트 폼
	@RequestMapping(value = "/admin/guideUpdateProc")
	private String GuideBoardUpdateProc(@ModelAttribute GuideDto guideBoard) {

		System.out.println("업대이트 되니? ");
		guideservice.GuideBoardUpdate(guideBoard);
		System.out.println("업대이트 망할것 : " + guideBoard);
		int guideNo = guideBoard.getGuideNo();
		String g_no = Integer.toString(guideNo);

		return "redirect:/guideDetail/" + g_no;
	}

	// 삭제 컨트롤러
	@RequestMapping(value = "/admin/guideDelete/{g_no}")
	private String GuideBoardDelete(@PathVariable("g_no") int g_no) {
		guideservice.GuideBoardDelete(g_no);
		return "redirect:/guideList";
	}

	 // 다중파일업로드 에디터
    @RequestMapping(value = "/file_uploader_DEXT.asp", method = RequestMethod.POST)
    @ResponseBody
    public String multiplePhotoUpload(HttpServletRequest request) {

        StringBuffer sb = new StringBuffer();
        try {

            String oldName = request.getHeader("file-name");

            String filePath = request.getSession().getServletContext().getRealPath("*/resources/templates/img/");
            System.err.println(filePath);
            String saveName = sb.append(new SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis()))
                    .append(UUID.randomUUID().toString())
                    .append(oldName.substring(oldName.lastIndexOf("."))).toString();
            System.err.println(filePath + saveName);
            InputStream is = request.getInputStream();

            OutputStream os = new FileOutputStream(filePath + saveName);
            int numRead;
            byte b[] = new byte[Integer.parseInt(request.getHeader("file-size"))];
            while ((numRead = is.read(b, 0, b.length)) != -1) {
                os.write(b, 0, numRead);
            }
            os.flush();
            os.close();

            sb = new StringBuffer();
            sb.append("&bNewLine=true")
                    .append("&sFileName=").append(oldName)
                    .append("&sFileURL=").append("*/resources/templates/img/").append(saveName);
            System.out.println(sb);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
	

}

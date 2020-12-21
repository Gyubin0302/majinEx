package com.majin.bit.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.deeplearning4j.models.word2vec.Word2Vec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.majin.bit.dto.Member;
import com.majin.bit.dto.MemberDTO;
import com.majin.bit.service.MemberService;
import com.majin.bit.word2vecTest.Word2VecTest;

@Controller
public class OAuth2Controller {
	@Autowired
	private MemberService memberService;

	@GetMapping({ "", "/" })
	public String index() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String path = "D:/final/userText/";
		File folder = new File(path);
		File userText = new File(path + auth.getName() + "_save.txt");
		if(!folder.exists()) { // 폴더가 없으면 폴더 생성
			try {
				folder.mkdirs();
	
				if(!userText.exists()) { // 파일이 없으면 파일 생성
					userText.createNewFile();
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				if(!userText.exists() && auth.getName() != "anonymousUser") {
					userText.createNewFile();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return "index";
	}

	@GetMapping("/home")
	public String home() {
		return "home";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/loginSuccess")
	public String loginSuccess() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(auth.getName());
		return "home";
	}

	@GetMapping("/hello")
	public String hello(@AuthenticationPrincipal DefaultOAuth2User user, @AuthenticationPrincipal UserDetails user2) {
		if (user != null) {
			System.out.println("\n\n\nController-----------------------\n" + user.getAttributes());

			System.out.println("name:" + user.getAttributes().get("name"));
		} else if (user2 != null) {
			System.out.println("\n\n\nController-----------------------\n" + user2.getUsername());

			System.out.println("name:" + user2.getAuthorities());
		}
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("\n\n"+auth.toString());
		System.out.println("이거는 securitycontextholder에서 가져온것"+auth.getName());
		return "hello";
	}

	@GetMapping("/loginFailure")
	public String loginFailure() {
		return "loginFailure";
	}

	@GetMapping("/signup")
	public String signup() {
		return "member/signup";
	}

	@PostMapping("/signup")
	public String signupsuccess(MemberDTO memberDTO) {
		System.out.println("여기는 컨트롤러에 memberservice 호출");
		memberService.save(memberDTO);
		return "login";
	}

	@GetMapping("/idcheck")
	@ResponseBody
	public boolean IdCheck(@RequestParam String id) throws Exception {
		return memberService.idcheck(id);
	}
	
	@GetMapping("/findpw")
	public String findpw() {
		return "findpw";
	}
	
	@GetMapping("/changepw")
	public String changepw() {
		return "changepw";
	}
	
	@PostMapping("/changepw")
	public String changepw2(@RequestParam String oldpw, @RequestParam String pw) {
		memberService.checkPw(SecurityContextHolder.getContext().getAuthentication().getName(),oldpw,pw);
		return "home";
	}
	
	@GetMapping("/disableid")
	public String disableId() {
		memberService.disableId(SecurityContextHolder.getContext().getAuthentication().getName());
		return "home";
	}

	@GetMapping("/yundoHorse")
	public String getYundoHorse() {
		return "home";
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Word2VecTest word2Vec = new Word2VecTest();
		word2Vec.training(auth.getName());
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/";
	}
}

package com.majin.bit.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.majin.bit.dto.MemberDTO;
import com.majin.bit.service.MemberService;

@Controller
public class OAuth2Controller {
	@Autowired
	private MemberService memberService;

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
		return "redirect:/";
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
		return "redirect:/";
	}
	
	@GetMapping("/disableid")
	public String disableId() {
		memberService.disableId(SecurityContextHolder.getContext().getAuthentication().getName());
		return "redirect:/";
	}

	@GetMapping("/yundoHorse")
	public String getYundoHorse() {
		return "/index";
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/";
	}
}

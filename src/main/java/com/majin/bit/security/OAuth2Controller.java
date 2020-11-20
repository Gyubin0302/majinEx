package com.majin.bit.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.majin.bit.dto.MemberDTO;
import com.majin.bit.service.MemberServiceImpl;

@Controller
public class OAuth2Controller {
	@Autowired
	private MemberServiceImpl memberService;

	@GetMapping({ "", "/" })
	public String index() {
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
	
	@GetMapping("/yundoHorse")
	public String getYundoHorse() {
		
		return "home";
	}
}

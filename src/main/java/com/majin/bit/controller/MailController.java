package com.majin.bit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.majin.bit.dao.MailDao;
import com.majin.bit.dto.MailDto;
import com.majin.bit.dto.Member;
import com.majin.bit.dto.MemberDTO;
import com.majin.bit.service.MailService;
import com.majin.bit.service.MemberService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class MailController {
    private final MailService mailService;
    private final MemberService memberService;
  
    @ResponseBody
    @PostMapping("/mail")
    public boolean execMail(MailDto mailDto) {
    	mailDto.setTitle("마진 인증");
    	mailDto.setMessage(MailService.createKey());
    	System.out.println(mailDto.getMessage());
    	if(!mailService.chkDuplicate(mailDto.getAddress())) {
    		mailService.save(mailDto);
    	} else {
    		mailService.update(mailDto);
    	}
        mailService.mailSend(mailDto);
        return true;
    }
    
    @ResponseBody
    @PostMapping("/mailChk")
    public boolean chkMail(MailDto mailDto) {
    	System.out.println("mailcheck controller\n\n");
    	return mailService.checkMail(mailDto);
    }
    
    @PostMapping("/findpw")
    public String resetPw(String id) {
    	String newPw = MailService.createKey();
    	memberService.newPw(id,newPw);
    	MailDto mailDto = new MailDto();
    	mailDto.setTitle("마진 비밀번호 초기화 이메일 입니다");
    	mailDto.setMessage(newPw);
    	mailDto.setAddress(memberService.findEmail(id));
    	System.out.println("비번 바꼇나 확인");
    	mailService.mailSend(mailDto);
    	return "login";
    }
    
}
package com.majin.bit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.majin.bit.dao.MailDao;
import com.majin.bit.dto.MailDto;
import com.majin.bit.service.MailService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class MailController {
    private final MailService mailService; 
  
    @ResponseBody
    @PostMapping("/mail")
    public boolean execMail(MailDto mailDto) {
    	mailDto.setTitle("제목입니다");
    	mailDto.setMessage(MailService.createKey());
    	if(!mailService.chkDuplicate(mailDto.getAddress())) {
    		mailService.save(mailDto);
    	} else {
    		mailService.update(mailDto);
    	}
        //mailService.mailSend(mailDto);
        return true;
    }
    
    @ResponseBody
    @PostMapping("/mailChk")
    public boolean chkMail(MailDto mailDto) {
    	System.out.println("mailcheck controller\n\n");
    	return mailService.checkMail(mailDto);
    }
}
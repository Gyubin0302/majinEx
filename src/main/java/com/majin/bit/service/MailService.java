package com.majin.bit.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.majin.bit.dao.MailDao;
import com.majin.bit.dto.MailDto;
import com.majin.bit.util.MailHandler;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MailService {
	@Autowired
	private MailDao mailDao;
	
	private JavaMailSender mailSender;
	private static final String FROM_ADDRESS = "dxb0325@gmail.com";

	public void mailSend(MailDto mailDto) {
		try {
			MailHandler mailHandler = new MailHandler(mailSender);
			// 받는 사람
			mailHandler.setTo(mailDto.getAddress());
			// 보내는 사람
			mailHandler.setFrom(MailService.FROM_ADDRESS);
			// 제목
			mailHandler.setSubject(mailDto.getTitle());
			// HTML Layout
			String htmlContent = mailDto.getMessage();
			mailHandler.setText(htmlContent, true);
			mailHandler.send();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String createKey() {
		StringBuffer key = new StringBuffer();
		Random rnd = new Random();

		for (int i = 0; i < 8; i++) { // 인증코드 8자리
			int index = rnd.nextInt(3); // 0~2 까지 랜덤

			switch (index) {
			case 0:
				key.append((char) ((int) (rnd.nextInt(26)) + 97));
				//  a~z  (ex. 1+97=98 => (char)98 = 'b')
				break;
			case 1:
				key.append((char) ((int) (rnd.nextInt(26)) + 65));
				//  A~Z 
				break;
			case 2:
				key.append((rnd.nextInt(10)));
				// 0~9
				break;
			}
		}

		return key.toString();
	}

	public void save(MailDto mailDto) {
		mailDao.save(mailDto);
	}
	
	public void update(MailDto mailDto) {
		MailDto user = mailDao.findByAddress(mailDto.getAddress());
		mailDto.setMailId(user.getMailId());
		save(mailDto);
	}

	public boolean checkMail(MailDto mailDto) {
		System.out.println(mailDto.getAddress());
		MailDto mail= mailDao.findByAddress(mailDto.getAddress());
		System.out.println("\n발급키 : "+mail.getMessage());
		System.out.println("입력키 : "+mailDto.getMessage());
		return mail.getMessage().equals(mailDto.getMessage()) ? true : false;
	}

	public boolean chkDuplicate(String address) {
		return mailDao.findByAddress(address) != null ? true : false;
	}
}
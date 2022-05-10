package com.mayy5.admin.service;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import javax.mail.MessagingException;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.mayy5.admin.util.MailUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MailSendService {

	private final JavaMailSender mailSender;
	private final TokenService tokenService;

	//인증메일 보내기
	public String sendAuthMail(String email) {
		//6자리 난수 인증번호 생성
		String authKey = tokenService.genSignUpAuthToken(email);
		//인증메일 보내기
		try {
			MailUtils sendMail = new MailUtils(mailSender);
			sendMail.setSubject("회원가입 이메일 인증");
			sendMail.setText(new StringBuffer().append("<h1>[이메일 인증]</h1>")
				.append("<p>아래 링크를 클릭하시면 이메일 인증이 완료됩니다.</p>")
				.append("<a href='http://localhost:8080/v1/signUpConfirm?email=")
				.append(email)
				.append("&authKey=")
				.append(authKey)
				.append("' target='_blenk'>이메일 인증 확인</a>")
				.toString());
			sendMail.setFrom("mayy5.master@gmail.com", "관리자");
			sendMail.setTo(email);
			sendMail.send();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return authKey;
	}
}
package com.sw.socialwebsocket.service;

import org.apache.commons.mail.SimpleEmail;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailService {
	
	private final JavaMailSender javaMailSender;
	
	public void mailSend(String email,String name){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        
        message.setSubject("회원가입 축하");
        message.setText(name + "님 가입 축하드림.");
        javaMailSender.send(message);
        System.out.println("메일 발송");
    }
	
	public void sendEmail(String email, String name) {
		mailSend(email, name);
	}

	private void sendSimple(String email, String name) {
		SimpleEmail mail = new SimpleEmail();

		// 메일 전송 서버 지정, 네이버 메일 - 환경설정 - pop3 설정
		mail.setHostName("smtp.naver.com");
		// 인코딩 설정
		mail.setCharset("utf-8");
		// 메일 전송 과정 추적해서 콘솔에 띄워줌
		mail.setDebug(true);
		// 로그인하기 위해 정보 입력
		mail.setAuthentication("이메일", "비밀번호");
		// 입력한 정보로 로그인 요청
		mail.setSSLOnConnect(true);
		try {
			// 보내는 사람 메일 / 이름 설정
			mail.setFrom("amdusias9109@gmail.com", "관리자");
			// 받는 사람 메일 / 이름, 회원가입 페이지에에서 가져온다.
			mail.addTo(email, name);
			// 메일 제목
			mail.setSubject("회원가입 축하");
			// 메일 내용
			mail.setMsg(name + "님! 가입을 축하드립니다!");
			// 메일 발송
			mail.send();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
}

package com.sw.socialwebsocket.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sw.socialwebsocket.handler.WebSocketHandler;
import com.sw.socialwebsocket.service.MailService;
import com.sw.socialwebsocket.service.MemberService;
import com.sw.socialwebsocket.vo.MemberVO;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class WebSocketController {
	
	private final MemberService memberservice;
	private final MailService mail;
	private final WebSocketHandler handler;
	// 채팅방 입장
	@GetMapping("webSocket")
	public String view_chat(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

		return "webSocket";
	}
	
	@PostMapping("/joiner")
	public String joiner(HttpSession session, MemberVO member) {
		if(member!=null) {
			if(memberservice.memberCheck(member)==0) {
				memberservice.insertMember(member);
				mail.sendEmail(member.getEmail(), member.getName());
			} else if(memberservice.memberCheck(member)==1){
				memberservice.updateNickName(member);
				session.setAttribute("loginMember",member);
			} else {
				return "/";
			}
			handler.loginMember(member);
			return "webSocket";
		}
		return "/";
	} 
	
	@GetMapping("joiner")
	public String joiner(HttpSession session) {
		MemberVO member = (MemberVO) session.getAttribute("loginMember");
		if(memberservice.memberCheck(member)==0) {
			session.setAttribute("state", "신규");
		} else {
			session.setAttribute("state", "멤버");
		}
		return "join";
	}
	
	@GetMapping("/join")
	public void join() {
		
	}
	
	@GetMapping("/")
	public String login() {
		return "login";
	}

}
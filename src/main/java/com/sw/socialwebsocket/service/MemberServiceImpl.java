package com.sw.socialwebsocket.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.sw.socialwebsocket.dao.MemberDAO;
import com.sw.socialwebsocket.vo.MemberVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	
	private final MemberDAO memberdao;

	LocalDate now = LocalDate.now();
	
	@Override
	public void insertMember(MemberVO membervo) {
		membervo.setRegistDate(now);
		memberdao.insertMember(membervo);
	}

	@Override
	public int memberCheck(MemberVO membervo) {
		
		return memberdao.memberCheck(membervo);
	}

	@Override
	public void deleteMember(String email, String provider) {
		memberdao.deleteMember(email, provider);
	}

	@Override
	public String callNickName(MemberVO membervo) {
		return memberdao.callNickName(membervo);
	}

	@Override
	public void updateNickName(MemberVO membervo) {
		memberdao.updateNickName(membervo);
	}

}

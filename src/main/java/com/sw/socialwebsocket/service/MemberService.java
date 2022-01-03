package com.sw.socialwebsocket.service;

import org.apache.ibatis.annotations.Param;

import com.sw.socialwebsocket.vo.MemberVO;


public interface MemberService {
	void insertMember(MemberVO membervo);
	void deleteMember(String email, String provider);
	int memberCheck(MemberVO membervo);
	String callNickName(MemberVO membervo);
	void updateNickName(MemberVO membervo);
}

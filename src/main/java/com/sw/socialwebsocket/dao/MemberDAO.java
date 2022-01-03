package com.sw.socialwebsocket.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sw.socialwebsocket.vo.MemberVO;


@Mapper
public interface MemberDAO {
	void insertMember(MemberVO membervo);
	void deleteMember(@Param("email") String email, @Param("provider")String provider);
	int memberCheck(MemberVO membervo);
	String callNickName(MemberVO membervo);
	void updateNickName(MemberVO membervo);
}

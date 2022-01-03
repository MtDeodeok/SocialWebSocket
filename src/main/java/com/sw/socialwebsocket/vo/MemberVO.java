package com.sw.socialwebsocket.vo;

import java.time.LocalDate;

import lombok.Data;

@Data
public class MemberVO {
	private int idx;
	private String email;
	private String name;
	private String nickName;
	private String provider;
	private LocalDate registDate;
	private String profile;
	private int grade;
	private String sessionID;
}

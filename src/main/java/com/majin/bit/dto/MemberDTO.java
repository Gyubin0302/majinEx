package com.majin.bit.dto;

import lombok.Data;

@Data
public class MemberDTO {
	private int mid;
	private String id;
	private String pw;
	private String name;
	private String nick;
	private String email;
	private String role;
	
	public Member toEntity() {
		return new Member(mid,id,pw,name,nick,email,role);
	}
}

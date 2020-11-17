package com.majin.bit.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity
@SequenceGenerator(name="seq",sequenceName = "MID",allocationSize = 1)
@Data
@Table(name = "member")
public class Member {
	@Id
	@GeneratedValue(generator = "seq",strategy = GenerationType.SEQUENCE)
	@Column(name = "mid")
	private int mid;
	@Column(name = "id")
	private String id;
	@Column(name = "pw")
	private String pw;
	@Column(name = "name")
	private String name;
	@Column(name = "nick")
	private String nick;
	@Column(name = "email")
	private String email;
	@Column(name = "role")
	private String role;
	
	public Member() {
	}
		
	public Member(int mid, String id, String pw, String name, String nick, String email, String role) {
		this.mid = mid;
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.nick = nick;
		this.email = email;
		this.role = role;
	}
}

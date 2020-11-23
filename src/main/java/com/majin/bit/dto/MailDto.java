package com.majin.bit.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "mailCertified")
public class MailDto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "mailId")
	private int mailId;
	@Column(name = "address")
    private String address;
	@Column(name = "message")
    private String message;
	@Column(name = "title")
	private String title;
	
	public MailDto(int mailId, String address, String message, String title) {
		super();
		this.mailId = mailId;
		this.address = address;
		this.message = message;
		this.title = title;
	}
	
}

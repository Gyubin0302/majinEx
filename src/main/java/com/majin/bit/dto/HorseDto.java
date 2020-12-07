package com.majin.bit.dto;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.IdClass;

import lombok.Data;

@Data
@IdClass(HorseDtoId.class)
public class HorseDto {
	
	@Id
	@Column
	private String hrNo; // 마번호
	
	@Id
	@Column
	private String meet; // 시행경마장구분(1.서울,2.제주,3.부산)
	
	@Id
	@Column
	private String act; // 08
	private String trNo; // 조교사 번호
	private String hrName; // 마명
	private String rating; // 레이팅
	private String ranks; // 등급
	private String nation; // 국적
	private String sex; // 성별
	private String birthDay; // 생년월일
	private String totalRecords; // 통산 전적
	private String ConsecutiveWinningP; // 연승률
	private String ComplementaryRate; // 복승률
	private String WinningP; // 승률
}

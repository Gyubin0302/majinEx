package com.majin.bit.dto;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.IdClass;

import lombok.Data;

@Data
@IdClass(JkDtoId.class)
public class JkDto {
	
	@Id
	@Column
	private String jkNo; // 기수 번호
	
	@Id
	@Column
	private String meet; // 시행경마장구분(1.서울,2.제주,3.부산)
	
	@Id
	@Column
	private String act; // 09
	private String jkName; // 기수 이름
	private String trNo; // 조교사 번호
	private String part; // 소속조
	private String jRweight; // 기승가능중량
	private String debut; // 데뷔 날짜
	private String spDate; // 은퇴날짜
	private String jYearTotal; // 최근 1년 전적
	private String jAllTotal; // 통산 전적
	private String jConsecutiveWinningP; // 연승률
	private String jComplementRyRate; // 복승률
	private String jWinningP; // 승률
}

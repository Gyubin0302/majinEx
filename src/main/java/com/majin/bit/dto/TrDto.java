package com.majin.bit.dto;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.IdClass;

import lombok.Data;

@Data
@IdClass(TrDtoId.class)
public class TrDto {
	
	@Id
	@Column
	private String trNo; // 조교사 번호
	
	@Id
	@Column
	private String meet; // 시행경마장구분(1.서울,2.제주,3.부산)
	
	@Id
	@Column
	private String act; // 10
	private String trName; // 조교사 이름
	private String part; // 소소족
	private String debut; // 데뷔 날짜
	private String tYearTotal; // 최근 1년 전적
	private String tAllTotal; // 통산 전적
	private String tConsecutiveWinningP; // 연승률
	private String tComplementRyRate; // 복승률
	private String tWinningP; // 승률
}

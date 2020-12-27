package com.majin.bit.dto;

import java.util.Date;

import lombok.Data;

@Data
public class SearchTermsDto {
	
	private int searchid;// 오토인크리먼트
	
	private String searchword;// 검색어
	
	private Date searchdate; // 검색 날짜 
	
	private int fale;// 식별번호
	
}

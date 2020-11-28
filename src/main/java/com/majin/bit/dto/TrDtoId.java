package com.majin.bit.dto;

import javax.persistence.Column;

public class TrDtoId {
	// 조교 복합키 
	@Column
	private String trNo;
	@Column
	private String meet;
	@Column
	private String act;
}

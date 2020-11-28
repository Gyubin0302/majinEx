package com.majin.bit.dto;

import javax.persistence.Column;

public class JkDtoId {
	// 기수 복합키
	@Column
	private String jkNo;
	@Column
	private String meet;
	@Column
	private String act;
}

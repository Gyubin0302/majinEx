package com.majin.bit.dto;

import javax.persistence.Column;

public class HorseDtoId {
	// 말 복합키
	@Column
	private String hrNo;
	@Column
	private String meet;
	@Column
	private String act;
}

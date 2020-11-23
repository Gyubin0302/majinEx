package com.majin.bit.dto;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import lombok.Data;

@Data
@Entity
public class YundoDto{
	@EmbeddedId
	private YundoJpaKey meet;
	@Column(name = "idSeq")
	private String idSeq;
	@Column(name = "jo")
	private String jo;
}

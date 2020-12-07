package com.majin.bit.dto;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import lombok.Data;

@Data
@Entity
public class YundoDto{
	@EmbeddedId
	private YundoJpaKey yundoJpaKey;
	@Column(name = "meetSeq")
	private String meetSeq;
	@Column(name = "idSeq")
	private String idSeq;
	@Column(name = "name")
	private String name;
	@Column(name = "jo")
	private String jo;
}

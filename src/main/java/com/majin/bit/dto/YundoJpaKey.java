package com.majin.bit.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class YundoJpaKey implements Serializable{
	@Column(name = "meet")
	private String meet;
	@Column(name = "year")
	private String year;
	
	public YundoJpaKey() {
	}
	
	public YundoJpaKey(String meet,String year) {
		this.meet = meet;
		this.year = year;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((meet == null) ? 0 : meet.hashCode());
		result = prime * result + ((year == null) ? 0 : year.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		YundoJpaKey other = (YundoJpaKey) obj;
		if (meet == null) {
			if (other.meet != null)
				return false;
		} else if (!meet.equals(other.meet))
			return false;
		if (year == null) {
			if (other.year != null)
				return false;
		} else if (!year.equals(other.year))
			return false;
		return true;
	}
	
}

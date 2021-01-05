package com.majin.bit.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;

@Getter
@Entity
@Table(name = "race")
public class RaceDto {
	@Id
	@Column(name = "idx")
	private int idx;
	@Column(name = "meet")
	private String meet;
	@Column(name = "rcdate")
	private int rcdate;
	@Column(name = "rcno")
	private int rcno;
	@Column(name = "rcdist")
	private int rcdist;
	@Column(name = "wgbudam")
	private double wgbudam;
	@Column(name = "chulno")
	private int chulno;
	@Column(name = "hrname")
	private String hrname;
	@Column(name = "name")
	private int name;
	@Column(name = "age")
	private int age;
	@Column(name = "sex")
	private int sex;
	@Column(name = "rating")
	private String rating;
	@Column(name = "sttime")
	private int sttime;
	@Column(name = "jconsecutivewinningp")
	private double jconsecutivewinningp;
	@Override
	public String toString() {
		return "RaceDto [idx=" + idx + ", meet=" + meet + ", rcdate=" + rcdate + ", rcno=" + rcno + ", rcdist=" + rcdist
				+ ", wgbudam=" + wgbudam + ", chulno=" + chulno + ", hrname=" + hrname + ", name=" + name + ", age="
				+ age + ", sex=" + sex + ", rating=" + rating + ", sttime=" + sttime + ", jconsecutivewinningp="
				+ jconsecutivewinningp + "]";
	}
	
}

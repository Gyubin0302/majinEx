package com.majin.bit.dto;

public class JkDto {
	private String jkNo; // 기수 번호
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

	public String getJkNo() {
		return jkNo;
	}

	public void setJkNo(String jkNo) {
		this.jkNo = jkNo;
	}

	public String getJkName() {
		return jkName;
	}

	public void setJkName(String jkName) {
		this.jkName = jkName;
	}

	public String getTrNo() {
		return trNo;
	}

	public void setTrNo(String trNo) {
		this.trNo = trNo;
	}

	public String getPart() {
		return part;
	}

	public void setPart(String part) {
		this.part = part;
	}

	public String getjRweight() {
		return jRweight;
	}

	public void setjRweight(String jRweight) {
		this.jRweight = jRweight;
	}

	public String getDebut() {
		return debut;
	}

	public void setDebut(String debut) {
		this.debut = debut;
	}

	public String getjYearTotal() {
		return jYearTotal;
	}

	public void setjYearTotal(String jYearTotal) {
		this.jYearTotal = jYearTotal;
	}

	public String getjAllTotal() {
		return jAllTotal;
	}

	public void setjAllTotal(String jAllTotal) {
		this.jAllTotal = jAllTotal;
	}

	public String getjConsecutiveWinningP() {
		return jConsecutiveWinningP;
	}

	public void setjConsecutiveWinningP(String jConsecutiveWinningP) {
		this.jConsecutiveWinningP = jConsecutiveWinningP;
	}

	public String getjComplementRyRate() {
		return jComplementRyRate;
	}

	public void setjComplementRyRate(String jComplementRyRate) {
		this.jComplementRyRate = jComplementRyRate;
	}

	public String getjWinningP() {
		return jWinningP;
	}

	public void setjWinningP(String jWinningP) {
		this.jWinningP = jWinningP;
	}

	public String getSpDate() {
		return spDate;
	}

	public void setSpDate(String spDate) {
		this.spDate = spDate;
	}

	@Override
	public String toString() {
		return "jkDto [jkNo=" + jkNo + ", jkName=" + jkName + ", trNo=" + trNo + ", part=" + part + ", jRweight="
				+ jRweight + ", debut=" + debut + ", spDate=" + spDate + ", jYearTotal=" + jYearTotal + ", jAllTotal="
				+ jAllTotal + ", jConsecutiveWinningP=" + jConsecutiveWinningP + ", jComplementRyRate="
				+ jComplementRyRate + ", jWinningP=" + jWinningP + "]";
	}

}

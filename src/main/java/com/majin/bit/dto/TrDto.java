package com.majin.bit.dto;

public class TrDto {
	private String trNo; //조교사 번호
	private String trName; // 조교사 이름
	private String part; // 소소족
	private String debut; // 데뷔 날짜
	private String tYearTotal; // 최근 1년 전적
	private String tAllTotal; // 통산 전적
	private String tConsecutiveWinningP; // 연승률 
	private String tComplementRyRate; // 복승률
	private String tWinningP; // 승률

	public String getTrNo() {
		return trNo;
	}

	public void setTrNo(String trNo) {
		this.trNo = trNo;
	}

	public String getTrName() {
		return trName;
	}

	public void setTrName(String trName) {
		this.trName = trName;
	}

	public String getPart() {
		return part;
	}

	public void setPart(String part) {
		this.part = part;
	}

	public String getDebut() {
		return debut;
	}

	public void setDebut(String debut) {
		this.debut = debut;
	}

	public String gettYearTotal() {
		return tYearTotal;
	}

	public void settYearTotal(String tYearTotal) {
		this.tYearTotal = tYearTotal;
	}

	public String gettAllTotal() {
		return tAllTotal;
	}

	public void settAllTotal(String tAllTotal) {
		this.tAllTotal = tAllTotal;
	}

	public String gettConsecutiveWinningP() {
		return tConsecutiveWinningP;
	}

	public void settConsecutiveWinningP(String tConsecutiveWinningP) {
		this.tConsecutiveWinningP = tConsecutiveWinningP;
	}

	public String gettComplementRyRate() {
		return tComplementRyRate;
	}

	public void settComplementRyRate(String tComplementRyRate) {
		this.tComplementRyRate = tComplementRyRate;
	}

	public String gettWinningP() {
		return tWinningP;
	}

	public void settWinningP(String tWinningP) {
		this.tWinningP = tWinningP;
	}

	@Override
	public String toString() {
		return "trDto [trNo=" + trNo + ", trName=" + trName + ", part=" + part + ", debut=" + debut + ", tYearTotal="
				+ tYearTotal + ", tAllTotal=" + tAllTotal + ", tConsecutiveWinningP=" + tConsecutiveWinningP
				+ ", tComplementRyRate=" + tComplementRyRate + ", tWinningP=" + tWinningP + "]";
	}

}

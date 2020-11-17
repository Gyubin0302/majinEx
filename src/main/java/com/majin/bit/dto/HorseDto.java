package com.majin.bit.dto;

public class HorseDto {
	private String hrNo;
	private String trNo;
	private String hrName;
	private String rating; //crawling
	private String rank;
	private String nation;
	private String sex; 
	private String birthDay;
	private String totalRecords; //crawling
	private String ConsecutiveWinningP; //crawling
 	private String ComplementaryRate; //crawling
	private String WinningP; //crawling

	public String getHrNo() {
		return hrNo;
	}

	public void setHrNo(String hrNo) {
		this.hrNo = hrNo;
	}

	public String getTrNo() {
		return trNo;
	}

	public void setTrNo(String trNo) {
		this.trNo = trNo;
	}

	public String getHrName() {
		return hrName;
	}

	public void setHrName(String hrName) {
		this.hrName = hrName;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	public String getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(String totalRecords) {
		this.totalRecords = totalRecords;
	}

	public String getConsecutiveWinningP() {
		return ConsecutiveWinningP;
	}

	public void setConsecutiveWinningP(String consecutiveWinningP) {
		ConsecutiveWinningP = consecutiveWinningP;
	}

	public String getComplementaryRate() {
		return ComplementaryRate;
	}

	public void setComplementaryRate(String complementaryRate) {
		ComplementaryRate = complementaryRate;
	}

	public String getWinningP() {
		return WinningP;
	}

	public void setWinningP(String winningP) {
		WinningP = winningP;
	}

	@Override
	public String toString() {
		return "HorseDto [hrNo=" + hrNo + ", trNo=" + trNo + ", hrName=" + hrName + ", rating=" + rating + ", rank="
				+ rank + ", nation=" + nation + ", sex=" + sex + ", birthDay=" + birthDay + ", totalRecords="
				+ totalRecords + ", ConsecutiveWinningP=" + ConsecutiveWinningP + ", ComplementaryRate="
				+ ComplementaryRate + ", WinningP=" + WinningP + "]";
	}

}

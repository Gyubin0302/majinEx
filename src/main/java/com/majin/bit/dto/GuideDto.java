package com.majin.bit.dto;

import java.util.Date;

public class GuideDto {

	private int guideNo;
	private String title;
	private String content;
	private Date write_date;

	public int getGuideNo() {
		return guideNo;
	}

	public void setGuideNo(int guideNo) {
		this.guideNo = guideNo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getWrite_date() {
		return write_date;
	}

	public void setWrite_date(Date write_date) {
		this.write_date = write_date;
	}

	@Override
	public String toString() {
		return "GuideDto [guideNo=" + guideNo + ", title=" + title + ", content=" + content + ", write_date="
				+ write_date + "]";
	}

}

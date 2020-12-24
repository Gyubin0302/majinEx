package com.majin.bit.dto;

import java.util.Date;

public class NoticeDto {

	private int boardNo;
	private String title;
	private String content;
	private Date writeDate;
	public int getBoardNo() {
		return boardNo;
	}
	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
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
	public Date getWriteDate() {
		return writeDate;
	}
	public void setWriteDate(Date writeDate) {
		this.writeDate = writeDate;
	}
	@Override
	public String toString() {
		return "NoticeDto [boardNo=" + boardNo + ", title=" + title + ", content=" + content + ", writeDate="
				+ writeDate + "]";
	}
	
	
}
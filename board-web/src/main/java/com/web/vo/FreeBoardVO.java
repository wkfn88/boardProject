package com.web.vo;

import java.sql.Timestamp;

public class FreeBoardVO {
	private int boardNum;
	private String boardTitle;
	private String memberId;
	private String boardPwd;
	private String boardContent;
	private Timestamp boardDate;
	private int readCount;
	private String timeMin;
	private int recommend;
	private int comment;
	private int anonymous;
	
	
	public int getAnonymous() {
		return anonymous;
	}
	public void setAnonymous(int anonymous) {
		this.anonymous = anonymous;
	}
	public int getComment() {
		return comment;
	}
	public void setComment(int comment) {
		this.comment = comment;
	}
	public int getRecommend() {
		return recommend;
	}
	public void setRecommend(int recommend) {
		this.recommend = recommend;
	}
	public String getTimeMin() {
		return timeMin;
	}
	public void setTimeMin(String timeMin) {
		this.timeMin = timeMin;
	}
	public int getBoardNum() {
		return boardNum;
	}
	public void setBoardNum(int boardNum) {
		this.boardNum = boardNum;
	}
	public String getBoardTitle() {
		return boardTitle;
	}
	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getBoardPwd() {
		return boardPwd;
	}
	public void setBoardPwd(String boardPwd) {
		this.boardPwd = boardPwd;
	}
	public String getBoardContent() {
		return boardContent;
	}
	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
	}
	public Timestamp getBoardDate() {
		return boardDate;
	}
	public void setBoardDate(Timestamp boardDate) {
		this.boardDate = boardDate;
	}
	public int getReadCount() {
		return readCount;
	}
	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}
}

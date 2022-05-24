package com.web.vo;

import java.sql.Timestamp;

public class CommentVO {
	private String commentNum;
	private int boardNum;
	private int commentNext;
	private String memberId;
	private String commentContent;
	private Timestamp commentDate;
	private String commentPwd;
	private int anonymous;
	
	
	
	public int getAnonymous() {
		return anonymous;
	}
	public void setAnonymous(int anonymous) {
		this.anonymous = anonymous;
	}
	public String getCommentNum() {
		return commentNum;
	}
	public void setCommentNum(String commentNum) {
		this.commentNum = commentNum;
	}
	public int getBoardNum() {
		return boardNum;
	}
	public void setBoardNum(int boardNum) {
		this.boardNum = boardNum;
	}
	public int getCommentNext() {
		return commentNext;
	}
	public void setCommentNext(int commentNext) {
		this.commentNext = commentNext;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	public Timestamp getCommentDate() {
		return commentDate;
	}
	public void setCommentDate(Timestamp commentDate) {
		this.commentDate = commentDate;
	}
	public String getCommentPwd() {
		return commentPwd;
	}
	public void setCommentPwd(String commentPwd) {
		this.commentPwd = commentPwd;
	}
	
}

package com.tumo.model;

public class CommentDto {
	private int boardIdx;
	private int userIdx;
	private String content;

	public CommentDto(int boardIdx, int userIdx, String content) {
		super();
		this.boardIdx = boardIdx;
		this.userIdx = userIdx;
		this.content = content;
	}

	public int getBoardIdx() {
		return boardIdx;
	}

	public void setBoardIdx(int boardIdx) {
		this.boardIdx = boardIdx;
	}

	public int getUserIdx() {
		return userIdx;
	}

	public void setUserIdx(int userIdx) {
		this.userIdx = userIdx;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "CommentDto [boardIdx=" + boardIdx + ", userIdx=" + userIdx + ", content=" + content + "]";
	}

}

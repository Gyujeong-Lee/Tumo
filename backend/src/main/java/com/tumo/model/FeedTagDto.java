package com.tumo.model;

public class FeedTagDto {
	private int boardIdx;
	private String content;

	public int getBoardIdx() {
		return boardIdx;
	}

	public void setBoardIdx(int boardIdx) {
		this.boardIdx = boardIdx;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public FeedTagDto(int boardIdx, String content) {
		super();
		this.boardIdx = boardIdx;
		this.content = content;
	}

	@Override
	public String toString() {
		return "FeedTagDto [boardIdx=" + boardIdx + ", content=" + content + "]";
	}

}

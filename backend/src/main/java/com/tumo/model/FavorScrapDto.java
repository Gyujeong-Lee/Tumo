package com.tumo.model;

public class FavorScrapDto {

	private int userIdx;
	private int boardIdx;

	public int getUserIdx() {
		return userIdx;
	}

	public void setUserIdx(int userIdx) {
		this.userIdx = userIdx;
	}

	public int getBoardIdx() {
		return boardIdx;
	}

	public void setBoardIdx(int boardIdx) {
		this.boardIdx = boardIdx;
	}

	public FavorScrapDto(int userIdx, int boardIdx) {
		super();
		this.userIdx = userIdx;
		this.boardIdx = boardIdx;
	}

	public FavorScrapDto() {
		super();
	}

	@Override
	public String toString() {
		return "FavorScrapDto [userIdx=" + userIdx + ", boardIdx=" + boardIdx + "]";
	}

}

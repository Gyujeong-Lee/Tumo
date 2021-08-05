package com.tumo.model;

public class FeedLikeDto {
	private int likeIdx;
	private int userIdx;
	private int boardIdx;
	private String createAt;

	public int getLikeIdx() {
		return likeIdx;
	}

	public void setLikeIdx(int likeIdx) {
		this.likeIdx = likeIdx;
	}

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

	public String getCreateAt() {
		return createAt;
	}

	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}

	public FeedLikeDto(int likeIdx, int userIdx, int boardIdx, String createAt) {
		super();
		this.likeIdx = likeIdx;
		this.userIdx = userIdx;
		this.boardIdx = boardIdx;
		this.createAt = createAt;
	}

	@Override
	public String toString() {
		return "FeedLikeDto [likeIdx=" + likeIdx + ", userIdx=" + userIdx + ", boardIdx=" + boardIdx + ", createAt="
			  + createAt + "]";
	}

}

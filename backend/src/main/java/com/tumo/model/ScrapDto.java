package com.tumo.model;

public class ScrapDto {

	private int scrapIdx;
	private int userIdx;
	private String nickname;
	private int boardIdx;
	private String title;

	public int getScrapIdx() {
		return scrapIdx;
	}

	public void setScrapIdx(int scrapIdx) {
		this.scrapIdx = scrapIdx;
	}

	public int getUserIdx() {
		return userIdx;
	}

	public void setUserIdx(int userIdx) {
		this.userIdx = userIdx;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getBoardIdx() {
		return boardIdx;
	}

	public void setBoardIdx(int boardIdx) {
		this.boardIdx = boardIdx;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ScrapDto() {
		super();
	}

	public ScrapDto(int scrapIdx, int userIdx, String nickname, int boardIdx, String title) {
		super();
		this.scrapIdx = scrapIdx;
		this.userIdx = userIdx;
		this.nickname = nickname;
		this.boardIdx = boardIdx;
		this.title = title;
	}

	@Override
	public String toString() {
		return "ScrapDto [scrapIdx=" + scrapIdx + ", userIdx=" + userIdx + ", nickname=" + nickname + ", boardIdx="
				+ boardIdx + ", title=" + title + "]";
	}

}

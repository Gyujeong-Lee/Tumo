package com.tumo.model;

public class ScrapDto {

	private int scrapIdx;
	private int userIdx;
	private String nickname;
	private int articleIdx;
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

	public int getArticleIdx() {
		return articleIdx;
	}

	public void setArticleIdx(int articleIdx) {
		this.articleIdx = articleIdx;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ScrapDto(int scrapIdx, int userIdx, String nickname, int articleIdx, String title) {
		super();
		this.scrapIdx = scrapIdx;
		this.userIdx = userIdx;
		this.nickname = nickname;
		this.articleIdx = articleIdx;
		this.title = title;
	}

	@Override
	public String toString() {
		return "ScrapDto [scrapIdx=" + scrapIdx + ", userIdx=" + userIdx + ", nickname=" + nickname + ", articleIdx="
				+ articleIdx + ", title=" + title + "]";
	}

}

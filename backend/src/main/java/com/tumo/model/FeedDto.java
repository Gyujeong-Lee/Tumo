package com.tumo.model;

public class FeedDto {
	private int boardIdx;
	private int userIdx;
	private String nickname;
	private int likes;
	private String title;
	private String content;
	private String createAt;
	private String updateAt;

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

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
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

	public String getCreateAt() {
		return createAt;
	}

	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}

	public String getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(String updateAt) {
		this.updateAt = updateAt;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public FeedDto(int boardIdx, int userIdx, String nickname, int likes, String title, String content, String createAt,
			String updateAt) {
		super();
		this.boardIdx = boardIdx;
		this.userIdx = userIdx;
		this.nickname = nickname;
		this.likes = likes;
		this.title = title;
		this.content = content;
		this.createAt = createAt;
		this.updateAt = updateAt;
	}

	@Override
	public String toString() {
		return "FeedDto [boardIdx=" + boardIdx + ", userIdx=" + userIdx + ", nickname=" + nickname + ", likes=" + likes
				+ ", title=" + title + ", content=" + content + ", createAt=" + createAt + ", updateAt=" + updateAt
				+ "]";
	}

}

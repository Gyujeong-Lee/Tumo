package com.tumo.model;

public class FeedCommentDto {
	private int commentIdx;
	private int boardIdx;
	private int userIdx;
	private String nickname;
	private String content;
	private String createAt;
	private String updateAt;

	public FeedCommentDto() {
		super();
	}

	public FeedCommentDto(int boardIdx, int userIdx, String content) {
		super();
		this.boardIdx = boardIdx;
		this.userIdx = userIdx;
		this.content = content;
	}

	public FeedCommentDto(int commentIdx, int boardIdx, int userIdx, String nickname, String content, String createAt,
			String updateAt) {
		super();
		this.commentIdx = commentIdx;
		this.boardIdx = boardIdx;
		this.userIdx = userIdx;
		this.nickname = nickname;
		this.content = content;
		this.createAt = createAt;
		this.updateAt = updateAt;
	}

	public FeedCommentDto(int commentIdx, int boardIdx, int userIdx, String content, String createAt, String updateAt) {
		super();
		this.commentIdx = commentIdx;
		this.boardIdx = boardIdx;
		this.userIdx = userIdx;
		this.content = content;
		this.createAt = createAt;
		this.updateAt = updateAt;
	}

	public int getCommentIdx() {
		return commentIdx;
	}

	public void setCommentIdx(int commentIdx) {
		this.commentIdx = commentIdx;
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

	@Override
	public String toString() {
		return "FeedCommentDto [commentIdx=" + commentIdx + ", boardIdx=" + boardIdx + ", userIdx=" + userIdx
				+ ", nickname=" + nickname + ", content=" + content + ", createAt=" + createAt + ", updateAt="
				+ updateAt + "]";
	}

}

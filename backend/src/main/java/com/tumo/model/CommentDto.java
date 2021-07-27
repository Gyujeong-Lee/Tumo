package com.tumo.model;

public class CommentDto {
	private int commentIdx;
	private int userIdx;
	private int articleIdx;
	private String content;
	private String createAt;
	private String updateAt;
	
	public CommentDto(int commentIdx, int userIdx, int articleIdx, String content, String createAt, String updateAt) {
		super();
		this.commentIdx = commentIdx;
		this.userIdx = userIdx;
		this.articleIdx = articleIdx;
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

	public int getUserIdx() {
		return userIdx;
	}

	public void setUserIdx(int userIdx) {
		this.userIdx = userIdx;
	}

	public int getArticleIdx() {
		return articleIdx;
	}

	public void setArticleIdx(int articleIdx) {
		this.articleIdx = articleIdx;
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

	@Override
	public String toString() {
		return "CommentDto [commentIdx=" + commentIdx + ", userIdx=" + userIdx + ", articleIdx=" + articleIdx
				+ ", content=" + content + ", createAt=" + createAt + ", updateAt=" + updateAt + "]";
	}
}

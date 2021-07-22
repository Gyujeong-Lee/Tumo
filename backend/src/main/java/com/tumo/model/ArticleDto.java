package com.tumo.model;

public class ArticleDto {

	private int articleIdx;
	private int userIdx;
	private String nickName;
	private String title;
	private String content;
	private String update_at;
	private String create_at;
	private int likeCnt;

	public int getArticleIdx() {
		return articleIdx;
	}

	public void setArticleIdx(int articleIdx) {
		this.articleIdx = articleIdx;
	}

	public int getUserIdx() {
		return userIdx;
	}

	public void setUserIdx(int userIdx) {
		this.userIdx = userIdx;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
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

	public String getUpdate_at() {
		return update_at;
	}

	public void setUpdate_at(String update_at) {
		this.update_at = update_at;
	}

	public String getCreate_at() {
		return create_at;
	}

	public void setCreate_at(String create_at) {
		this.create_at = create_at;
	}

	public int getLikeCnt() {
		return likeCnt;
	}

	public void setLikeCnt(int likeCnt) {
		this.likeCnt = likeCnt;
	}

	public ArticleDto(int articleIdx, int userIdx, String nickName, String title, String content, String update_at,
			String create_at, int likeCnt) {
		super();
		this.articleIdx = articleIdx;
		this.userIdx = userIdx;
		this.nickName = nickName;
		this.title = title;
		this.content = content;
		this.update_at = update_at;
		this.create_at = create_at;
		this.likeCnt = likeCnt;
	}

	@Override
	public String toString() {
		return "ArticleDto [articleIdx=" + articleIdx + ", content=" + content + ", create_at=" + create_at
				+ ", likeCnt=" + likeCnt + ", nickName=" + nickName + ", title=" + title + ", update_at=" + update_at
				+ ", userIdx=" + userIdx + "]";
	}

}

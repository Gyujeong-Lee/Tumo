package com.tumo.model;

import java.util.List;

public class ArticleDto {

	private int userIdx;
	private String stock;
	private String title;
	private String content;
	private List<String> tags;

	public int getUserIdx() {
		return userIdx;
	}

	public void setUserIdx(int userIdx) {
		this.userIdx = userIdx;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
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

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public ArticleDto() {
		super();
	}

	public ArticleDto(int userIdx, String stock, String title, String content, List<String> tags) {
		super();
		this.userIdx = userIdx;
		this.stock = stock;
		this.title = title;
		this.content = content;
		this.tags = tags;
	}

	@Override
	public String toString() {
		return "ArticleDto [userIdx=" + userIdx + ", stock=" + stock + ", title=" + title
				+ ", content=" + content + ", tags=" + tags + "]";
	}

}

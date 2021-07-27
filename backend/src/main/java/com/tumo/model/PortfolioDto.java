package com.tumo.model;

import java.util.List;
import java.util.Map;

public class PortfolioDto {
	
	int portfolioIdx;
	int userIdx;
	String title;
	String content;
	double goal;
	public int getPortfolioIdx() {
		return portfolioIdx;
	}
	public void setPortfolioIdx(int portfolioIdx) {
		this.portfolioIdx = portfolioIdx;
	}
	public int getUserIdx() {
		return userIdx;
	}
	public PortfolioDto(int portfolioIdx, int userIdx, String title, String content, double goal) {
		super();
		this.portfolioIdx = portfolioIdx;
		this.userIdx = userIdx;
		this.title = title;
		this.content = content;
		this.goal = goal;
	}
	public void setUserIdx(int userIdx) {
		this.userIdx = userIdx;
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
	public double getGoal() {
		return goal;
	}
	public void setGoal(double goal) {
		this.goal = goal;
	}
	
}

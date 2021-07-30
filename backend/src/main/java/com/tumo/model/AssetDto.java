package com.tumo.model;

public class AssetDto {
	private int assetIdx;
	private int portfolioIdx;
	private String stock_code;
	private int quantity;
	private int price;
	private double goal;
	public int getAssetIdx() {
		return assetIdx;
	}
	public void setAssetIdx(int assetIdx) {
		this.assetIdx = assetIdx;
	}
	public int getPortfolioIdx() {
		return portfolioIdx;
	}
	public void setPortfolioIdx(int portfolioIdx) {
		this.portfolioIdx = portfolioIdx;
	}
	public String getStock_code() {
		return stock_code;
	}
	public void setStock_code(String stock_code) {
		this.stock_code = stock_code;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public double getGoal() {
		return goal;
	}
	public void setGoal(double goal) {
		this.goal = goal;
	}
	
}

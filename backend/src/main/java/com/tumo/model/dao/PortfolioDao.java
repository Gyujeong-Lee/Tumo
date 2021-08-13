package com.tumo.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tumo.model.AssetDto;
import com.tumo.model.PortfolioDto;

public interface PortfolioDao {

	
	boolean createList(PortfolioDto portfoliodto);

	List<Map<Object, Object>> readList(int userIdx);
	
	boolean deleteList(int userIdx);

	boolean createAsset(AssetDto assetDto);

	List<Map<Object, Object>> readAsset(int portfolioIdx);
	
	boolean deleteAsset(int assetIdx);

	List<Map<Object, Object>> readFeedList(HashMap<String, Object> tmp);

	List<Map<Object, Object>> searchPortfolio(HashMap<String, Object> tmp);

	int recentPortfolio(int userIdx);

	void updateRank();

	void updateYield();

	void updateList(PortfolioDto portfoliodto);

	void updateAsset(AssetDto assetDto);

	String readRank(String nickname);

	List<Map<Object, Object>> readTopPortfolio();
}

package com.tumo.model.dao;

import java.util.List;
import java.util.Map;

import com.tumo.model.AssetDto;
import com.tumo.model.PortfolioDto;

public interface PortfolioDao {

	boolean createList(PortfolioDto portfoliodto);

	List<PortfolioDto> readList(int userIdx);
	
	boolean deleteList(int userIdx);

	boolean createAsset(AssetDto assetDto);

	List<Map<Object, Object>> readAsset(int portfolioIdx);
	
	boolean deleteAsset(int assetIdx);

	List<Map<Object, Object>> readFeedList(int userIdx, int pageNum);

	List<Map<Object, Object>> searchPortfolio(String searchContent, String pageNum);

	int recentPortfolio(int userIdx);
}

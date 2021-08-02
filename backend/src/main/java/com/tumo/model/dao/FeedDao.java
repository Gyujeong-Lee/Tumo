package com.tumo.model.dao;

import java.util.HashMap;
import java.util.List;

import com.tumo.model.ArticleDto;
import com.tumo.model.FeedDto;

public interface FeedDao {

	List<FeedDto> readFeed(int boardIdx);

	List<FeedDto> searchFeed(String title);

	void createArticle(ArticleDto articleDto);
	
	int findBoardIdxByArticle(ArticleDto articleDto);
	
	FeedDto readArticle(int boardIdx);
	
	List<String> readFeedTag(int boardIdx);
	
	void updateArticle(FeedDto feedDto);

	void deleteArticle(int boardIdx);

	void createFeedTag(HashMap<String, Object> tagMap);

	void deleteFeedTag(int boardIdx);

}

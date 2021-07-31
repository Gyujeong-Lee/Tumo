package com.tumo.model.dao;

import java.util.HashMap;
import java.util.List;

import com.tumo.model.FeedDto;

public interface FeedDao {

	List<FeedDto> readFeed(int boardIdx);

	List<FeedDto> searchFeed(String title);

	void createArticle(FeedDto newArticle);
	
	void readArticle(int boardIdx);
	
	void updateArticle(FeedDto updateArticle);

	void deleteArticle(int boardIdx);

}

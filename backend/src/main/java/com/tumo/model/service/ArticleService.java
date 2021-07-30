package com.tumo.model.service;

import java.util.HashMap;

import com.tumo.model.FeedDto;

public interface ArticleService {

	boolean createArticle(FeedDto newArticle);

	boolean readArticle(int boardIdx);
	
	boolean updateArticle(FeedDto updateArticle);
	
	boolean deleteArticle(int boardIdx);

}

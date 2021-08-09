package com.tumo.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tumo.model.ArticleDto;
import com.tumo.model.CommentDto;
import com.tumo.model.FeedCommentDto;
import com.tumo.model.FeedDto;

public interface FeedDao {

	List<FeedDto> readFeed(Map<String, Object> param);

	List<FeedDto> searchFeed(String title);

	void createArticle(ArticleDto articleDto);
	
	int findBoardIdxByArticle(ArticleDto articleDto);
	
	FeedDto readArticle(int boardIdx);
	
	List<String> readFeedTag(int boardIdx);
	
	void updateArticle(FeedDto feedDto);

	void deleteArticle(int boardIdx);

	void createFeedTag(HashMap<String, Object> tagMap);

	void deleteFeedTag(int boardIdx);

	void createComment(CommentDto commentDto);

	int countCommentInFeed(int boardIdx);

	List<FeedCommentDto> readComment(Map<String, Integer> param);

	void deleteComment(int commentIdx);

	List<FeedDto> searchFeed(Map<String, Object> param);

	int readFeedPageCnt(int userIdx);
	
	List<Map<Object, Object>> readHotKeyword();

}

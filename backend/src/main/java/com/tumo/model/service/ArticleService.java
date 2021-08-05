package com.tumo.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tumo.model.ArticleDto;
import com.tumo.model.CommentDto;
import com.tumo.model.FeedCommentDto;
import com.tumo.model.FeedDto;

public interface ArticleService {

	boolean createArticle(ArticleDto articleDto);

	int findBoardIdxByArticle(ArticleDto articleDto);

	boolean createFeedTags(int boardIdx, List<String> tags);

	HashMap<String, Object> readArticle(Map<String, Integer> param);

	boolean updateArticle(Map<String, Object> param);

	boolean deleteArticle(int boardIdx);

	boolean createComment(CommentDto commentDto);

	List<FeedCommentDto> readComment(Map<String, Integer> param);

	boolean deleteComment(int commentIdx);

	int readCommentPageCnt(int boardIdx);

}

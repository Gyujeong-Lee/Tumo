package com.tumo.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface FeedService {

	List<HashMap<String, Object>> readFeed(Map<String, Object> param);

	List<HashMap<String, Object>> searchFeed(Map<String, Object> param);

	List<Map<Object, Object>> readHotKeyword();

	int readFeedPageCnt(int userIdx);

	List<HashMap<String, Object>> readRecommendedArticles(int boardIdx);

}

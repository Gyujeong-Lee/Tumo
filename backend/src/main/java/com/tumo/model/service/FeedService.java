package com.tumo.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tumo.model.FeedDto;

public interface FeedService {

	List<HashMap<String, Object>> readFeed(Map<String, Object> param);

	List<HashMap<String, Object>> searchFeed(Map<String, Object> param);

	List<FeedDto> readHotkey(int boardIdx);

	int readFeedPageCnt(int userIdx);

}

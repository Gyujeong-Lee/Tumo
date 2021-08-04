package com.tumo.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tumo.model.FeedDto;
import com.tumo.model.FeedLikeDto;
import com.tumo.model.ScrapDto;

public interface FeedService {
	
	List<HashMap<String, Object>> readFeed(Map<String, Object> param);
	
	List<FeedDto> searchFeedByTitle(String title);
	
	List<FeedDto> readHotkey(int boardIdx);

}

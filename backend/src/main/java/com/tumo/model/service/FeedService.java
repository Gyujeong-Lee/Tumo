package com.tumo.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tumo.model.FeedDto;
import com.tumo.model.FeedLikeDto;
import com.tumo.model.ScrapDto;

public interface FeedService {
	
	List<FeedDto> readFeed(int boardIdx);
	
	List<FeedDto> searchFeedByTitle(String title);
	
	List<FeedDto> readComment(int boardIdx);

	List<FeedDto> readHotkey(int boardIdx);


}

package com.tumo.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tumo.model.FeedLikeDto;
import com.tumo.model.ScrapDto;

public interface SNSDao {

	int createScrap(HashMap<String, Integer> info);
	
	List<ScrapDto> readScrapList(int userIdx);
	
	int deleteScrap(HashMap<String, Integer> info);

	void createFavor(HashMap<String, Integer> info);

	FeedLikeDto readIsLike(Map<String, Integer> param);

	void deleteFavor(HashMap<String, Integer> info);

}

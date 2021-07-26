package com.tumo.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tumo.model.FeedDto;
import com.tumo.model.FeedLikeDto;
import com.tumo.model.ScrapDto;
import com.tumo.model.UserDto;

public interface SNSDao {

	int createScrap(HashMap<String, Integer> info);
	
	List<ScrapDto> readScrapList(int userIdx);
	
	int deleteScrap(HashMap<String, Integer> info);

	void addFavor(int boardIdx);

	void createFavor(HashMap<String, Integer> info);

	FeedLikeDto readIsLike(Map<String, Integer> param);

	void deleteFavor(HashMap<String, Integer> info);

	void subFavor(int boardIdx);

	List<FeedDto> readMyPost(int userIdx);

	List<UserDto> searchUser(Map<String, Object> param);

	int countSearchedUser(Map<String, Object> param);

	ProfileDto readUser(int userIdx);

	Integer getFollowingCount(int userIdx);

	Integer getFollowerCount(int userIdx);

}

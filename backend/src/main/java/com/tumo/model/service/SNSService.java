package com.tumo.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tumo.model.FavorScrapDto;
import com.tumo.model.FeedDto;
import com.tumo.model.FeedLikeDto;
import com.tumo.model.ProfileDto;
import com.tumo.model.ScrapDto;

public interface SNSService {

	boolean createScrap(FavorScrapDto info);

	List<ScrapDto> readScrapList(int userIdx);

	boolean deleteScrap(FavorScrapDto param);

	boolean createFavor(FavorScrapDto favorDto);

	FeedLikeDto readIsLike(FavorScrapDto param);

	boolean deleteFavor(FavorScrapDto param);

	List<FeedDto> readMyPost(int userIdx);

	List<Map<String, Object>> searchUser(String searchContent, int pageNum);

	HashMap<String, Object> readUser(String nickname);

	List<ProfileDto> readFollowerList(int userIdx);

	List<ProfileDto> readFollowingList(int userIdx);

	Boolean readIsFollow(Map<String, Object> param);

	void deleteFollowing(Map<String, Object> param);

	String createFollowRequest(HashMap<String, Integer> info);

	String createFollowing(HashMap<String, Integer> info);

	void deleteFollowingRequest(HashMap<String, Integer> param);

}

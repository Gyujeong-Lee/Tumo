package com.tumo.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tumo.model.FavorScrapDto;
import com.tumo.model.FeedDto;
import com.tumo.model.FeedLikeDto;
import com.tumo.model.NotificationDto;
import com.tumo.model.ProfileDto;
import com.tumo.model.ScrapDto;

public interface SNSDao {

	int createScrap(FavorScrapDto info);

	List<ScrapDto> readScrapList(int userIdx);

	int deleteScrap(FavorScrapDto info);

	Map<String, Object> readIsScrap(FavorScrapDto param);

	void addFavor(int boardIdx);

	void createFavor(FavorScrapDto info);

	FeedLikeDto readIsLike(FavorScrapDto param);

	void deleteFavor(FavorScrapDto info);

	void subFavor(int boardIdx);

	List<FeedDto> readMyPost(int userIdx);

	List<Map<String, Object>> searchUser(Map<String, Object> param);

	int countSearchedUser(Map<String, Object> param);

	ProfileDto readUser(int userIdx);

	Integer getFollowingCount(int userIdx);

	Integer getFollowerCount(int userIdx);

	List<ProfileDto> readFollowerList(int userIdx);

	List<ProfileDto> readFollowingList(int userIdx);

	Map<String, Integer> readIsFollow(Map<String, Object> param);

	void deleteFollowing(Map<String, Object> param);

	void createFollowing(HashMap<String, Integer> info);

	void notifyFollowRequest(HashMap<String, Integer> info);

	void deleteNotifiedFollowRequest(HashMap<String, Integer> info);

	void deleteFollowRequest(Map<String, Object> param);

	List<NotificationDto> readAlarmList(int userIdx);

	void updateAlarm(int notificationIdx);

}

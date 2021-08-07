package com.tumo.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tumo.model.FavorScrapDto;
import com.tumo.model.FeedDto;
import com.tumo.model.FeedLikeDto;
import com.tumo.model.FollowDto;
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

	FollowDto readIsFollow(FollowDto followDto);

	void deleteFollowing(FollowDto followDto);

	void createFollowing(FollowDto followDto);

	void deleteNotifiedFollowRequest(Map<String, Object> param);

	void deleteFollowRequest(Map<String, Object> param);

	List<NotificationDto> readAlarmList(int userIdx);

	void updateAlarm(int notificationIdx);

	void createNotification(Map<String, Object> param);

}

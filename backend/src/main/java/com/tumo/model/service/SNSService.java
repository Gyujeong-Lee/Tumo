package com.tumo.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tumo.model.FeedDto;
import com.tumo.model.FeedLikeDto;
import com.tumo.model.ScrapDto;
import com.tumo.model.UserDto;

public interface SNSService {

	boolean createScrap(HashMap<String, Integer> info);

	List<ScrapDto> readScrapList(int userIdx);

	boolean deleteScrap(HashMap<String, Integer> info);

	boolean createFavor(HashMap<String, Integer> info);

	FeedLikeDto readIsLike(Map<String, Integer> param);

	boolean deleteFavor(HashMap<String, Integer> info);

	List<FeedDto> readMyPost(int userIdx);

	List<UserDto> searchUser(String searchContent, int pageNum);

}

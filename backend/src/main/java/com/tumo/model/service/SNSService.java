package com.tumo.model.service;

import java.util.HashMap;
import java.util.List;

import com.tumo.model.ArticleDto;
import com.tumo.model.ScrapDto;

public interface SNSService {

	boolean registScrap(HashMap<String, Integer> info);

	List<ScrapDto> showScrapList(int userId);

	boolean deleteScrap(HashMap<String, Integer> info);

}

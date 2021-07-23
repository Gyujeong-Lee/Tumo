package com.tumo.model.dao;

import java.util.HashMap;
import java.util.List;

import com.tumo.model.ScrapDto;

public interface SNSDao {

	int registScrap(HashMap<String, Integer> info);
	
	List<ScrapDto> showScrapList(int userId);
	
	int deleteScrap(HashMap<String, Integer> info);

}

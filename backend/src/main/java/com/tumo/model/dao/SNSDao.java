package com.tumo.model.dao;

import java.util.List;

import com.tumo.model.ArticleDto;
import com.tumo.model.ScrapDto;

public interface SNSDao {

	int registScrap(ArticleDto info);
	
	List<ScrapDto> showScrapList(int userId);
	
	int deleteScrap(ScrapDto info);

}

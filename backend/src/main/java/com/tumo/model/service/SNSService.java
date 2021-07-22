package com.tumo.model.service;

import java.util.List;

import com.tumo.model.ArticleDto;
import com.tumo.model.ScrapDto;

public interface SNSService {

	boolean registScrap(ArticleDto info);

	List<ScrapDto> showScrapList(int userId);

	boolean deleteScrap(ScrapDto info);

}

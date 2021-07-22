package com.tumo.model.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tumo.model.ArticleDto;
import com.tumo.model.ScrapDto;
import com.tumo.model.dao.SNSDao;

@Service
public class SNSServiceImpl implements SNSService {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public boolean registScrap(ArticleDto info) {
		sqlSession.getMapper(SNSDao.class).registScrap(info);
		return true;
	}

	@Override
	public List<ScrapDto> showScrapList(int userId) {
		return sqlSession.getMapper(SNSDao.class).showScrapList(userId);
	}

	@Override
	public boolean deleteScrap(ScrapDto info) {
		sqlSession.getMapper(SNSDao.class).deleteScrap(info);
		return true;
	}

}

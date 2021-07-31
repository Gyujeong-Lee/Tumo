package com.tumo.model.service;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tumo.model.FeedDto;
import com.tumo.model.dao.FeedDao;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public boolean createArticle(FeedDto feedDto) {
		FeedDto newArticle = new FeedDto(feedDto.getTitle(), feedDto.getContent());
		sqlSession.getMapper(FeedDao.class).createArticle(newArticle);
		return true;
	}
	
	@Override
	public boolean readArticle(int boardIdx) {
		sqlSession.getMapper(FeedDao.class).readArticle(boardIdx);
		return false;
	}
	
	@Override
	public boolean updateArticle(FeedDto feedDto) {
		FeedDto updateArticle = new FeedDto(feedDto.getTitle(), feedDto.getContent());
		sqlSession.getMapper(FeedDao.class).updateArticle(updateArticle);
		return false;
	}

	@Override
	public boolean deleteArticle(int boardIdx) {
		sqlSession.getMapper(FeedDao.class).deleteArticle(boardIdx);
		return true;
	}


}

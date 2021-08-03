package com.tumo.model.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tumo.model.FeedDto;
import com.tumo.model.dao.FeedDao;
import com.tumo.model.dao.SNSDao;

@Service
public class FeedServiceImpl implements FeedService {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public List<FeedDto> readFeed(int userIdx) {
		return sqlSession.getMapper(FeedDao.class).readFeed(userIdx);
	}

	@Override
	public List<FeedDto> searchFeedByTitle(String title) {
		return sqlSession.getMapper(FeedDao.class).searchFeed(title);
	}

	@Override
	public List<FeedDto> readHotkey(int boardIdx) {
		// TODO Auto-generated method stub
		return null;
	}

}

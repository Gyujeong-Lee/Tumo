package com.tumo.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tumo.model.FeedDto;
import com.tumo.model.dao.FeedDao;
import com.tumo.model.dao.SNSDao;

@Service
public class FeedServiceImpl implements FeedService {

	@Autowired
	private SqlSession sqlSession;

	@Override
	@Transactional
	public List<HashMap<String, Object>> readFeed(Map<String, Object> param) {
		List<FeedDto> feedDtoList = sqlSession.getMapper(FeedDao.class).readFeed(param);
		List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();

		int userIdx = (int) param.get("userIdx");
		for (FeedDto feedDto : feedDtoList) {
			Map<String, Integer> param2 = new HashMap<String, Integer>();
			int boardIdx = feedDto.getBoardIdx();
			param2.put("userIdx", userIdx);
			param2.put("boardIdx", boardIdx);

			HashMap<String, Object> tmp = new HashMap<String, Object>();
			tmp.put("boardIdx", boardIdx);
			tmp.put("userIdx", feedDto.getUserIdx());
			tmp.put("nickname", feedDto.getNickname());
			tmp.put("title", feedDto.getTitle());
			tmp.put("content", feedDto.getContent());
			tmp.put("likes", feedDto.getLikes());
			tmp.put("stock", feedDto.getStock());
			tmp.put("tags", sqlSession.getMapper(FeedDao.class).readFeedTag(boardIdx));
			tmp.put("isLike", sqlSession.getMapper(SNSDao.class).readIsLike(param2) == null ? false : true);
			tmp.put("isScrap", sqlSession.getMapper(SNSDao.class).readIsScrap(param2) == null ? false : true);

			result.add(tmp);
		}

		return result;
	}

	@Override
	public List<HashMap<String, Object>> searchFeed(Map<String, Object> param) {
		List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();

		System.out.println(param.get("searchContent"));
		List<FeedDto> feedDtoList = sqlSession.getMapper(FeedDao.class).searchFeed(param);
		
		for(FeedDto feedDto : feedDtoList) {
			int boardIdx = feedDto.getBoardIdx();
			HashMap<String, Object> tmp = new HashMap<String, Object>();
			
			tmp.put("boardIdx", boardIdx);
			tmp.put("userIdx", feedDto.getUserIdx());
			tmp.put("nickname", feedDto.getNickname());
			tmp.put("title", feedDto.getTitle());
			tmp.put("content", feedDto.getContent());
			tmp.put("likes", feedDto.getLikes());
			tmp.put("stock", feedDto.getStock());
			tmp.put("tags", sqlSession.getMapper(FeedDao.class).readFeedTag(boardIdx));

			result.add(tmp);
		}
		
		
		return result;
	}

	@Override
	public List<FeedDto> readHotkey(int boardIdx) {
		// TODO Auto-generated method stub
		return null;
	}

}

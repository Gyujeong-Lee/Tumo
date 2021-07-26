package com.tumo.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tumo.model.FeedDto;
import com.tumo.model.FeedLikeDto;
import com.tumo.model.ScrapDto;
import com.tumo.model.UserDto;
import com.tumo.model.dao.SNSDao;

@Service
public class SNSServiceImpl implements SNSService {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public boolean createScrap(HashMap<String, Integer> info) {
		sqlSession.getMapper(SNSDao.class).createScrap(info);
		return true;
	}

	@Override
	public List<ScrapDto> readScrapList(int userIdx) {
		return sqlSession.getMapper(SNSDao.class).readScrapList(userIdx);
	}

	@Override
	public boolean deleteScrap(HashMap<String, Integer> info) {
		sqlSession.getMapper(SNSDao.class).deleteScrap(info);
		return true;
	}

	@Override
	@Transactional
	public boolean createFavor(HashMap<String, Integer> info) {
		sqlSession.getMapper(SNSDao.class).addFavor(info.get("boardIdx"));
		sqlSession.getMapper(SNSDao.class).createFavor(info);
		return true;
	}

	@Override
	public FeedLikeDto readIsLike(Map<String, Integer> param) {
		return sqlSession.getMapper(SNSDao.class).readIsLike(param);
	}

	@Override
	@Transactional
	public boolean deleteFavor(HashMap<String, Integer> info) {
		sqlSession.getMapper(SNSDao.class).subFavor(info.get("boardIdx"));
		sqlSession.getMapper(SNSDao.class).deleteFavor(info);
		return true;
	}

	@Override
	public List<FeedDto> readMyPost(int userIdx) {
		return sqlSession.getMapper(SNSDao.class).readMyPost(userIdx);
	}

	@Override
	public List<UserDto> searchUser(String searchContent, int pageNum) {
		searchContent = searchContent.replaceAll("\\+", " ");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("searchContent", searchContent);
		param.put("pageNum", pageNum * 5);
		int cnt = sqlSession.getMapper(SNSDao.class).countSearchedUser(param);
		
		System.out.println("searchContent = "+param.get("searchContent"));
		if(cnt == 0)
			return null;
		
		return sqlSession.getMapper(SNSDao.class).searchUser(param);
	}

}

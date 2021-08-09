package com.tumo.model.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tumo.model.FavorScrapDto;
import com.tumo.model.FeedDto;
import com.tumo.model.dao.FeedDao;
import com.tumo.model.dao.SNSDao;
import com.tumo.model.dao.UserDao;

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
			int boardIdx = feedDto.getBoardIdx();

			HashMap<String, Object> tmp = new HashMap<String, Object>();
			tmp.put("boardIdx", boardIdx);
			tmp.put("userIdx", feedDto.getUserIdx());
			tmp.put("nickname", feedDto.getNickname());
			tmp.put("title", feedDto.getTitle());
			tmp.put("content", feedDto.getContent());
			tmp.put("likes", feedDto.getLikes());
			tmp.put("stock", feedDto.getStock());
			tmp.put("createAt", feedDto.getCreateAt());
			tmp.put("updateAt", feedDto.getUpdateAt());
			tmp.put("tags", sqlSession.getMapper(FeedDao.class).readFeedTag(boardIdx));
			tmp.put("isLike", sqlSession.getMapper(SNSDao.class).readIsLike(new FavorScrapDto(userIdx, boardIdx)) == null ? false : true);
			tmp.put("isScrap", sqlSession.getMapper(SNSDao.class).readIsScrap(new FavorScrapDto(userIdx, boardIdx)) == null ? false : true);

			result.add(tmp);
		}

		return result;
	}

	@Override
	public List<HashMap<String, Object>> searchFeed(Map<String, Object> param) {
		List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();

		param.put("searchContent", (param.get("searchContent").toString()).trim());
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
	public List<Map<Object, Object>> readHotKeyword() {
		
		return sqlSession.getMapper(FeedDao.class).readHotKeyword();
	}

	@Override
	public int readFeedPageCnt(int userIdx) {
		int totalCnt = sqlSession.getMapper(FeedDao.class).readFeedPageCnt(userIdx);
		
		return totalCnt % 10 == 0 ? totalCnt / 10 : totalCnt / 10 + 1;
	}

	@Override
	public List<HashMap<String, Object>> readRecommendedArticles(int boardIdx) {
		// 태그 얻어오기
		int userIdx =  sqlSession.getMapper(FeedDao.class).readArticle(boardIdx).getUserIdx();
		List<String> tags = sqlSession.getMapper(FeedDao.class).readFeedTag(boardIdx);
		// 태그에 해당하는 boardIdx 얻어오기
		Map<Integer, Integer> boardIdxFreq = new HashMap<Integer, Integer>();
		for(String tag : tags) {
			List<Integer> boardIdx4Tags = sqlSession.getMapper(FeedDao.class).readBoardIdx4Tags(tag);
			for(int bidx : boardIdx4Tags) {
				if(userIdx ==  sqlSession.getMapper(FeedDao.class).readArticle(bidx).getUserIdx())
					continue;
				
				if(boardIdxFreq.get(bidx) == null)
					boardIdxFreq.put(bidx, 1);
				else
					boardIdxFreq.put(bidx, boardIdxFreq.get(bidx)+1);
			}
		}
		
		if(boardIdxFreq.get(boardIdx) != null)
			boardIdxFreq.remove(boardIdx);
		
		// sort
		List<Map.Entry<Integer,Integer>> entries = new LinkedList<>(boardIdxFreq.entrySet());
		Collections.sort(entries, (o1, o2) -> o2.getValue().compareTo(o1.getValue()));
		List<Integer> sortedBoardIdx = new ArrayList<Integer>();
		for(Map.Entry<Integer, Integer> entry : entries) {
			sortedBoardIdx.add(entry.getKey());
		}
		
		List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
		for(int bidx : sortedBoardIdx) {
			HashMap<String, Object> tmp = new HashMap<String, Object>();
			tmp.put("boardIdx", bidx);
			FeedDto feed = sqlSession.getMapper(FeedDao.class).readArticle(bidx);
			tmp.put("nickname", sqlSession.getMapper(UserDao.class).findUserByUserIdx(feed.getUserIdx()).getNickname());
			tmp.put("tag",  sqlSession.getMapper(FeedDao.class).readFeedTag(bidx));
			
			result.add(tmp);
		}
		
		return result;
	}

}

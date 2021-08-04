package com.tumo.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tumo.model.ArticleDto;
import com.tumo.model.CommentDto;
import com.tumo.model.FeedCommentDto;
import com.tumo.model.FeedDto;
import com.tumo.model.dao.FeedDao;
import com.tumo.model.dao.SNSDao;
import com.tumo.model.dao.UserDao;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private SqlSession sqlSession;

	@Override
	@Transactional
	public boolean createArticle(ArticleDto articleDto) {
		sqlSession.getMapper(FeedDao.class).createArticle(articleDto);

		List<String> tags = articleDto.getTags();
		int boardIdx = findBoardIdxByArticle(articleDto);
		createFeedTags(boardIdx, tags);

		return true;
	}

	@Override
	public int findBoardIdxByArticle(ArticleDto articleDto) {
		return sqlSession.getMapper(FeedDao.class).findBoardIdxByArticle(articleDto);
	}

	@Override
	@Transactional
	public boolean createFeedTags(int boardIdx, List<String> tags) {
		HashMap<String, Object> tagMap;
		for (String tag : tags) {
			tagMap = new HashMap<String, Object>();
			tagMap.put("board_idx", boardIdx);
			tagMap.put("content", tag);

			sqlSession.getMapper(FeedDao.class).createFeedTag(tagMap);
		}
		return true;
	}

	@Override
	@Transactional
	public HashMap<String, Object> readArticle(Map<String, Integer> param) {
		HashMap<String, Object> result = new HashMap<String, Object>();

		FeedDto feed = sqlSession.getMapper(FeedDao.class).readArticle(param.get("boardIdx"));
		if (feed == null)
			return null;

		List<String> feedTag = sqlSession.getMapper(FeedDao.class).readFeedTag(param.get("boardIdx"));
		String nickname = sqlSession.getMapper(UserDao.class).findUserByUserIdx(feed.getUserIdx()).getNickname();
		boolean isLike = sqlSession.getMapper(SNSDao.class).readIsLike(param) == null ? false : true;
		boolean isScrap = sqlSession.getMapper(SNSDao.class).readIsScrap(param) == null ? false : true;

		result.put("boardIdx", feed.getBoardIdx());
		result.put("userIdx", feed.getUserIdx());
		result.put("nickname", nickname);
		result.put("title", feed.getTitle());
		result.put("content", feed.getContent());
		result.put("createAt", feed.getCreateAt());
		result.put("updateAt", feed.getUpdateAt());
		result.put("tags", feedTag);
		result.put("isLike", isLike);
		result.put("isScrap", isScrap);

		return result;
	}

	@Override
	@Transactional
	public boolean updateArticle(Map<String, Object> param) {
		int boardIdx = (int) param.get("boardIdx");

		FeedDto feed = sqlSession.getMapper(FeedDao.class).readArticle(boardIdx);
		if (feed == null)
			return false;

		ArticleDto articleDto = (ArticleDto) param.get("articleDto");
		FeedDto feedDto = new FeedDto(boardIdx, articleDto.getUserIdx(), articleDto.getStock(), articleDto.getTitle(),
				articleDto.getContent());
		sqlSession.getMapper(FeedDao.class).updateArticle(feedDto);
		sqlSession.getMapper(FeedDao.class).deleteFeedTag(boardIdx);
		createFeedTags(boardIdx, articleDto.getTags());

		return true;
	}

	@Override
	@Transactional
	public boolean deleteArticle(int boardIdx) {
		FeedDto feed = sqlSession.getMapper(FeedDao.class).readArticle(boardIdx);
		if (feed == null)
			return false;

		sqlSession.getMapper(FeedDao.class).deleteArticle(boardIdx);
		sqlSession.getMapper(FeedDao.class).deleteFeedTag(boardIdx);
		return true;
	}

	@Override
	@Transactional
	public boolean createComment(CommentDto commentDto) {
		FeedDto feed = sqlSession.getMapper(FeedDao.class).readArticle(commentDto.getBoardIdx());
		if (feed == null)
			return false;

		sqlSession.getMapper(FeedDao.class).createComment(commentDto);

		return true;
	}

	@Override
	@Transactional
	public List<FeedCommentDto> readComment(Map<String, Integer> param) {
		FeedDto feed = sqlSession.getMapper(FeedDao.class).readArticle(param.get("boardIdx"));

		if (feed == null)
			return null;

		int pageNum = param.get("pageNum") * 5;
		param.remove("pageNum");
		param.put("pageNum", pageNum);
		int cnt = sqlSession.getMapper(FeedDao.class).countCommentInFeed(param);

		if (cnt == 0)
			return null;

		return sqlSession.getMapper(FeedDao.class).readComment(param);
	}

	@Override
	public boolean deleteComment(int commentIdx) {
		sqlSession.getMapper(FeedDao.class).deleteComment(commentIdx);
		return true;
	}
}

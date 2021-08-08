package com.tumo.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tumo.model.FavorScrapDto;
import com.tumo.model.FeedDto;
import com.tumo.model.FeedLikeDto;
import com.tumo.model.FollowDto;
import com.tumo.model.NotificationDto;
import com.tumo.model.ProfileDto;
import com.tumo.model.ScrapDto;
import com.tumo.model.dao.FeedDao;
import com.tumo.model.dao.SNSDao;
import com.tumo.model.dao.UserDao;

@Service
public class SNSServiceImpl implements SNSService {

	@Autowired
	private SqlSession sqlSession;

	@Override
	@Transactional
	public boolean createScrap(FavorScrapDto info) {
		if(sqlSession.getMapper(SNSDao.class).readIsScrap(info) != null)
			return false;
		sqlSession.getMapper(SNSDao.class).createScrap(info);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userIdx", sqlSession.getMapper(FeedDao.class).readArticle(info.getBoardIdx()).getUserIdx());
		param.put("otheruserIdx", info.getUserIdx());
		param.put("type", 4);
		param.put("boardIdx", info.getBoardIdx());
		sqlSession.getMapper(SNSDao.class).createNotification(param);
		return true;
	}

	@Override
	public List<ScrapDto> readScrapList(int userIdx) {
		return sqlSession.getMapper(SNSDao.class).readScrapList(userIdx);
	}

	@Override
	@Transactional
	public boolean deleteScrap(FavorScrapDto info) {
		if(sqlSession.getMapper(SNSDao.class).readIsScrap(info) == null)
			return false;
		sqlSession.getMapper(SNSDao.class).deleteScrap(info);
		return true;
	}

	@Override
	@Transactional
	public boolean createFavor(FavorScrapDto info) {
		boolean isLike = readIsLike(info) == null ? false : true;
		if(isLike)
			return false;
		
		sqlSession.getMapper(SNSDao.class).addFavor(info.getBoardIdx());
		sqlSession.getMapper(SNSDao.class).createFavor(info);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userIdx", sqlSession.getMapper(FeedDao.class).readArticle(info.getBoardIdx()).getUserIdx());
		param.put("otheruserIdx", info.getUserIdx());
		param.put("type", 2);
		param.put("boardIdx", info.getBoardIdx());
		sqlSession.getMapper(SNSDao.class).createNotification(param);
		
		return true;
	}

	@Override
	public FeedLikeDto readIsLike(FavorScrapDto param) {
		return sqlSession.getMapper(SNSDao.class).readIsLike(param);
	}

	@Override
	@Transactional
	public boolean deleteFavor(FavorScrapDto info) {
		boolean isLike = readIsLike(info) == null ? false : true;
		if(!isLike)
			return false;
		
		sqlSession.getMapper(SNSDao.class).subFavor(info.getBoardIdx());
		sqlSession.getMapper(SNSDao.class).deleteFavor(info);
		return true;
	}

	@Override
	public List<FeedDto> readMyPost(int userIdx) {
		return sqlSession.getMapper(SNSDao.class).readMyPost(userIdx);
	}

	@Override
	@Transactional
	public List<Map<String, Object>> searchUser(String searchContent, int pageNum) {
		searchContent = searchContent.replaceAll("\\+", " ");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("searchContent", searchContent);
		param.put("pageNum", pageNum * 5);
		int cnt = sqlSession.getMapper(SNSDao.class).countSearchedUser(param);
		System.out.println("searchContent = " + param.get("searchContent"));
		if (cnt == 0)
			return null;

		return sqlSession.getMapper(SNSDao.class).searchUser(param);
	}

	@Override
	@Transactional
	public HashMap<String, Object> readUser(String nickname) {
		int userIdx = sqlSession.getMapper(UserDao.class).findUserByNickname(nickname).getUserIdx();
		ProfileDto profileDto = sqlSession.getMapper(SNSDao.class).readUser(userIdx);
		if (profileDto == null)
			return null;

		
		Integer followingCnt = sqlSession.getMapper(SNSDao.class).getFollowingCount(userIdx);
		Integer followerCnt = sqlSession.getMapper(SNSDao.class).getFollowerCount(userIdx);

		profileDto.setFollowingCnt(followingCnt == null ? 0 : followingCnt);
		profileDto.setFollowerCnt(followerCnt == null ? 0 : followerCnt);

		List<String> tags = sqlSession.getMapper(UserDao.class).findUserTagByUserIdx(userIdx);

		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("userIdx", profileDto.getUserIdx());
		result.put("nickname", profileDto.getNickname());
		result.put("introduce", profileDto.getIntroduce());
		result.put("followingCnt", profileDto.getFollowingCnt());
		result.put("followerCnt", profileDto.getFollowerCnt());
		result.put("disclosure", profileDto.getDisclosure());
		result.put("tags", tags);
		
		return result;
	}

	@Override
	public List<ProfileDto> readFollowerList(int userIdx) {
		return sqlSession.getMapper(SNSDao.class).readFollowerList(userIdx);
	}

	@Override
	public List<ProfileDto> readFollowingList(int userIdx) {
		return sqlSession.getMapper(SNSDao.class).readFollowingList(userIdx);
	}

	@Override
	public Boolean readIsFollow(FollowDto followDto) {
		FollowDto follow = sqlSession.getMapper(SNSDao.class).readIsFollow(followDto);
		
		if (follow == null)
			return false;

		return true;
	}

	@Override
	public void deleteFollowing(FollowDto followDto) {
		sqlSession.getMapper(SNSDao.class).deleteFollowing(followDto);
	}

	@Override
	@Transactional
	public String createFollowRequest(FollowDto followDto) {
		ProfileDto result = sqlSession.getMapper(SNSDao.class).readUser(followDto.getFollowingIdx());
		String disclosure = result.getDisclosure();
		
		if(disclosure.equals("public")) {
			sqlSession.getMapper(SNSDao.class).createFollowing(followDto);
			return "Follow";
		} else {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("userIdx", followDto.getFollowingIdx());
			param.put("otheruserIdx", followDto.getUserIdx());
			param.put("type", 1);
			param.put("boardIdx", -1);
			
			sqlSession.getMapper(SNSDao.class).createNotification(param);
			return "Wait";
		}
	}

	
	@Override
	@Transactional
	public String createFollowing(FollowDto followDto) {
		sqlSession.getMapper(SNSDao.class).createFollowing(followDto);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userIdx", followDto.getFollowingIdx());
		param.put("otheruserIdx", followDto.getUserIdx());
		sqlSession.getMapper(SNSDao.class).deleteNotifiedFollowRequest(param);
		return "Follow";
	}

	@Override
	public void deleteFollowingRequest(Map<String, Object> param) {
		sqlSession.getMapper(SNSDao.class).deleteNotifiedFollowRequest(param);
	}

	@Override
	public List<NotificationDto> readAlarmList(int userIdx) {
		List<NotificationDto> notificationList = sqlSession.getMapper(SNSDao.class).readAlarmList(userIdx);
		
		return notificationList;
	}

	@Override
	public void updateAlarm(int notificationIdx) {
		sqlSession.getMapper(SNSDao.class).updateAlarm(notificationIdx);
	}

}

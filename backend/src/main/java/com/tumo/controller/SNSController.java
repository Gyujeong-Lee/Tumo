package com.tumo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tumo.model.FavorScrapDto;
import com.tumo.model.FeedDto;
import com.tumo.model.FeedLikeDto;
import com.tumo.model.FollowDto;
import com.tumo.model.NotificationDto;
import com.tumo.model.ProfileDto;
import com.tumo.model.ScrapDto;
import com.tumo.model.service.SNSService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/sns")
@Api("SNS 컨트롤러 V1")
@CrossOrigin("*")
public class SNSController {

	private static final Logger logger = LoggerFactory.getLogger(SNSController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private SNSService snsService;

	@ApiOperation(value = "스크랩 생성", notes = "게시글 스크랩")
	@PostMapping("/scrap")
	public ResponseEntity<Map<String, Object>> createScrap(@RequestBody FavorScrapDto info) {
		boolean result = snsService.createScrap(info);
		Map<String, Object> map = new HashMap<String, Object>();
		if (!result)
			map.put("message", FAIL);
		else
			map.put("message", SUCCESS);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	@ApiOperation(value = "스크랩한 게시물 조회", notes = "특정 유저가 스크랩한 게시글 리스트 조회")
	@GetMapping("/scrap/{userIdx}")
	public ResponseEntity<Map<String, Object>> readScrapList(@PathVariable int userIdx) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<ScrapDto> scrap = snsService.readScrapList(userIdx);
		if (scrap == null || scrap.size() == 0) {
			map.put("message", FAIL);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.NO_CONTENT);
		}
		map.put("scrap", scrap);
		map.put("message", SUCCESS);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	@ApiOperation(value = "스크랩한 게시물 삭제", notes = "스크랩한 게시글 스크랩 취소")
	@DeleteMapping("/scrap/{userIdx}/{boardIdx}")
	public ResponseEntity<Map<String, Object>> deleteScrap(@PathVariable int userIdx, @PathVariable int boardIdx) {
		Map<String, Object> result = new HashMap<String, Object>();
		FavorScrapDto param = new FavorScrapDto(userIdx, boardIdx);
		if (snsService.deleteScrap(param))
			result.put("message", SUCCESS);
		else
			result.put("message", FAIL);
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}

	@ApiOperation(value = "좋아요 생성", notes = "게시글 좋아요")
	@PostMapping("/favor")
	public ResponseEntity<Map<String, Object>> createFavor(@RequestBody FavorScrapDto favorDto) {
		boolean result = snsService.createFavor(favorDto);
		Map<String, Object> map = new HashMap<String, Object>();
		if (!result)
			map.put("message", FAIL);
		else
			map.put("message", SUCCESS);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	@ApiOperation(value = "좋아요 삭제", notes = "좋아요한 게시글 좋아요 삭제")
	@DeleteMapping("/favor/{userIdx}/{boardIdx}")
	public ResponseEntity<Map<String, Object>> deleteFavor(@PathVariable int userIdx, @PathVariable int boardIdx) {
		Map<String, Object> map = new HashMap<String, Object>();
		FavorScrapDto param = new FavorScrapDto(userIdx, boardIdx);
		if (!snsService.deleteFavor(param))
			map.put("message", FAIL);
		else
			map.put("message", SUCCESS);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	@ApiOperation(value = "좋아요 상태 확인", notes = "특정 게시물에 대한 사용자의 좋아요 여부 조회")
	@GetMapping("/favor/{userIdx}/{boardIdx}")
	public ResponseEntity<Map<String, Object>> readIsLike(@PathVariable int userIdx, @PathVariable int boardIdx) {
		Map<String, Object> map = new HashMap<String, Object>();

		FavorScrapDto param = new FavorScrapDto(userIdx, boardIdx);
		FeedLikeDto like = snsService.readIsLike(param);
		if (like == null) {
			map.put("isLike", false);
			map.put("message", FAIL);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.NO_CONTENT);
		}
		map.put("isLike", true);
		map.put("message", SUCCESS);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	@ApiOperation(value = "내가 쓴 글 조회")
	@GetMapping("/board/{userIdx}")
	public ResponseEntity<Map<String, Object>> readMyPost(@PathVariable int userIdx) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<FeedDto> scrap = snsService.readMyPost(userIdx);
		if (scrap == null || scrap.size() == 0) {
			map.put("message", FAIL);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.NO_CONTENT);
		}
		map.put("myFeed", scrap);
		map.put("message", SUCCESS);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	@ApiOperation(value = "유저 검색")
	@GetMapping("/search/{searchContent}/{pageNum}")
	public ResponseEntity<Map<String, Object>> searchUser(@PathVariable String searchContent,
			@PathVariable int pageNum) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> users = snsService.searchUser(searchContent, pageNum);
		System.out.println(users + " 잘되니");
		if (users == null || users.size() == 0) {
			result.put("message", FAIL);
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.NO_CONTENT);
		}
		result.put("users", users);
		result.put("message", SUCCESS);
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}

	@ApiOperation(value = "유저 정보 조회", notes = "특정 회원의 닉네임, 한 줄 소개, 팔로잉 수, 팔로워 수")
	@GetMapping("/profile/{nickname}")
	public ResponseEntity<Map<String, Object>> readUserInfo(@PathVariable String nickname) {
		Map<String, Object> result = new HashMap<String, Object>();
		HashMap<String, Object> user = snsService.readUser(nickname);
		if (user == null) {
			result.put("message", FAIL);
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.NO_CONTENT);
		}
		result.put("users", user);
		result.put("message", SUCCESS);
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}

	@ApiOperation(value = "팔로워 리스트")
	@GetMapping("/follower/{userIdx}")
	public ResponseEntity<Map<String, Object>> readFollowerList(@PathVariable int userIdx) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<ProfileDto> followers = snsService.readFollowerList(userIdx);
		result.put("message", SUCCESS);
		result.put("followers", followers);
		if (followers == null || followers.size() == 0) {
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}

	@ApiOperation(value = "팔로잉 리스트")
	@GetMapping("/following/{userIdx}")
	public ResponseEntity<Map<String, Object>> readFollowingList(@PathVariable int userIdx) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<ProfileDto> following = snsService.readFollowingList(userIdx);
		result.put("message", SUCCESS);
		result.put("followers", following);
		if (following == null || following.size() == 0) {
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}

	@ApiOperation(value = "팔로잉 여부", notes = "userIdx가 followingIdx를 팔로잉했는지 조회")
	@GetMapping("/follow/{userIdx}/{followingIdx}")
	public ResponseEntity<Map<String, Object>> readIsFollow(@PathVariable int userIdx, @PathVariable int followingIdx) {
		Map<String, Object> result = new HashMap<String, Object>();
		FollowDto followDto = new FollowDto(userIdx, followingIdx);

		Boolean isFollow = snsService.readIsFollow(followDto);
		result.put("message", SUCCESS);
		result.put("isFollow", isFollow);
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}

	@ApiOperation(value = "팔로우 추가 및 팔로우 요청", notes = "userIdx가 followingIdx를 팔로우. followingIdx가 공개 계정이면 추가(Follow), 비공개 계정이면 요청(Request)")
	@PostMapping("follow")
	public ResponseEntity<Map<String, Object>> createFollowRequest(@RequestBody FollowDto followDto) {
		Map<String, Object> result = new HashMap<String, Object>();
		String response = snsService.createFollowRequest(followDto);
		result.put("message", SUCCESS);
		result.put("result", response);
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}

	@ApiOperation(value = "요청 승인 후 팔로우 추가", notes = "팔로우 요청 승인 시 팔로우 추가, 팔로우 요청 알림 삭제 (followingIdx가 userIdx의 팔로우 요청 승인) ")
	@PostMapping("follow/acception")
	public ResponseEntity<Map<String, Object>> createFollowing(@RequestBody FollowDto followDto) {
		Map<String, Object> result = new HashMap<String, Object>();
		String response = snsService.createFollowing(followDto);
		result.put("message", SUCCESS);
		result.put("result", response);
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}

	@ApiOperation(value = "팔로우 요청 거절", notes = "userIdx가 otheruserIdx를 거절")
	@DeleteMapping("follow/rejection/{userIdx}/{otheruserIdx}")
	public ResponseEntity<Map<String, Object>> deleteFollowingRequest(@PathVariable int userIdx,
			@PathVariable int otheruserIdx) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userIdx", userIdx);
		param.put("otheruserIdx", otheruserIdx);

		snsService.deleteFollowingRequest(param);
		result.put("message", SUCCESS);
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}

	@ApiOperation(value = "팔로우 삭제")
	@DeleteMapping("follow/{userIdx}/{followingIdx}")
	public ResponseEntity<Map<String, Object>> deleteFollowing(@PathVariable int userIdx,
			@PathVariable int followingIdx) {
		Map<String, Object> result = new HashMap<String, Object>();
		FollowDto followDto = new FollowDto(userIdx, followingIdx);
		snsService.deleteFollowing(followDto);
		result.put("message", SUCCESS);
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}

	@ApiOperation(value = "알림 리스트 조회", notes = "응답 type의 경우, 1번 : follow 요청 / 2번 : 좋아요 / 3번 : 댓글 / 4번 : 스크랩")
	@GetMapping("alarm/{userIdx}")
	public ResponseEntity<Map<String, Object>> readAlarmList(@PathVariable int userIdx) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<NotificationDto> notification = snsService.readAlarmList(userIdx);
		result.put("message", SUCCESS);
		result.put("notification", notification);

		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}

	@ApiOperation(value = "알림 읽음 표시")
	@PutMapping("alarm/{notificationIdx}")
	public ResponseEntity<Map<String, Object>> updateAlarmList(@PathVariable int notificationIdx) {
		Map<String, Object> result = new HashMap<String, Object>();
		snsService.updateAlarm(notificationIdx);
		result.put("message", SUCCESS);
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}
}

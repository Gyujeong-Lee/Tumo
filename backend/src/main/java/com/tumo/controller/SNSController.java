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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tumo.model.FeedDto;
import com.tumo.model.FeedLikeDto;
import com.tumo.model.ScrapDto;
import com.tumo.model.UserDto;
import com.tumo.model.dao.ProfileDto;
import com.tumo.model.service.SNSService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/sns")
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
	public ResponseEntity<Map<String, Object>> createScrap(@RequestBody HashMap<String, Integer> info) {
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
	@DeleteMapping("/scrap")
	public ResponseEntity<Map<String, Object>> deleteScrap(@RequestBody HashMap<String, Integer> info) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (snsService.deleteScrap(info)) {
			map.put("message", SUCCESS);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		map.put("message", FAIL);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "좋아요 생성", notes = "게시글 좋아요")
	@PostMapping("/favor")
	public ResponseEntity<Map<String, Object>> createFavor(@RequestBody HashMap<String, Integer> info) {
		boolean result = snsService.createFavor(info);
		Map<String, Object> map = new HashMap<String, Object>();
		if (!result)
			map.put("message", FAIL);
		else
			map.put("message", SUCCESS);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	@ApiOperation(value = "좋아요 삭제", notes = "좋아요한 게시글 좋아요 삭제")
	@DeleteMapping("/favor")
	public ResponseEntity<Map<String, Object>> deleteFavor(@RequestBody HashMap<String, Integer> info) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (snsService.deleteFavor(info)) {
			map.put("message", SUCCESS);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		map.put("message", FAIL);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "좋아요 상태 확인", notes = "특정 게시물에 대한 사용자의 좋아요 여부 조회")
	@GetMapping("/favor/{user_idx}/{board_idx}")
	public ResponseEntity<Map<String, Object>> readIsLike(@PathVariable int userIdx, @PathVariable int boardIdx) {
		Map<String, Object> map = new HashMap<String, Object>();

		Map<String, Integer> param = new HashMap<String, Integer>();
		param.put("userIdx", userIdx);
		param.put("boardIdx", boardIdx);
		FeedLikeDto like = snsService.readIsLike(param);
		if (like == null) {
			map.put("like", false);
			map.put("message", FAIL);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.NO_CONTENT);
		}
		map.put("like", true);
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
		map.put("scrap", scrap);
		map.put("message", SUCCESS);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	@ApiOperation(value = "유저 검색")
	@GetMapping("/search/{searchContent}/{pageNum}")
	public ResponseEntity<Map<String, Object>> searchUser(@PathVariable String searchContent,
			@PathVariable int pageNum) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<UserDto> users = snsService.searchUser(searchContent, pageNum);
		if (users == null || users.size() == 0) {
			result.put("message", FAIL);
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.NO_CONTENT);
		}
		result.put("users", users);
		result.put("message", SUCCESS);
		return new ResponseEntity<Map<String,Object>>(result, HttpStatus.OK);
	}
	
	@ApiOperation(value = "유저 정보 조회", notes = "특정 회원의 닉네임, 한 줄 소개, 팔로잉 수, 팔로워 수")
	@GetMapping("/profile/{userIdx}")
	public ResponseEntity<Map<String, Object>> readUserInfo(@PathVariable int userIdx) {
		Map<String, Object> result = new HashMap<String, Object>();
		ProfileDto user = snsService.readUser(userIdx);
		if (user == null) {
			result.put("message", FAIL);
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.NO_CONTENT);
		}
		result.put("users", user);
		result.put("message", SUCCESS);
		return new ResponseEntity<Map<String,Object>>(result, HttpStatus.OK);
	}
	

}

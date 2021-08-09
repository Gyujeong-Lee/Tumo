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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tumo.model.FeedDto;
import com.tumo.model.service.FeedService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/feed")
@CrossOrigin("*")
public class FeedController {

	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private FeedService feedService;

	@ApiOperation(value = "게시글 리스트 조회")
	@GetMapping("/{userIdx}/{pageNum}")
	public ResponseEntity<Map<String, Object>> readFeed(@PathVariable int userIdx, @PathVariable int pageNum) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userIdx", userIdx);
		param.put("pageNum", pageNum * 10);

		List<HashMap<String, Object>> feedList = feedService.readFeed(param);
		int totalPageCnt = feedService.readFeedPageCnt(userIdx);
		
		if (feedList == null || feedList.size() == 0) {
			result.put("message", FAIL);
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.NO_CONTENT);
		}

		result.put("message", SUCCESS);
		result.put("feedList", feedList);
		result.put("totalPageCnt", totalPageCnt);

		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}

	@ApiOperation(value = "피드 검색")
	@GetMapping("/search/{searchContent}/{pageNum}")
	public ResponseEntity<Map<String, Object>> searchFeed(@PathVariable String searchContent, @PathVariable int pageNum) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("searchContent", searchContent);
		param.put("pageNum", pageNum * 5);
		
		List<HashMap<String, Object>> feedList = feedService.searchFeed(param);
		result.put("feedList", feedList);
		result.put("message", SUCCESS);
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}

	@ApiOperation(value = "인기 키워드")
	@GetMapping("/hotkeyword")
	public ResponseEntity<Map<String, Object>> readHotkey() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<Object, Object>> hotkeyList = feedService.readHotKeyword();
		if (hotkeyList == null || hotkeyList.size() == 0) {
			map.put("message", FAIL);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.NO_CONTENT);
		}
		map.put("hotkeyList", hotkeyList);
		map.put("message", SUCCESS);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
}

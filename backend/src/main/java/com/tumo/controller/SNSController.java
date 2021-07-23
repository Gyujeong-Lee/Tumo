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

import com.tumo.model.ScrapDto;
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
	public ResponseEntity<Map<String, Object>> registScrap(@RequestBody HashMap<String, Integer> info) {
		boolean result = snsService.registScrap(info);
		Map<String, Object> map = new HashMap<String, Object>();
		if (!result)
			map.put("message", FAIL);
		else
			map.put("message", SUCCESS);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	@ApiOperation(value = "스크랩한 게시물 조회", notes = "특정 유저가 스크랩한 게시글 리스트 조회")
	@GetMapping("/scrap/{userId}")
	public ResponseEntity<Map<String, Object>> showScrapList(@PathVariable int userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<ScrapDto> scrap = snsService.showScrapList(userId);
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

}

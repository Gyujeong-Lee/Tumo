package com.tumo.controller;

import java.util.HashMap;
import java.util.Map;

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

import com.tumo.model.ArticleDto;
import com.tumo.model.FeedDto;
import com.tumo.model.FeedLikeDto;
import com.tumo.model.service.ArticleService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/article")
@CrossOrigin("*")
public class ArticleController {

	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private ArticleService articleService;

	@ApiOperation(value = "게시글 생성")
	@PostMapping("/")
	public ResponseEntity<Map<String, Object>> createArticle(@RequestBody ArticleDto articleDto) {
		boolean result = articleService.createArticle(articleDto);
		Map<String, Object> map = new HashMap<String, Object>();
		if (!result)
			map.put("message", FAIL);
		else
			map.put("message", SUCCESS);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	@ApiOperation(value = "게시글 상세내용 조회", notes = "조회할 게시글의 boardIdx, 조회하는 유저의 userIdx 입력")
	@GetMapping("/{boardIdx}/{userIdx}")
	public ResponseEntity<Map<String, Object>> readArticle(@PathVariable int boardIdx, @PathVariable int userIdx) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Integer> param = new HashMap<String, Integer>();
		param.put("boardIdx", boardIdx);
		param.put("userIdx", userIdx);
		HashMap<String, Object> feed = articleService.readArticle(param);

		if (feed == null) {
			map.put("message", FAIL);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.NO_CONTENT);
		}

		map.put("message", SUCCESS);
		map.put("feed", feed);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	@ApiOperation(value = "게시글 수정")
	@PutMapping("/{boardIdx}")
	public ResponseEntity<Map<String, Object>> updateArticle(@PathVariable int boardIdx, @RequestBody ArticleDto articleDto) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("articleDto", articleDto);
		param.put("boardIdx", boardIdx);
		
		boolean isSuccess = articleService.updateArticle(param);
		Map<String, Object> result = new HashMap<String, Object>();
		if (!isSuccess) {
			result.put("message", FAIL);
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.NO_CONTENT);
		} else
			result.put("message", SUCCESS);

		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}

	@ApiOperation(value = "게시글 삭제")
	@DeleteMapping("/{boardIdx}")
	public ResponseEntity<Map<String, Object>> deleteArticle(@PathVariable int boardIdx) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (articleService.deleteArticle(boardIdx)) {
			map.put("message", FAIL);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.NO_CONTENT);
		} else
			map.put("message", SUCCESS);

		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

}

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

import com.tumo.model.FeedDto;
import com.tumo.model.service.ArticleService;
import com.tumo.model.service.FeedService;

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
	@PostMapping("/article")
	public ResponseEntity<Map<String, Object>> createArticle(@RequestBody FeedDto newArticle) {
		boolean result = articleService.createArticle(newArticle);
		Map<String, Object> map = new HashMap<String, Object>();
		if (!result)
			map.put("message", FAIL);
		else
			map.put("message", SUCCESS);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	    }
	
	@ApiOperation(value = "게시글 상세내용 조회")
	@GetMapping("/article/{board_idx}")
	public ResponseEntity<Map<String, Object>> readArticle(@PathVariable int boardIdx) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (articleService.readArticle(boardIdx)) {
			map.put("message", FAIL);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.NO_CONTENT);
		}
		else 
			map.put("message", SUCCESS);
		
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	@ApiOperation(value = "게시글 수정")
	@PutMapping("/article/{board_idx}")
	public ResponseEntity<Map<String, Object>> updateArticle(@RequestBody FeedDto updateArticle) {
		boolean result = articleService.updateArticle(updateArticle);
		Map<String, Object> map = new HashMap<String, Object>();
		if (!result) {
			map.put("message", FAIL);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.NO_CONTENT);
		}
		else 
			map.put("message", SUCCESS);
		
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	@ApiOperation(value = "게시글 삭제")
	@DeleteMapping("/article/{board_idx}")
	public ResponseEntity<Map<String, Object>> deleteArticle(@PathVariable int boardIdx) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (articleService.deleteArticle(boardIdx)) {
			map.put("message", FAIL);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.NO_CONTENT);
		}
		else 
			map.put("message", SUCCESS);
		
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	

}

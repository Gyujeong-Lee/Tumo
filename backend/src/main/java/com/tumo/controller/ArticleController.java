package com.tumo.controller;

import java.util.HashMap;
import java.util.List;
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
import com.tumo.model.CommentDto;
import com.tumo.model.FeedCommentDto;
import com.tumo.model.service.ArticleService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/article")
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
	public ResponseEntity<Map<String, Object>> updateArticle(@PathVariable int boardIdx,
			@RequestBody ArticleDto articleDto) {
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
		if (!articleService.deleteArticle(boardIdx)) {
			map.put("message", FAIL);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.NO_CONTENT);
		} else
			map.put("message", SUCCESS);

		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	@ApiOperation(value = "댓글 작성")
	@PostMapping("/comment")
	public ResponseEntity<Map<String, Object>> createComment(@RequestBody CommentDto commentDto) {
		boolean result = articleService.createComment(commentDto);
		Map<String, Object> map = new HashMap<String, Object>();
		if (!result)
			map.put("message", FAIL);
		else
			map.put("message", SUCCESS);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	@ApiOperation(value = "댓글 리스트 조회", notes = "게시글 boardIdx에 해당하는 댓글 5개씩 조회(pageNum은 0부터 시작)")
	@GetMapping("/comment/{boardIdx}/{pageNum}")
	public ResponseEntity<Map<String, Object>> readComment(@PathVariable int boardIdx, @PathVariable int pageNum) {
		Map<String, Integer> param = new HashMap<String, Integer>();
		param.put("boardIdx", boardIdx);
		param.put("pageNum", pageNum);
		List<FeedCommentDto> comments = articleService.readComment(param);
		int totalPageCnt = articleService.readCommentPageCnt(boardIdx);
		
		Map<String, Object> result = new HashMap<String, Object>();
		if (comments == null || comments.size() == 0) {
			result.put("message", FAIL);
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.NO_CONTENT);
		}
		result.put("message", SUCCESS);
		result.put("commentList", comments);
		result.put("totalPageCnt", totalPageCnt);
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}

	@ApiOperation(value = "댓글 삭제")
	@DeleteMapping("/comment/{commentIdx}")
	public ResponseEntity<Map<String, Object>> deleteCommentIdx(@PathVariable int commentIdx) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (!articleService.deleteComment(commentIdx)) {
			map.put("message", FAIL);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.NO_CONTENT);
		}
		map.put("message", SUCCESS);

		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

}

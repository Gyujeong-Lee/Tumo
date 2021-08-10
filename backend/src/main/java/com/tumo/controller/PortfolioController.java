package com.tumo.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.common.io.ByteStreams;
import com.tumo.model.AssetDto;
import com.tumo.model.PortfolioDto;
import com.tumo.model.service.PortfolioService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/portfolio")
@CrossOrigin("*")
public class PortfolioController {

	@Autowired 
	PortfolioService portfolioService;
	
	@Transactional
	@PostMapping(value ="/list")
	@ApiOperation(value = "포트폴리오 생성")
	public ResponseEntity createListt(@RequestBody Map<String, Object> portfolioMap){
		
		ResponseEntity response = null;
		Map<String, Object> resultMap = new HashMap<>();
		List<Map> asset =(List<Map>) portfolioMap.get("assets");	
		PortfolioDto portfoliodto = new PortfolioDto((int)(portfolioMap.get("userIdx")),(String)portfolioMap.get("title") ,
				(String)portfolioMap.get("content"),Double.parseDouble(String.valueOf(portfolioMap.get("goal"))));
		boolean result=portfolioService.createList(portfoliodto);
		if(result) {
			int portfolioIdx=portfolioService.recentPortfolio(portfoliodto.getUserIdx());
			for (int i = 0; i < asset.size(); i++) {
				AssetDto assetdto= new AssetDto();
				assetdto.setPortfolioIdx(portfolioIdx);
				assetdto.setGoal(Double.parseDouble(String.valueOf(asset.get(i).get("goal"))) );
				assetdto.setStock_code((String) asset.get(i).get("stock_code"));
				assetdto.setPrice((int) asset.get(i).get("price"));
				assetdto.setQuantity((int)asset.get(i).get("quantity"));
				portfolioService.createAsset(assetdto);
			}
			resultMap.put("message", "success");
		response = new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.CREATED);
		}else {
			resultMap.put("message", "fail");
			response = new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
//	@PostMapping(value ="/list")
//	@ApiOperation(value = "포트폴리오 생성")
//	public ResponseEntity createList(@RequestBody PortfolioDto portfoliodto ){
//		
//		ResponseEntity response = null;
//		Map<String, Object> resultMap = new HashMap<>();
//		boolean result=portfolioService.createList(portfoliodto);
//		if(result) {resultMap.put("message", "success");
//		response = new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.CREATED);
//		}else {
//			resultMap.put("message", "fail");
//			response = new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//		return response;
//	}
	
	
	@PutMapping(value ="/list")
	@ApiOperation(value = "포트폴리오 수정")
	public ResponseEntity createList(@RequestBody PortfolioDto portfoliodto ){
		
		ResponseEntity response = null;
		System.out.println(1234);
		Map<String, Object> resultMap = new HashMap<>();
		boolean result=portfolioService.updateList(portfoliodto);
		if(result) {
			resultMap.put("message", "success");
		response = new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.CREATED);
		}else {
			resultMap.put("message", "fail");
			response = new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	
	@GetMapping(value ="/list/{userIdx}")
	@ApiOperation(value = "포트폴리오 리스트")
	public ResponseEntity readList(@PathVariable int userIdx){
		ResponseEntity response = null;
		Map<String, Object> resultMap = new HashMap<>();
		List<Map<Object, Object>> portfolioList=portfolioService.readList(userIdx);
		if (portfolioList == null || portfolioList.size() == 0) {
			resultMap.put("message", "fail");
			return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.NO_CONTENT);
		}
		for (int i = 0; i < portfolioList.size(); i++) {
			List<Map<Object,Object>> assetList=portfolioService.readAsset(Integer.parseInt(portfolioList.get(i).get("portfolio_idx").toString()));
			assetList=portfolioService.calcAsset(assetList);
			
			int sum=portfolioService.sumAsset(assetList);
			int cursum=portfolioService.sumCurAsset(assetList);
			double percent=0;
			double ssum=sum;
			if(sum!=0 && cursum!=0) {
				percent=((cursum*100-sum*100)/ssum);
			}
			String result=String.format("%.2f",percent);
			Map<Object,Object> amount=new HashMap<Object, Object>();
			portfolioList.get(i).put("sum", sum);
			portfolioList.get(i).put("cursum", cursum);
			portfolioList.get(i).put("percent", Double.parseDouble(result));
		}
		resultMap.put("portfolio", portfolioList);
		resultMap.put("message", "success");
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
		
	}
	
	@DeleteMapping(value ="/list/{portfolioIdx}")
	@ApiOperation(value = "포트폴리오 리스트 삭제")
	public ResponseEntity deleteList(@PathVariable int portfolioIdx){
		ResponseEntity response = null;
		Map<String, Object> resultMap = new HashMap<>();
		boolean result=portfolioService.deleteList(portfolioIdx);
		if(result) {resultMap.put("message", "success");
		response = new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.CREATED);
		}else {
			resultMap.put("message", "fail");
			response = new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
		
	}
	
	@PostMapping(value ="/asset")
	@ApiOperation(value = "개별자산 생성")
	public ResponseEntity createAsset(@RequestBody AssetDto assetDto){
		
		ResponseEntity response = null;
		Map<String, Object> resultMap = new HashMap<>();
		boolean result=portfolioService.createAsset(assetDto);
		if(result) {resultMap.put("message", "success");
		response = new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.CREATED);
		}else {
			resultMap.put("message", "fail");
			response = new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	@GetMapping(value ="/asset/{portfolioIdx}")
	@ApiOperation(value = "개별자산 조회")
	public ResponseEntity readAsset(@PathVariable int portfolioIdx){
		
		ResponseEntity response = null;
		Map<String, Object> resultMap = new HashMap<>();
		List<Map<Object,Object>> assetList=portfolioService.readAsset(portfolioIdx);
		assetList=portfolioService.calcAsset(assetList);
		int sum=portfolioService.sumAsset(assetList);
		int cursum=portfolioService.sumCurAsset(assetList);
		double percent=0;
		double ssum=sum;
		if(sum!=0 && cursum!=0) {
			percent=((cursum*100-sum*100)/ssum);
		}
		String result=String.format("%.2f",percent);
		Map<Object,Object> amount=new HashMap<Object, Object>();
		amount.put("sum", sum);
		amount.put("cursum", cursum);
		amount.put("percent", Double.parseDouble(result));
		if(assetList==null || assetList.size()==0) {
			resultMap.put("message", "fail");
			response = new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.NO_CONTENT);
		}else {
			resultMap.put("Asset", assetList);
			resultMap.put("amount", amount);
			resultMap.put("message", "success");
			response = new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
			
		}
		return response;
	}
	
	@PutMapping(value ="/asset")
	@ApiOperation(value = "개별자산 수정")
	public ResponseEntity updateAsset(@RequestBody AssetDto assetDto){
		ResponseEntity response = null;
		Map<String, Object> resultMap = new HashMap<>();
		boolean result=portfolioService.updateAsset(assetDto);
		if(result) {resultMap.put("message", "success");
		response = new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
		}else {
			resultMap.put("message", "fail");
			response = new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
		
	}
	
	
	@DeleteMapping(value ="/asset/{assetIdx}")
	@ApiOperation(value = "개별자산 삭제")
	public ResponseEntity deleteAsset(@PathVariable int assetIdx){
		ResponseEntity response = null;
		Map<String, Object> resultMap = new HashMap<>();
		boolean result=portfolioService.deleteAsset(assetIdx);
		if(result) {resultMap.put("message", "success");
		response = new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
		}else {
			resultMap.put("message", "fail");
			response = new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
		
	}
	
	@GetMapping(value ="/feedlist/{userIdx}/{pageNum}")
	@ApiOperation(value = "포트폴리오 피드리스트")
	public ResponseEntity feedList(@PathVariable("userIdx") int userIdx, @PathVariable("pageNum") int pageNum){
		ResponseEntity response = null;
		Map<String, Object> resultMap = new HashMap<>();
		List<Map<Object, Object>> portfolioList=portfolioService.readFeedList(userIdx,pageNum);
		
		
		
		if (portfolioList == null || portfolioList.size() == 0) {
			resultMap.put("message", "fail");
			return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.NO_CONTENT);
		}
		for (int i = 0; i < portfolioList.size(); i++) {
			List<Map<Object,Object>> assetList=portfolioService.readAsset(Integer.parseInt(portfolioList.get(i).get("portfolio_idx").toString()));
			assetList=portfolioService.calcAsset(assetList);
			
			int sum=portfolioService.sumAsset(assetList);
			int cursum=portfolioService.sumCurAsset(assetList);
			double percent=0;
			double ssum=sum;
			if(sum!=0 && cursum!=0) {
				percent=((cursum*100-sum*100)/ssum);
			}	
			String result=String.format("%.2f",percent);
			Map<Object,Object> amount=new HashMap<Object, Object>();
			portfolioList.get(i).put("sum", sum);
			portfolioList.get(i).put("cursum", cursum);
			portfolioList.get(i).put("percent", Double.parseDouble(result));
		}	
		resultMap.put("portfolio", portfolioList);
		resultMap.put("message", "success");
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
		
	}
	
	@GetMapping(value ="/search/{searchContent}/{pageNum}")
	@ApiOperation(value = "포트폴리오 검색")
	public ResponseEntity searchPortfolio(@PathVariable("searchContent") String searchContent, @PathVariable("pageNum") String pageNum){
		ResponseEntity response = null;
		Map<String, Object> resultMap = new HashMap<>();
		List<Map<Object, Object>> portfolioList=portfolioService.searchPortfolio(searchContent,pageNum);
		if (portfolioList == null || portfolioList.size() == 0) {
			resultMap.put("message", "fail");
			return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.NO_CONTENT);
		}
		for (int i = 0; i < portfolioList.size(); i++) {
			List<Map<Object,Object>> assetList=portfolioService.readAsset(Integer.parseInt(portfolioList.get(i).get("portfolioIdx").toString()));
			assetList=portfolioService.calcAsset(assetList);
			int sum=portfolioService.sumAsset(assetList);
			int cursum=portfolioService.sumCurAsset(assetList);
			double percent=0;
			double ssum=sum;
			if(sum!=0 && cursum!=0) {
				percent=((cursum*100-sum*100)/ssum);
			}
			String result=String.format("%.2f",percent);
			Map<Object,Object> amount=new HashMap<Object, Object>();
			amount.put("sum", sum);
			amount.put("cursum", cursum);
			amount.put("percent", Double.parseDouble(result));
			portfolioList.get(i).put("amount", amount);
		}	
		resultMap.put("portfolio", portfolioList);
		resultMap.put("message", "success");
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
		
		
		
	}
	
	
	
}

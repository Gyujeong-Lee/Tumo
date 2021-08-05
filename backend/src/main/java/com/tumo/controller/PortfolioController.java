package com.tumo.controller;

import java.sql.SQLException;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
	
	@PostMapping(value ="/list")
	@ApiOperation(value = "포트폴리오 생성")
	public ResponseEntity createList(@RequestBody PortfolioDto portfoliodto ){
		
		ResponseEntity response = null;
		Map<String, Object> resultMap = new HashMap<>();
		boolean result=portfolioService.createList(portfoliodto);
		if(result) {resultMap.put("message", "success");
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
		List<PortfolioDto> portfolioList=portfolioService.readList(userIdx);
		if (portfolioList == null || portfolioList.size() == 0) {
			resultMap.put("message", "fail");
			return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.NO_CONTENT);
		}
		resultMap.put("portfolio", portfolioList);
		resultMap.put("message", "success");
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
		
	}
	
	@DeleteMapping(value ="/list/{portfolioIdx}")
	@ApiOperation(value = "포트폴리오 리스트")
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
		double percent=((cursum*100-sum*100)/sum);
		String result=String.format("%.2f",percent);
		Map<Object,Object> amount=new HashMap<Object, Object>();
		amount.put("sum", sum);
		amount.put("cursum", cursum);
		amount.put("percent", Double.parseDouble(result));
		if(assetList==null || assetList.size()==0) {
			resultMap.put("message", "fail");
			response = new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}else {
			resultMap.put("Asset", assetList);
			resultMap.put("amount", amount);
			resultMap.put("message", "success");
			response = new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.CREATED);
			
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
		response = new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.CREATED);
		}else {
			resultMap.put("message", "fail");
			response = new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
		
	}
}

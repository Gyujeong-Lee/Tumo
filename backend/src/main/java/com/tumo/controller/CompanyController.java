package com.tumo.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.ByteStreams;
import com.tumo.model.service.CompanyService;


import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/company")
@CrossOrigin("*")
public class CompanyController {

	
		@Autowired 
		CompanyService companyService;
		
		@GetMapping(value ="/search/{searchContent}")
		@ApiOperation(value = "국내회사 검색")
		public List<Map<Object,Object>> searchCompany(@PathVariable("searchContent") String searchContent){
			
			return companyService.searchCompany(searchContent);
			
		}
		
		@GetMapping(value ="/searchforeign/{searchContent}")
		@ApiOperation(value = "해외회사 검색")
		public List<Map<Object,Object>> searchForeign(@PathVariable("searchContent") String searchContent){
			//System.out.println(searchContent);
			return companyService.searchCompanyForeign(searchContent);
			
		}
		
		
		@GetMapping(value ="/detailList/{userIdx}")
		@ApiOperation(value = "국내회사 검색")
		public List<Map<Object,Object>> detailList(@PathVariable("userIdx") String userIdx){
			
			return companyService.detailList(userIdx);
			
		}
		
		
		
		
		
		@GetMapping(value ="/detail/{corp_name}")
		@ApiOperation(value = "국내회사디테일")
		public ResponseEntity companyDetail(@PathVariable("corp_name") String corp_name){
	    	
			String corp_code=companyService.readCorp(corp_name);
			while(corp_code.length()!=8) {
				corp_code="0"+corp_code;
			}
			Map<String, Object> resultMap=companyService.corpDetail(corp_code,corp_name);
			if(resultMap.size()==0||resultMap==null) {
				resultMap.put("message", "fail");
				return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.NO_CONTENT);
			}else {
				resultMap.put("message", "success");
				return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
			}
			
		
			
		}
		
		
		
		@GetMapping(value ="/report/{companyName}")
		@ApiOperation(value = "국내회사리포트")
		public ResponseEntity corpReport(@PathVariable("companyName") String corp_name){
	    	
			String corp_code=companyService.readCorp(corp_name);
			while(corp_code.length()!=8) {
				corp_code="0"+corp_code;
			}
			List<Map<String, Object>> result=companyService.corpReport(corp_code);
			
			Map<String, Object> resultMap= new HashMap<String, Object>();
			System.out.println(result);
			if(result.size()==0||result==null) {
				resultMap.put("message", "fail");
				return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.NO_CONTENT);
			}else {
				resultMap.put("list",result);
				resultMap.put("message", "success");
				return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
			}
			
		
			
		}
		@GetMapping(value ="/news/{corp_name}")
		@ApiOperation(value = "국내회사 뉴스")
		public ResponseEntity news(@PathVariable("corp_name") String corp_name){
			
			
			 String stockcode=companyService.readStock(corp_name);
			 List<Map<String, Object>> result =companyService.CompanyNews(stockcode);
			 
			 Map<String, Object> resultMap= new HashMap<String, Object>();
			 resultMap.put("news", result);
			if(resultMap.size()==0||resultMap==null) {
				resultMap.put("message", "fail");
				return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.NO_CONTENT);
			}else {
				resultMap.put("message", "success");
				return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
			}
				
			
		}
		
//		@GetMapping(value ="/test")
//		@ApiOperation(value = "api 파일 생성")
//		public ResponseEntity test(){
//			
//			//ResponseEntity response = null;
//			 RestTemplate restTemplate = new RestTemplate();
//			    UriComponents uriComponents = UriComponentsBuilder
//			            .fromHttpUrl("https://opendart.fss.or.kr/api")
//			            .pathSegment("corpCode.xml")
//			            .queryParam("crtfc_key", "32b1e829ffb13df1927c85ec2936092a63b22fdf")
//			            .build();
//
//			    
//			    Path file = restTemplate.execute(uriComponents.toUriString(), HttpMethod.GET, null, response -> {
//
//			        Path zipFile = Files.createTempFile("opendart-", ".zip");
//			        System.out.println(response.getBody());
//			        Files.write(zipFile,  ByteStreams.toByteArray(response.getBody()) );
//
//			        return zipFile;
//			    });
//			    System.out.println(file.toAbsolutePath());
//			    // Then
//			    System.out.println(file.getRoot());
//			    System.out.println(file.getFileName());
//			    assertNotNull(file);
//			    assertEquals("opendart", file.getFileName().toString().substring(0, 8));
//			return null;
//		}
		
		
		
		
		
}

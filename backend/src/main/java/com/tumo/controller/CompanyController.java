package com.tumo.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		
		
		
		
		
		@GetMapping(value ="/detail/{stock_code}")
		@ApiOperation(value = "국내회사디테일")
		public ResponseEntity companyDetail(@PathVariable("stock_code") String stock_code){
			
		
			while(stock_code.length()!=6) {
				stock_code="0"+stock_code;
			}
			
	    	String key = "32b1e829ffb13df1927c85ec2936092a63b22fdf";
	    	String result = "";
			String corp_code="";
			result=companyService.readCorp(stock_code);
			
			ResponseEntity response = null;
			
			try {

	    		URL url = new URL("	https://opendart.fss.or.kr/api/fnlttSinglAcnt.json?crtfc_key="
	    				+ key + "&corp_code="+corp_code);

	    		BufferedReader bf;

	    		bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));

	    		result = bf.readLine();
	        	

	    	}catch(Exception e) {
	    		e.printStackTrace();
	    	}
			Map<String, Object> resultMap = new HashMap<String, Object>();
			if(result.equals("")||result==null) {
				resultMap.put("message", "fail");
				return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.NO_CONTENT);
			}else {
				resultMap.put("result", result);
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

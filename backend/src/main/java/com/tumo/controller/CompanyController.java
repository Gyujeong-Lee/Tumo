package com.tumo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tumo.model.service.CompanyService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/company")
@CrossOrigin("*")
public class CompanyController {

	
		@Autowired 
		CompanyService companyService;
		
		@GetMapping(value ="/search/{searchContent}/{pageNum}")
		@ApiOperation(value = "국내회사 검색")
		public List<Map<Object,Object>> searchCompany(@PathVariable("searchContent") String searchContent, @PathVariable("pageNum") String pageNum){
			
			return companyService.searchCompany(searchContent,pageNum);
			
		}
		
		@GetMapping(value ="/searchforeign/{searchContent}/{pageNum}")
		@ApiOperation(value = "해외회사 검색")
		public List<Map<Object,Object>> searchForeign(@PathVariable("searchContent") String searchContent, @PathVariable("pageNum") int pageNum){
			//System.out.println(searchContent);
			return companyService.searchCompanyForeign(searchContent,pageNum);
			
		}
}

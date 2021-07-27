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

@RestController
@RequestMapping("/company")
@CrossOrigin("*")
public class CompanyController {

	
		@Autowired 
		CompanyService companyService;
		
		@GetMapping(value ="/search/{searchContent}/{pageNum}")
		public List<Map<Object,Object>> searchCompany(@PathVariable("searchContent") String searchContent, @PathVariable("pageNum") String pageNum){
			
			return companyService.searchCompany(searchContent,pageNum);
			
		}
}

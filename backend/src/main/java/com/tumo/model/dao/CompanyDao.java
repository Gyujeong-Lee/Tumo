package com.tumo.model.dao;

import java.util.List;
import java.util.Map;

public interface CompanyDao {
	public List<Map<Object,Object>> searchCompany(String keyword,String pageNum);
	
 }

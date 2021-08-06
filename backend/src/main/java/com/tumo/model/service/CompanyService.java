package com.tumo.model.service;

import java.util.List;
import java.util.Map;


public interface CompanyService {
	public List<Map<Object,Object>> searchCompany(String searchContent,String pageNum);

	public List<Map<Object, Object>> searchCompanyForeign(String searchContent, int pageNum);
}

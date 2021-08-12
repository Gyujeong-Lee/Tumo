package com.tumo.model.service;

import java.util.List;
import java.util.Map;


public interface CompanyService {
	public List<Map<Object,Object>> searchCompany(String searchContent);

	public List<Map<Object, Object>> searchCompanyForeign(String searchContent);
	
	public List<Map<String, Object>> CompanyNews(String code);

	public String readCorp(String corp_name);

	public List<Map<Object, Object>> detailList(String userIdx);

	public Map<String, Object> corpDetail(String corp_code, String corp_name);

	public String readStock(String corp_name);

	public List<Map<String, Object>> corpReport(String corp_code);
}

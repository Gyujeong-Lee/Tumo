package com.tumo.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CompanyDao {



	public List<Map<Object, Object>> searchCompanyForeign(HashMap<String, Object> tmp);
	public List<Map<Object, Object>> searchCompany(HashMap<String, Object> tmp);
	public String readCorp(String corp_name);
	public String readStock(String corp_name);
	
 }

package com.tumo.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tumo.model.dao.CompanyDao;

@Service
public class CompanyServiceImpl implements CompanyService {
	@Autowired 
	private SqlSession sqlSession;
	
	
	@Override
	public List<Map<Object, Object>> searchCompany(String searchContent,String pageNum) {
				//0시작
				int temp=Integer.parseInt(pageNum);
				pageNum=Integer.toString(temp*5);
				HashMap<String, Object> tmp = new HashMap<String, Object>();
				tmp.put("pageNum", pageNum);
				tmp.put("searchContent", searchContent);
		return sqlSession.getMapper(CompanyDao.class).searchCompany(tmp);
	}


	@Override
	public List<Map<Object, Object>> searchCompanyForeign( String searchContent, int pageNum) {
			
		HashMap<String, Object> tmp = new HashMap<String, Object>();
		
		tmp.put("pageNum", pageNum);
		tmp.put("searchContent", searchContent);
		return sqlSession.getMapper(CompanyDao.class).searchCompanyForeign(tmp);
	}

}

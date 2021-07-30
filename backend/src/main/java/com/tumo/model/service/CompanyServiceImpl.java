package com.tumo.model.service;

import java.util.List;
import java.util.Map;

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
				
		return sqlSession.getMapper(CompanyDao.class).searchCompany(searchContent,pageNum);
	}

}

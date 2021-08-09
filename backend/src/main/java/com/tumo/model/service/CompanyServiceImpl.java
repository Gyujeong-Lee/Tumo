package com.tumo.model.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tumo.model.dao.CompanyDao;

@Service
public class CompanyServiceImpl implements CompanyService {
	@Autowired 
	private SqlSession sqlSession;
	
	
	@Override
	public List<Map<Object, Object>> searchCompany(String searchContent) {
//				//0시작
//				int temp=Integer.parseInt(pageNum);
//				pageNum=Integer.toString(temp*5);
				HashMap<String, Object> tmp = new HashMap<String, Object>();
//				tmp.put("pageNum", pageNum);
				tmp.put("searchContent", searchContent);
		return sqlSession.getMapper(CompanyDao.class).searchCompany(tmp);
	}


	@Override
	public List<Map<Object, Object>> searchCompanyForeign( String searchContent) {
			
		HashMap<String, Object> tmp = new HashMap<String, Object>();
		
//		tmp.put("pageNum", pageNum);
		tmp.put("searchContent", searchContent);
		return sqlSession.getMapper(CompanyDao.class).searchCompanyForeign(tmp);
	}


	@Override
	public List<Map<Object, Object>> CompanyNews(String code) {
		
		while(code.length()!=6) {
			code="0"+code;
		}
		
		//System.out.println(code);
		String url="https://finance.naver.com/item/news.nhn?code="+code;
		 Document doc;
			try {
				doc = Jsoup.connect(url).get();
				//System.out.println(doc.toString());
				Elements elem = doc.select("dl.blind");
				//System.out.println(elem.select("dd").get(3).text().toString());
				
				StringTokenizer st = new StringTokenizer(elem.select("dd").get(3).text().toString());
				String temp=st.nextToken();
				temp=st.nextToken();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return null;
	}

}

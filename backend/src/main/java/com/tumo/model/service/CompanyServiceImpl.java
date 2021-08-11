package com.tumo.model.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tumo.model.dao.CompanyDao;

@Service
public class CompanyServiceImpl implements CompanyService {
	@Autowired 
	private SqlSession sqlSession;
	
	
	@Override
	public List<Map<Object, Object>> searchCompany(String searchContent) {

				HashMap<String, Object> tmp = new HashMap<String, Object>();
				tmp.put("searchContent", searchContent);
		return sqlSession.getMapper(CompanyDao.class).searchCompany(tmp);
	}


	@Override
	public List<Map<Object, Object>> searchCompanyForeign( String searchContent) {
			
		HashMap<String, Object> tmp = new HashMap<String, Object>();

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
				Elements elem = doc.select("tbody");
				System.out.println(elem.text().toString());
				
				StringTokenizer st = new StringTokenizer(elem.select("dd").get(3).text().toString());
				String temp=st.nextToken();
				temp=st.nextToken();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return null;
	}


	@Override
	public String readCorp(String corp_name) {
		System.out.println(corp_name);
		return sqlSession.getMapper(CompanyDao.class).readCorp(corp_name);
	}


	@Override
	public List<Map<Object, Object>> detailList(String userIdx) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Map<String, Object> corpDetail(String corp_code,String corp_name) {
		String key = "32b1e829ffb13df1927c85ec2936092a63b22fdf";
    	String result = "";
		
		
		try {

    		URL url = new URL("https://opendart.fss.or.kr/api/company.json?crtfc_key="
    				+ key + "&corp_code="+corp_code);

    		BufferedReader bf;

    		bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));

    		result = bf.readLine();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
		System.out.println(result);
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			resultMap = mapper.readValue(result, Map.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resultMap;
	}

}

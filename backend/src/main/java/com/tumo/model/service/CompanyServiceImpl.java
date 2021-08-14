package com.tumo.model.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;

import org.apache.tomcat.util.json.ParseException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
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
	public List<Map<String, Object>> CompanyNews(String code) {
		
		while(code.length()!=6) {
			code="0"+code;
		}
		List<Map<String, Object>> List= new ArrayList<Map<String, Object>>();
		
		System.out.println(code);
		String url="https://finance.naver.com/item/news_news.nhn?code="+code+"&page=&sm=title_entity_id.basic&clusterId=";
		 Document doc;
			try {
				doc = Jsoup.connect(url).get();
				//System.out.println(doc.toString()+1234);
				Elements elem = doc.select("table > tbody > tr:not(.relation_tit)");
				//System.out.println(elem.toString());
				Elements title = elem.select("td.title");
				Elements info = elem.select("td.info");
				Elements date = elem.select("td.date");
				for (int i = 0; i < 5; i++) {
					Map<String, Object> result= new HashMap<String, Object>();
					result.put("title", title.get(i).text());
					result.put("href", title.get(i).select("a[href]").attr("href"));
					result.put("author", info.get(i).text());
					result.put("date", date.get(i).text());
					//System.out.println(title.get(i).select("a[href]").attr("href"));
					List.add(result);
				}
				//System.out.println(elem.toString());
//				StringTokenizer st = new StringTokenizer(elem.select("dd").get(3).text().toString());
//				String temp=st.nextToken();
//				temp=st.nextToken();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return List;
	}

	
	
	
	
	@Override
	public String readCorp(String corp_name) {
		
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


	@Override
	public String readStock(String corp_name) {
		
		return sqlSession.getMapper(CompanyDao.class).readStock(corp_name);
	}


	@Override
	public List<Map<String, Object>> corpReport(String corp_code) {
		String key = "32b1e829ffb13df1927c85ec2936092a63b22fdf";
    	String result = "";
    	System.out.println(corp_code);
    	 Date date = new Date();
         ZoneId timeZone = ZoneId.systemDefault();
         LocalDate getLocalDate = date.toInstant().atZone(timeZone).toLocalDate();
		try {
    		URL url = new URL("https://opendart.fss.or.kr/api/fnlttSinglAcnt.json?crtfc_key="
    				+ key +"&corp_code="+corp_code+ "&bsns_year="+(getLocalDate.getYear()-1)+"&reprt_code=11011");
    		BufferedReader bf;
    		bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
    		result = bf.readLine();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}		
		System.out.println("https://opendart.fss.or.kr/api/fnlttSinglAcnt.json?crtfc_key="
    				+ key +"&corp_code="+corp_code+ "&bsns_year="+(getLocalDate.getYear()-1)+"&reprt_code=11011"	);
		System.out.println(result);
		JSONParser parser = new JSONParser();
		JSONObject obj = null;
			try {
				obj = (JSONObject)parser.parse(result);
			} catch (org.json.simple.parser.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		JSONArray item = (JSONArray)obj.get("list");
		
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		long [] moveList=new long [6];//유동자산,유동부채
		long [] debt=new long [6];//부채총계,자본총계
		long [] roe= new long [6];//당기순이익 ,매출액
		Map<String, Object> tmpmap= new HashMap<String, Object>();
		if(item==null) {
			
		
		for (int i = 0; i < item.size(); i++) {
			JSONObject tmp = (JSONObject) item.get(i);
			
			if(tmp.get("fs_div").toString().equals("OFS")) {
			if(tmp.get("account_nm").toString().equals("매출액")) {
				tmpmap=putmap(tmp,tmpmap,"매출액");
				list.add(tmpmap);
//				tmpmap.put("value", "매출액");
//				tmpmap.put("thisyear", tmp.get("thstrm_amount").toString());
//				tmpmap.put("lastyear", tmp.get("frmtrm_amount").toString());
//				tmpmap.put("twoyear", tmp.get("bfefrmtrm_amount").toString());
				
			}
			
			if(tmp.get("account_nm").toString().equals("영업이익")) {
				tmpmap=putmap(tmp,tmpmap,"영업이익");
				list.add(tmpmap);
				
			}
			if(tmp.get("account_nm").toString().equals("당기순이익")) {
				tmpmap=putmap(tmp,tmpmap,"당기순이익");
				list.add(tmpmap);
				roe[0]=Long.parseLong(tmp.get("thstrm_amount").toString().replace(",","").replace("-","-0").trim());
				roe[1]=Long.parseLong(tmp.get("frmtrm_amount").toString().replace(",","").replace("-","-0").trim());
				roe[2]=Long.parseLong(tmp.get("bfefrmtrm_amount").toString().replace(",","").replace("-","-0").trim());
			}
			if(tmp.get("account_nm").toString().equals("유동자산")) {
				moveList[0]=Long.parseLong(tmp.get("thstrm_amount").toString().replace(",","").replace("-","-0").trim());
				moveList[1]=Long.parseLong(tmp.get("frmtrm_amount").toString().replace(",","").replace("-","-0").trim());
				moveList[2]=Long.parseLong(tmp.get("bfefrmtrm_amount").toString().replace(",","").replace("-","-0").trim());
			}
			if(tmp.get("account_nm").toString().equals("유동부채")) {
				moveList[3]=Long.parseLong(tmp.get("thstrm_amount").toString().replace(",","").replace("-","-0").trim());
				moveList[4]=Long.parseLong(tmp.get("frmtrm_amount").toString().replace(",","").replace("-","-0").trim());
				moveList[5]=Long.parseLong(tmp.get("bfefrmtrm_amount").toString().replace(",","").replace("-","-0").trim());
			}
			
			if(tmp.get("account_nm").toString().equals("부채총계")) {
				debt[0]=Long.parseLong(tmp.get("thstrm_amount").toString().replace(",","").replace("-","-0").trim());
				debt[1]=Long.parseLong(tmp.get("frmtrm_amount").toString().replace(",","").replace("-","-0").trim());
				debt[2]=Long.parseLong(tmp.get("bfefrmtrm_amount").toString().replace(",","").replace("-","-0").trim());
			}
			if(tmp.get("account_nm").toString().equals("자본총계")) {
				debt[3]=Long.parseLong(tmp.get("thstrm_amount").toString().replace(",","").replace("-","-0").trim());
				debt[4]=Long.parseLong(tmp.get("frmtrm_amount").toString().replace(",","").replace("-","-0").trim());
				debt[5]=Long.parseLong(tmp.get("bfefrmtrm_amount").toString().replace(",","").replace("-","-0").trim());
			}
			if(tmp.get("account_nm").toString().equals("매출액")) {
				roe[3]=Long.parseLong(tmp.get("thstrm_amount").toString().replace(",","").replace("-","-0").trim());
				roe[4]=Long.parseLong(tmp.get("frmtrm_amount").toString().replace(",","").replace("-","-0").trim());
				roe[5]=Long.parseLong(tmp.get("bfefrmtrm_amount").toString().replace(",","").replace("-","-0").trim());
			}
			
			
			
			}
			
		}

		tmpmap= new HashMap<String, Object>();
		tmpmap.put("value","유동비율" );
		tmpmap.put("thisyear", String.format("%.2f",moveList[0]*1.0/moveList[3]*1.0*100));
		tmpmap.put("lastyear", String.format("%.2f",moveList[1]*1.0/moveList[4]*1.0*100));
		tmpmap.put("twoyear", String.format("%.2f",moveList[2]*1.0/moveList[5]*1.0*100));
		list.add(tmpmap);
		
		tmpmap= new HashMap<String, Object>();
		tmpmap.put("value","부채비율" );
		tmpmap.put("thisyear", String.format("%.2f",(debt[0]*1.0)/(debt[3]*1.0)*100));
		tmpmap.put("lastyear", String.format("%.2f",debt[1]*1.0/debt[4]*1.0*100));
		tmpmap.put("twoyear", String.format("%.2f",debt[2]*1.0/debt[5]*1.0*100));
		list.add(tmpmap);
		
		tmpmap= new HashMap<String, Object>();
		tmpmap.put("value","매출액순이익률" );
		tmpmap.put("thisyear", String.format("%.2f",roe[0]*1.0/roe[3]*1.0*100));
		tmpmap.put("lastyear", String.format("%.2f",roe[1]*1.0/roe[4]*1.0*100));
		tmpmap.put("twoyear", String.format("%.2f",roe[2]*1.0/roe[5]*1.0*100));
		list.add(tmpmap);
	
		System.out.println(roe[0]);
		System.out.println(roe[3]);
		
		tmpmap= new HashMap<String, Object>();
		tmpmap.put("value","자기자본순이익률(ROE)" );
		tmpmap.put("thisyear", String.format("%.2f",roe[0]*1.0/debt[3]*1.0*100));
		tmpmap.put("lastyear", String.format("%.2f",roe[1]*1.0/debt[4]*1.0*100));
		tmpmap.put("twoyear", String.format("%.2f",roe[2]*1.0/debt[5]*1.0*100));
		list.add(tmpmap);
		}
		return list;
	}

	public  Map<String, Object> putmap(JSONObject tmp ,Map<String, Object> tmpmap,String value){
		tmpmap= new HashMap<String, Object>();
		tmpmap.put("value", value);
		tmpmap.put("thisyear", tmp.get("thstrm_amount").toString());
		tmpmap.put("lastyear", tmp.get("frmtrm_amount").toString());
		tmpmap.put("twoyear", tmp.get("bfefrmtrm_amount").toString());
		
		return tmpmap;
		
	}
	
}

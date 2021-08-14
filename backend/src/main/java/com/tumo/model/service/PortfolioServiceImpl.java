package com.tumo.model.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.ibatis.session.SqlSession;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tumo.model.AssetDto;
import com.tumo.model.PortfolioDto;
import com.tumo.model.dao.CompanyDao;
import com.tumo.model.dao.PortfolioDao;

@Service
public class PortfolioServiceImpl implements PortfolioService {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public boolean createList(PortfolioDto portfoliodto) {
		sqlSession.getMapper(PortfolioDao.class).createList(portfoliodto);
		return true;
	}

	@Override
	public List<Map<Object, Object>> readList(int userIdx) {

		return sqlSession.getMapper(PortfolioDao.class).readList(userIdx);
	}

	@Override
	public boolean deleteList(int portfolioIdx) {
		sqlSession.getMapper(PortfolioDao.class).deleteList(portfolioIdx);
		return true;
	}

	@Override
	public boolean createAsset(AssetDto assetDto) {
		sqlSession.getMapper(PortfolioDao.class).createAsset(assetDto);
		return true;
	}

	@Override
	public List<Map<Object, Object>> readAsset(int portfolioIdx) {
		return sqlSession.getMapper(PortfolioDao.class).readAsset(portfolioIdx);
	}

	@Override
	public List<Map<Object, Object>> calcAsset(List<Map<Object, Object>> assetList) {
		for (int i = 0; i < assetList.size(); i++) {
			String code = (String) assetList.get(i).get("stock_code");
			// System.out.println(code);
			while (code.length() != 6) {
				code = "0" + code;
			}

			// System.out.println(code);
			String url = "https://finance.naver.com/item/main.nhn?code=" + code;

			// System.out.println(url);
			Document doc;
			try {
				doc = Jsoup.connect(url).get();
				// System.out.println(doc.toString());
				Elements elem = doc.select("dl.blind");
				// System.out.println(elem.select("dd").get(3).text().toString());

				StringTokenizer st = new StringTokenizer(elem.select("dd").get(3).text().toString());
				String temp = st.nextToken();
				temp = st.nextToken();
				// System.out.println();
				temp = temp.replace(",", "");
				int curprice = Integer.parseInt(temp);
				// System.out.println(elem.toString());
				assetList.get(i).put("curprice", curprice);
				int price = (int) assetList.get(i).get("price");
				double percent = 0;
				if (price == 0) {

				} else {
					percent = ((curprice * 100 - price * 100) / price);
				}

				String result = String.format("%.2f", percent);
				// System.out.println(result);
				assetList.get(i).put("percent", Double.parseDouble(result));
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return assetList;
	}

	@Override
	public int sumCurAsset(List<Map<Object, Object>> assetList) {
		int temp = 0;
		for (int i = 0; i < assetList.size(); i++) {
			temp += (int) assetList.get(i).get("curprice") * (int) assetList.get(i).get("quantity");
		}
		return temp;
	}

	@Override
	public int sumAsset(List<Map<Object, Object>> assetList) {
		int temp = 0;
		for (int i = 0; i < assetList.size(); i++) {
			temp += (int) assetList.get(i).get("price") * (int) assetList.get(i).get("quantity");
		}
		return temp;
	}

	@Override
	public boolean deleteAsset(int assetIdx) {
		sqlSession.getMapper(PortfolioDao.class).deleteAsset(assetIdx);
		return true;
	}

	@Override
	public List<Map<Object, Object>> readFeedList(int userIdx, int pageNum) {
		HashMap<String, Object> tmp = new HashMap<String, Object>();
		tmp.put("userIdx", userIdx);
		tmp.put("pageNum", pageNum * 5);
		System.out.println(userIdx);
		return sqlSession.getMapper(PortfolioDao.class).readFeedList(tmp);
	}

	@Override
	public List<Map<Object, Object>> searchPortfolio(String searchContent) {
		HashMap<String, Object> tmp = new HashMap<String, Object>();
		tmp.put("searchContent", searchContent);
		return sqlSession.getMapper(PortfolioDao.class).searchPortfolio(tmp);
	}

	@Override
	public int recentPortfolio(int userIdx) {

		return sqlSession.getMapper(PortfolioDao.class).recentPortfolio(userIdx);
	}

	@Override
	public boolean updateRank() {

		sqlSession.getMapper(PortfolioDao.class).updateRank();
		return true;
	}

	@Override
	public boolean updateYield() {
		sqlSession.getMapper(PortfolioDao.class).updateYield();
		return true;
	}

	@Override
	public boolean updateList(PortfolioDto portfoliodto) {

		sqlSession.getMapper(PortfolioDao.class).updateList(portfoliodto);

		return true;
	}

	@Override
	public boolean updateAsset(AssetDto assetDto) {
		sqlSession.getMapper(PortfolioDao.class).updateAsset(assetDto);
		return true;
	}

	@Override
	public int readRank(String nickname) {
		String temp = sqlSession.getMapper(PortfolioDao.class).readRank(nickname);

		if (temp == null) {
			return 0;
		} else {
			return Integer.parseInt(temp);
		}

	}

	@Override
	public List<Map<Object, Object>> readTopPortfolio() {

		return sqlSession.getMapper(PortfolioDao.class).readTopPortfolio();
	}
}

package com.majin.bit.util;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class JockeyCrawling {
	
	public Map<String, Object> javaCrawling(String jkNo, String meet){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Response response = Jsoup.connect("http://race.kra.co.kr/jockey/ProfilePersonItem.do")
								.userAgent("Mozilla/5.0")
								.timeout(10 * 1000)
								.method(Method.POST)
								.data("jkNo", jkNo)
								.data("meet" , meet)
								.data("Act", "09")
								.data("Sub" ,"1")
								.execute();
			
			Document document = response.parse();
			
			map.put("jAllTotal", document.select("#contents > div.tableType1 > table > tbody > tr:nth-child(1) > td:nth-child(2)").text());
			map.put("jYearTotal", document.select("#contents > div.tableType1 > table > tbody > tr:nth-child(2) > td:nth-child(2)").text());
			map.put("jWinningP", document.select("#contents > div.tableType1 > table > tbody > tr:nth-child(1) > td:nth-child(3)").text().substring(4));
			map.put("jComplementRyRate", document.select("#contents > div.tableType1 > table > tbody > tr:nth-child(1) > td:nth-child(4)").text().substring(5));
			map.put("jConsecutiveWinningP",  document.select("#contents > div.tableType1 > table > tbody > tr:nth-child(1) > td:nth-child(5)").text().substring(5));
			map.put("meet", meet);
			
			response = Jsoup.connect("http://race.kra.co.kr/jockey/ProfileContractByeondong.do")
					.userAgent("Mozilla/5.0")
					.timeout(10 * 1000)
					.method(Method.POST)
					.data("Sub" ,"1")
					.data("Act", "09")
					.data("meet" , "1")
					.data("realRcdate", "")
					.data("realRcNo", "")
					.data("jkNo", jkNo)
					.execute();
			document = response.parse();
			
			String trNoParser = document.select("#contents > div.tableType2 > table > tbody > tr:nth-child(1) > td:nth-child(3)").text();

			if (trNoParser.equals("프리기수") || trNoParser.equals("미계약") || trNoParser.equals("") || trNoParser.equals("[부]프리기수") ) {
				map.put("trNo", "프리기수");
			} else {
				map.put("trNo",document.select("#contents > div.tableType2 > table > tbody > tr:nth-child(1) > td:nth-child(3)").toString().substring(60, 66));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

}

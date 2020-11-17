package com.majin.bit.util;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;

public class HorseCrawling {
	
	public Map<String, Object> javaCrawling(String hrNo, String meet){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Response response = Jsoup.connect("http://race.kra.co.kr/racehorse/profileHorseItem.do")
								.userAgent("Mozilla/5.0")
								.timeout(10 * 1000)
								.method(Method.POST)
								.data("rank", "(unable to decode value)")
								.data("age", "")
								.data("HorseName" , "") 
								.data("Sub" ,"1")
								.data("Act", "08")
								.data("meet" , meet)
								.data("arg_OrderBy", "")
								.data("hrNo", hrNo)
								.execute();
			
			Document document = response.parse();
			
			map.put("rating", document.select("#contents > div.tableType1 > table > tbody > tr:nth-child(1) > td:nth-child(4)").text());
			map.put("totalRecords", document.select("#contents > div.tableType1 > table > tbody > tr:nth-child(5) > td:nth-child(2)").text());
			map.put("WinningP", document.select("#contents > div.tableType1 > table > tbody > tr:nth-child(5) > td:nth-child(3)").text().substring(4));
			map.put("ComplementaryRate", document.select("#contents > div.tableType1 > table > tbody > tr:nth-child(5) > td:nth-child(4)").text().substring(5));
			map.put("ConsecutiveWinningP",  document.select("#contents > div.tableType1 > table > tbody > tr:nth-child(5) > td:nth-child(5)").text().substring(5));

								
		} catch (Exception e) {
			System.out.println(e);
		}
		return map;
	}

}

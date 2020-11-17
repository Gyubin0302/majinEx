package com.majin.bit.util;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;

public class TrainerCrawling {
	
	public Map<String, Object> javaCrawling(String trNo, String meet){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Response response = Jsoup.connect("http://race.kra.co.kr/trainer/profileTrainerPersonItem.do")
								.userAgent("Mozilla/5.0")
								.timeout(10 * 1000)
								.method(Method.POST)
								.data("trNo", trNo)
								.data("meet" , meet) 
								.data("Act", "10")
								.data("Sub" ,"1")
								.execute();
			
			Document document = response.parse();
			
			map.put("tAllTotal", document.select("#contents > div.tableType1 > table > tbody > tr:nth-child(1) > td:nth-child(2)").text());
			map.put("tYearTotal", document.select("#contents > div.tableType1 > table > tbody > tr:nth-child(2) > td:nth-child(2)").text());
			map.put("WinningP", document.select("#contents > div.tableType1 > table > tbody > tr:nth-child(1) > td:nth-child(3)").text().substring(4));
			map.put("ComplementaryRate", document.select("#contents > div.tableType1 > table > tbody > tr:nth-child(1) > td:nth-child(4)").text().substring(5));
			map.put("ConsecutiveWinningP",  document.select("#contents > div.tableType1 > table > tbody > tr:nth-child(1) > td:nth-child(5)").text().substring(5));

								
		} catch (Exception e) {
			System.out.println(e);
		}
		return map;
	}

}

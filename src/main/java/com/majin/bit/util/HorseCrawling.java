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
			
			String trParser = document.select("#contents > div.tableType1 > table > tbody > tr:nth-child(2) > td:nth-child(8)").text();
			System.out.println(trParser);
			if ( trParser.equals("휴양마(99)")){
				map.put("trNo", "0AAAAA");
			} else if(trParser.equals("")){
				map.put("trNo", "");
			} else {
				map.put("trNo", document.select("#contents > div.tableType1 > table > tbody > tr:nth-child(2) > td:nth-child(8)").toString().substring(106, 112));
			}
			
			String rankParser = document.select("#contents > div.tableType1 > table > tbody > tr:nth-child(2) > td:nth-child(2)").toString().substring(23,25);
			if(rankParser.equals("미(")) {
				map.put("ranks", "미검마");
			} else if (rankParser.equals("신(")) {
				map.put("ranks", "신");
			} else if (rankParser.equals("(1")) {
				map.put("ranks", "");
			}
			else {
				map.put("ranks", document.select("#contents > div.tableType1 > table > tbody > tr:nth-child(2) > td:nth-child(2)").toString().substring(23,25));
			}
			
			String hrNameParser = document.select("#contents > div.tableType1 > table > tbody > tr:nth-child(1) > td:nth-child(2)").text();
			String[] strArray = hrNameParser.split(" ");
			String hrName = strArray[0];
			map.put("hrName", hrName);
			
			map.put("hrNo", document.select("#contents > div.tableType1 > table > tbody > tr:nth-child(1) > td:nth-child(6)").text());
			map.put("nation", document.select("#contents > div.tableType1 > table > tbody > tr:nth-child(2) > td:nth-child(6)").text());
			map.put("rating", document.select("#contents > div.tableType1 > table > tbody > tr:nth-child(1) > td:nth-child(4)").text());
			map.put("sex", document.select("#contents > div.tableType1 > table > tbody > tr:nth-child(2) > td:nth-child(4)").text());
			map.put("totalRecords", document.select("#contents > div.tableType1 > table > tbody > tr:nth-child(5) > td:nth-child(2)").text());
			map.put("WinningP", document.select("#contents > div.tableType1 > table > tbody > tr:nth-child(5) > td:nth-child(3)").text().substring(4));
			map.put("ComplementaryRate", document.select("#contents > div.tableType1 > table > tbody > tr:nth-child(5) > td:nth-child(4)").text().substring(5));
			map.put("ConsecutiveWinningP",  document.select("#contents > div.tableType1 > table > tbody > tr:nth-child(5) > td:nth-child(5)").text().substring(5));
			map.put("birthDay",  document.select("#contents > div.tableType1 > table > tbody > tr:nth-child(3) > td:nth-child(2)").text());
			map.put("meet", meet);
								
		} catch (Exception e) {
			System.out.println(e);
		}
		return map;
	}

}

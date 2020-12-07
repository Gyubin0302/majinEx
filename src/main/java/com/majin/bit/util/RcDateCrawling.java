package com.majin.bit.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;

public class RcDateCrawling {

	public List<String> rcDateCrawling(String meet) throws IOException {

		Response response = Jsoup.connect("http://race.kra.co.kr/chulmainfo/Riding.do").userAgent("Mozilla/5.0")
							.timeout(10 * 1000)
							.method(Method.GET)
							.data("Sub", "3")
							.data("Act", "02")
							.data("meet", meet)
							.execute();

		Document document = response.parse();

		int period = document.select("#period > option").size();

		List<String> rcDate = new ArrayList<String>();

		if (period > 0) {

			for (int i = 1; i <= period; i++) {
				String index = i + "";
				rcDate.add(document.select("#period > option:nth-child(" + index + ")").val());
			}
			return rcDate;

		} else {
			return null;
		}

	}

}

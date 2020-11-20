package com.majin.bit.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.majin.bit.dto.YundoDto;

public class YundoCrawling {
	public List<YundoDto> yundoCrwaling() {
		String URL = "http://race.kra.co.kr/raceScore/RepresentHorseList.do?Act=05&Sub=4&meet=1";
		Document doc = null;
		try {
			doc = Jsoup.connect(URL).get();
		} catch (IOException e1) {
			System.out.println("web connect error");
			e1.printStackTrace();
		}

		List<String> datas = new ArrayList();
		YundoDto yundo;
		List<YundoDto> yundos = new ArrayList();

		Elements ele = doc.getElementsByTag("table");
		Elements thead = ele.get(0).getElementsByTag("thead");

		String pattern = "[\\d]{6}";
		Pattern p = Pattern.compile(pattern);
		Matcher m;
		// 연도 대표말 제목
		System.out.println(thead.select("th").append("|").text());

		// 연도 대표말 내용
		Elements tbody = ele.get(0).getElementsByTag("tbody");
		for (Element e : tbody.select("tr")) {
			Iterator<Element> ie1 = e.select("td").iterator();
			Element ie2;
			String data;
			yundo = new YundoDto();
			// 여기서 dto 생성 data
			while (ie1.hasNext()) {
				data = "";
				yundo.setMeet("1");
				yundo.setYear(ie1.next().text());

				ie2 = ie1.next();
				m = p.matcher(ie2.toString());
				data += m.find() ? m.group() : 0;
				data += "/";

				ie2 = ie1.next();
				m = p.matcher(ie2.toString());
				data += m.find() ? m.group() : 0;
				data += "/";

				ie2 = ie1.next();
				m = p.matcher(ie2.toString());
				data += m.find() ? m.group() : 0;
				data += "/";

				ie2 = ie1.next();
				m = p.matcher(ie2.toString());
				data += m.find() ? m.group() : 0;
				data += "/";

				ie2 = ie1.next();
				m = p.matcher(ie2.toString());
				data += m.find() ? m.group() : 0;
				data += "/";

				ie2 = ie1.next();
				m = p.matcher(ie2.toString());
				data += m.find() ? m.group() : 0;
				data += "/";

				ie2 = ie1.next();
				m = p.matcher(ie2.toString());
				data += m.find() ? m.group() : 0;

				yundo.setIdSeq(data);
				yundo.setJo(ie1.next().text());
			}
			yundos.add(yundo);
		}

		for (YundoDto yundoo : yundos) {
			System.out.println(yundoo);
		}
		return yundos;
	}
}

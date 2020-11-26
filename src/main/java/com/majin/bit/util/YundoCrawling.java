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
import com.majin.bit.dto.YundoJpaKey;

public class YundoCrawling {
	public List<YundoDto> yundoCrwaling(String meet) {
		String URL = "http://race.kra.co.kr/raceScore/RepresentHorseList.do?";
		if (meet.equals("1") || meet.equals("3")){
			URL += "Act=05&Sub=4&meet="+meet;
		}
		else if (meet.equals("2")){
			URL += "Act=05&Sub=2&meet="+meet;
		}
		Document doc = null;
		try {
			doc = Jsoup.connect(URL).get();
		} catch (IOException e1) {
			System.out.println("web connect error");
			e1.printStackTrace();
		}

		List<String> datas = new ArrayList();
		YundoDto yundo;
		YundoJpaKey yundojpakey;

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
			String data2;
			yundo = new YundoDto();
			// 여기서 dto 생성 data
			while (ie1.hasNext()) {
				data = "";
				data2 = "";
				yundojpakey = new YundoJpaKey(meet,ie1.next().text());
				yundo.setYundoJpaKey(yundojpakey);

				ie2 = ie1.next();
				m = p.matcher(ie2.toString());
				data += m.find() ? m.group() : 0;
				data += "/";
				data2 += ie2.text().length() < 1 ? "없음" : ie2.text();
				data2 += "/";

				ie2 = ie1.next();
				m = p.matcher(ie2.toString());
				data += m.find() ? m.group() : 0;
				data += "/";
				data2 += ie2.text().length() < 1 ? "없음" : ie2.text();
				data2 += "/";

				ie2 = ie1.next();
				m = p.matcher(ie2.toString());
				data += m.find() ? m.group() : 0;
				data += "/";
				data2 += ie2.text().length() < 1 ? "없음" : ie2.text();
				data2 += "/";
				

				ie2 = ie1.next();
				m = p.matcher(ie2.toString());
				data += m.find() ? m.group() : 0;
				data += "/";
				data2 += ie2.text().length() < 1 ? "없음" : ie2.text();
				data2 += "/";

				ie2 = ie1.next();
				m = p.matcher(ie2.toString());
				data += m.find() ? m.group() : 0;
				data += "/";
				data2 += ie2.text().length() < 1 ? "없음" : ie2.text();
				data2 += "/";

				if(meet.equals("1") || meet.equals("3")){
					ie2 = ie1.next();
					m = p.matcher(ie2.toString());
					data += m.find() ? m.group() : 0;
					data += "/";
					data2 += ie2.text().length() < 1 ? "없음" : ie2.text();
					data2 += "/";
				} else if(meet.equals("2")){
					yundo.setJo(ie1.next().text());
				}

				ie2 = ie1.next();
				m = p.matcher(ie2.toString());
				data += m.find() ? m.group() : 0;
				data2 += ie2.text().length() < 1 ? "없음" : ie2.text();
				
				if(meet.equals("2")){
					data += "/";
					data2 += "/";
					ie2 = ie1.next();
					m = p.matcher(ie2.toString());
					data += m.find() ? m.group() : 0;
					data2 += ie2.text().length() < 1 ? "없음" : ie2.text();
				} else if(meet.equals("1") || meet.equals("3")){
					yundo.setJo(ie1.next().text());
				}
				yundo.setIdSeq(data);
				yundo.setName(data2);
			}
			yundos.add(yundo);
		}

		for (YundoDto yundoo : yundos) {
			System.out.println(yundoo);
		}
		return yundos;
	}
}

package com.majin.bit.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.majin.bit.dao.RaceDao;
import com.majin.bit.dto.RaceDto;

@Service
@Transactional
public class RaceService {
	@Autowired
	RaceDao raceDao;

	public List<RaceDto> allrace() {
		List<RaceDto> allrace = raceDao.findBySome();
		return allrace;
	}

	public List<RaceDto> viewrace(String meet, int rcdate, int rcno) {
		List<RaceDto> allrace = raceDao.findBySomeOne(meet, rcdate, rcno);
		return allrace;
	}

	public String[] read(String meet, int rcdate, int rcno) {
		String area="";
		switch(meet) {
			case "서울":
				area="seoul";
				break;
			case "제주":
				area="jeju";
				break;
			case "부산경남":
				area="busan";
				break;
		}
		File file = new File("D:/data/final/real/"+area+".csv");
		area=area+"_"+Integer.toString(rcdate)+"_"+Integer.toString(rcno)+",";
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line = "";
			while ((line = br.readLine()) != null) {
				//System.out.println(line);
				if(line.contains(area)) {
					int test=line.indexOf("\"");
					String chulno=line.substring(test+2,(line.substring(test).indexOf("\",")+test-1));
					String percent=line.substring((line.substring(test).indexOf("\",")+test+4),line.length()-2);
					String[] tokens = chulno.split("(,)");
					ArrayList<String> A = new ArrayList<String>();
					ArrayList<String> B = new ArrayList<String>();
					for (String string : tokens) {
						A.add(string.trim());
					}
					String reg = "\\[(.*?)\\]";
					Pattern pat = Pattern.compile(reg);
					Matcher match = pat.matcher(percent);
					while (match.find()) {
						B.add(match.group());
					}
					String[] C = new String[A.size()];
					int i=0;
					for(String a : A) {
						C[i]=(A.get(i)+"번말 : "+B.get(i));
						i++;
					}
					return C;
				}
			}
			br.close();
		} catch (Exception e) {
		}
	return null;
	}
}

package com.majin.bit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.majin.bit.dao.YundoDao;
import com.majin.bit.dto.YundoDto;
import com.majin.bit.dto.YundoJpaKey;
import com.majin.bit.util.YundoCrawling;

@Service
@Transactional
public class YundoService {
	@Autowired
	YundoDao yundoDao;

	public void yundoCrawling(String meet) {
		YundoCrawling yundoCrawling = new YundoCrawling();
		List<YundoDto> yundos = yundoCrawling.yundoCrwaling(meet);

		for (YundoDto yundo : yundos) {
			yundoDao.save(yundo);
		}
	}
	
	public List<YundoDto> showYundo(String meet){
		YundoJpaKey yundoJpaKey = new YundoJpaKey();
		yundoJpaKey.setMeet(meet);
		List<YundoDto> yundos = yundoDao.findAllByYundoJpaKey(yundoJpaKey);
		return yundos;
	}
	
	public String[][] splitFun(List<YundoDto> yundos,int key) {
		String[][] list = new String[yundos.size()][7];
		int count=0;
		for (YundoDto yundo : yundos) {
			if(key==1)
				list[count]=yundo.getIdSeq().split("/");
			else if(key==2)
				list[count]=yundo.getName().split("/");
			else if(key==3)
				list[count]=yundo.getMeetSeq().split("/");
			count++;
		}
		return list;
	}
}

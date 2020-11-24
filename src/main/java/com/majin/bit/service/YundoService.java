package com.majin.bit.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.majin.bit.dao.YundoDao;
import com.majin.bit.dto.YundoDto;
import com.majin.bit.util.YundoCrawling;

@Service
@Transactional
public class YundoService {
	@Autowired
	YundoDao yundoDao;

	public void yundoCrawling() {
		YundoCrawling yundoCrawling = new YundoCrawling();
		List<YundoDto> yundos = yundoCrawling.yundoCrwaling();

		for (YundoDto yundo : yundos) {
			System.out.println(yundo);
			yundoDao.save(yundo);
		}
	}
}

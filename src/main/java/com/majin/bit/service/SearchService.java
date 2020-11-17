package com.majin.bit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.majin.bit.dao.SearchDao;

@Service
@Transactional
public class SearchService {
	
	@Autowired
	private SearchDao searchDao;
	

}

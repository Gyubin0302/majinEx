package com.majin.bit.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.majin.bit.dto.YundoDto;

public interface YundoDao extends JpaRepository<YundoDto, String> {

}

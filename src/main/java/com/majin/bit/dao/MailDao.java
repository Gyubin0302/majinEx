package com.majin.bit.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.majin.bit.dto.MailDto;

public interface MailDao extends JpaRepository<MailDto, Integer> {
	MailDto findByAddress(String address);
}

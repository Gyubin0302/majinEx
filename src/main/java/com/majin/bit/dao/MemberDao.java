package com.majin.bit.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.majin.bit.dto.Member;


public interface MemberDao extends JpaRepository<Member, Integer> {
	Member findById(String id);
}
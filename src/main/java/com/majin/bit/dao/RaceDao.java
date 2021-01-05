package com.majin.bit.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.majin.bit.dto.RaceDto;

public interface RaceDao extends JpaRepository<RaceDto, Integer> {
	@Query(value="select * from race group by meet,rcdate,rcno,rcdist,sttime",nativeQuery = true)
	List<RaceDto> findBySome();
	
	@Query(value="select * from race where meet=(?1) and rcdate=(?2) and rcno=(?3) order by chulno",nativeQuery = true)
	List<RaceDto> findBySomeOne(@Param("meet") String meet,@Param("rcdate") int rcdate,@Param("int") int rcno);
}

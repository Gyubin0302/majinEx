package com.majin.bit.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.majin.bit.dto.YundoDto;
import com.majin.bit.dto.YundoJpaKey;

public interface YundoDao extends JpaRepository<YundoDto, String> {
	@Query("SELECT e FROM YundoDto e WHERE e.yundoJpaKey.meet = :#{#yundoJpaKey.meet} ORDER BY YEAR DESC")
	List<YundoDto> findAllByYundoJpaKey(@Param("yundoJpaKey")YundoJpaKey yundoJpaKey);
	
	/*
	 * @Query("SELECT e FROM Expense e WHERE e.date BETWEEN :#{#search.start} AND :#{#search.end}"
	 * ) List<Expense> findByDateBetween(@Param("search") Expense.Search search);
	 */
}

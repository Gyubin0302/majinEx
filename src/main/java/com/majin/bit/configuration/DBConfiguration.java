package com.majin.bit.configuration;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * Spring Framework에 Bean 내용들을 토대로 초기값을 설정
 * @Bean 들은 싱글턴 패턴으로 spring framework에 등록된다.
 * 
 * Hikari CP를 Sql세션에 등록하기 위한 설정 내용
 * 
 * @author minhj
 *
 */
 
@Configuration
@MapperScan(basePackages = "com.majin.bit.dao")
public class DBConfiguration {
	
	//데이터베이스 경로설정
		@Bean
		public SqlSessionFactory sqlSessionFactory( DataSource dataSource ) throws Exception {
			System.out.println("DatabaseConfig  SqlSessionFactory");
			
			SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
			sqlSessionFactoryBean.setDataSource(dataSource);
			
			Resource[] arrResource = new PathMatchingResourcePatternResolver().getResources("classpath:sqlmap/*Mapper.xml");
			sqlSessionFactoryBean.setMapperLocations(arrResource);
			sqlSessionFactoryBean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
			
			return (SqlSessionFactory)sqlSessionFactoryBean.getObject();
		}
		
		@Bean
		public SqlSessionTemplate sqlSession( SqlSessionFactory sqlSessionFactory) {
			
			return new SqlSessionTemplate(sqlSessionFactory);
			
		}

}

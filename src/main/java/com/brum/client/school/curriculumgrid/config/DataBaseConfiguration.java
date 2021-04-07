package com.brum.client.school.curriculumgrid.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DataBaseConfiguration {

	@Autowired
	private Environment env;
	
	@Bean
	@Primary
	public DataSource cgDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		
		dataSource.setDriverClassName(env.getProperty("spring.datasource.driverClassName"));
		dataSource.setUrl(env.getProperty("spring.datasource.url"));
		dataSource.setUsername(env.getProperty("spring.datasource.username"));
		dataSource.setPassword(env.getProperty("spring.datasource.password"));
		
		return dataSource;
	}
	
	@Bean(name = "dsOAuth")
	public DataSource oAuthDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		
		dataSource.setDriverClassName(env.getProperty("spring.datasource-oauth.driverClassName"));
		dataSource.setUrl(env.getProperty("spring.datasource-oauth.url"));
		dataSource.setUsername(env.getProperty("spring.datasource-oauth.username"));
		dataSource.setPassword(env.getProperty("spring.datasource-oauth.password"));
		
		return dataSource;
	}

}

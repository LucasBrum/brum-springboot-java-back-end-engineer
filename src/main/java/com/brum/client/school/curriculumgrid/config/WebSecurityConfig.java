package com.brum.client.school.curriculumgrid.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.brum.client.school.curriculumgrid.service.impl.UserInfoServiceImpl;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	public void globalConfiguration(AuthenticationManagerBuilder auth, UserInfoServiceImpl userInfo) throws Exception {
		auth.userDetailsService(userInfo).passwordEncoder(this.encoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		String[] allowed = new String[] {
				"/webjars", "/users", "/static/**"
		};
		
		http.csrf().disable().authorizeRequests()
			.antMatchers(allowed).permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.httpBasic();
	}

}

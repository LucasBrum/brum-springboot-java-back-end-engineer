package com.brum.client.school.curriculumgrid.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	public void globalConfiguration(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder pass = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		auth.inMemoryAuthentication().withUser("rasmoo").password(pass.encode("rasmoo123")).roles("ADMIN");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		String[] allowed = new String[] {
				"/webjars", "/usuario","/static/**"
		};
		
		http.csrf().disable().authorizeRequests()
			.antMatchers(allowed).permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.httpBasic();
	}
}
package com.brum.client.school.curriculumgrid.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.WebSecurity;

@Configuration
@Profile("test")
public class ApplicationNoSecurity extends WebSecurityConfig {

	@Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/**");
    }
}

package com.brum.client.school.curriculumgrid.oauth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.brum.client.school.curriculumgrid.service.impl.UserInfoServiceImpl;

@Configuration
@EnableResourceServer
public class ResourceServer extends ResourceServerConfigurerAdapter {
	
	public static final String RESOURCE_ID = "curriculumgrid";
	
	private UserInfoServiceImpl detalhesDoUsuario;
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.resourceId(RESOURCE_ID);
		resources.tokenServices(this.defaultTokenServices());
		resources.tokenStore(this.tokenStore());
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().authenticated().and().cors();
	}
	
	@Bean
	public UserAuthenticationConverter userTokenConverter() {
		DefaultUserAuthenticationConverter converter = new DefaultUserAuthenticationConverter();
		converter.setUserDetailsService(this.detalhesDoUsuario);
		return converter;
	}
	
	@Bean
	public AccessTokenConverter defaultAccessTokenConverter() {
		DefaultAccessTokenConverter tokenConverter = new DefaultAccessTokenConverter();
		tokenConverter.setUserTokenConverter(this.userTokenConverter());
		return  tokenConverter;
	}
	
	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
		accessTokenConverter.setAccessTokenConverter(this.defaultAccessTokenConverter());
		accessTokenConverter.setSigningKey("assinatura-curriculumgrid");
		return accessTokenConverter;
	}
	
	@Bean
	public DefaultTokenServices defaultTokenServices() {
		DefaultTokenServices tokenServices = new DefaultTokenServices();
		tokenServices.setTokenStore(this.tokenStore());
		return tokenServices;
	}
	
	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(this.jwtAccessTokenConverter());
	}
}

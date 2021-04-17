package com.brum.client.school.curriculumgrid.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.client.LinkDiscoverer;
import org.springframework.hateoas.client.LinkDiscoverers;
import org.springframework.hateoas.mediatype.collectionjson.CollectionJsonLinkDiscoverer;
import org.springframework.plugin.core.SimplePluginRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.OAuth;
import springfox.documentation.service.ResourceOwnerPasswordCredentialsGrant;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableWebMvc
@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig extends WebMvcConfigurationSupport {
	
	public static final String COURSE = "COURSE";
	public static final String SUBJECT = "SUBJECT";
	
	@Value("${host.full.dns.auth.link}")
	private String authLink;

	@Bean
	public Docket curriculumGridApi() {
		return new Docket(DocumentationType.SWAGGER_2).useDefaultResponseMessages(false).select()
				.apis(RequestHandlerSelectors.basePackage("com.brum.client.school.curriculumgrid")).build()
				.apiInfo(this.apiInfo())
				.securitySchemes(Collections.singletonList(this.securitySchema()))
				.securityContexts(Arrays.asList(this.securityContext()))
				.tags(new Tag(COURSE, "Operations to manipulate Course entity"), new Tag(SUBJECT, "Operations to manipulate Subject entity"));
	}
	
	@Bean
	public LinkDiscoverers discoverers() {
		List<LinkDiscoverer> listPlugins = new ArrayList<>();
		listPlugins.add(new CollectionJsonLinkDiscoverer());
		
		return new LinkDiscoverers(SimplePluginRegistry.create());
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Curriculum Grid")
				.description("API Responsible for maintenance of curriculum grid")
				.version("1.0.0")
				.license("")
				.build();
	}
	
	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:META-INF/resources/webjars/");
	}
	
	private OAuth securitySchema() {
		List<AuthorizationScope> authScopes = new ArrayList<>();
		authScopes.add(new AuthorizationScope("cw_logged", "Access logged area"));
		authScopes.add(new AuthorizationScope("cw_not_logged", "Access not logged area"));
		
		List<GrantType> grantTypes = new ArrayList<>();
		grantTypes.add(new ResourceOwnerPasswordCredentialsGrant(this.authLink));
		
		return new OAuth("auth2-Schema", authScopes, grantTypes);
	}
	
	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(this.defaultAuth()).build();
	}
	
	private List<SecurityReference> defaultAuth() {
		AuthorizationScope[] authScopes = new AuthorizationScope[2];
		authScopes[0] = new AuthorizationScope("cw_logged", "Access logged area");
		authScopes[1] = new AuthorizationScope("cw_not_logged", "Access not logged area");
		
		return Collections.singletonList(new SecurityReference("auth2-Schema", authScopes));
	}

}

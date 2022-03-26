package com.api.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
@ComponentScan(basePackages = { "com.api" })
public class WebConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		super.addInterceptors(registry);
		registry.addInterceptor(new LocaleChangeInterceptor());
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST", "PUT", "DELETE")
				.allowedHeaders("*").exposedHeaders("content-length").allowCredentials(false).maxAge(3600);
	}
	
//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//	    registry.setOrder(-1)
//	        .addResourceHandler("/index.html")
//	        .addResourceLocations("classpath:/html/");
//	    super.addResourceHandlers(registry);
//	}
	
	@Override
	  public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    // Including all static resources.

	    registry.addResourceHandler("/assets/**", 
	              "/css/**", 
	              "/images/**",
	              "/js/**"
	         ).addResourceLocations("/assets/",
	              "/css/", 
	              "/images/",
	              "/js/"
	    ).resourceChain(true)
	     .addResolver(new PathResourceResolver());

	     super.addResourceHandlers(registry);
	  }

}
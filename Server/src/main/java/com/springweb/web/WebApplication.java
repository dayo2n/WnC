package com.springweb.web;

import com.springweb.web.aop.LogTraceAop;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing //auditing 사용 위해 꼭(JPA 에서 시간)
@Import(LogTraceAop.class)
@SpringBootApplication
public class WebApplication {

	public static final String APPLICATION_LOCATIONS = "spring.config.location="
			+ "classpath:application.properties,"
			+ "classpath:jwt.properties,"
			+ "classpath:database.properties,"
			+ "classpath:aws.properties";

	public static void main(String[] args) {
		new SpringApplicationBuilder(WebApplication.class)
				.properties(APPLICATION_LOCATIONS)
				.run(args);
	}



}

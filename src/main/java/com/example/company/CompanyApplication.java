package com.example.company;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages="com.example.company")
@EnableAutoConfiguration
@SpringBootApplication
public class CompanyApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(CompanyApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
	        return builder.sources(this.getClass());
	}
}

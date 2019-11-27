package org.kovtun.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = { 
		"org.kovtun.services",
		"org.kovtun.utils",
		"org.kovtun.models",
		"org.kovtun.formdata",
		"org.kovtun.controllers",
		"org.kovtun.start",
		"org.kovtun.repositories",
		"org.kovtun.services",
		"org.kovtun.Dao",
		"org.kovtun.dataModel"
		})
//@EnableAutoConfiguration(exclude = { org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class })
@EnableMongoRepositories(basePackages = {"org.kovtun.repositories"})
public class SpringBootWebApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpringBootWebApplication.class);
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SpringBootWebApplication.class, args);
	}

}
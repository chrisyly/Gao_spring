package com.ucdavis.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(AppProperties.class)
@SpringBootApplication
public class App {

	public static void main(String args[]) {
		SpringApplication.run(App.class);
		System.out.println("The application is running!");
	}
}
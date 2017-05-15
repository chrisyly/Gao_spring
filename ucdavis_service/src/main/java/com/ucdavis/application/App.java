package com.ucdavis.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class App {

	/*
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(App.class);
    }
    */
	
	public static void main(String[] args) {
		
		SpringApplication.run(App.class, args);
		System.out.println("The app is running!");
		
		
	}

}

package com.ucdavis.application.WebController;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.ucdavis.application.Greeting;


@RestController
@SessionAttributes("id") 
public class WelcomeController {

	private static final String template = "Hello, %s! Welcome to UC DAVIS.";
	
	private final AtomicLong counter = new AtomicLong();
	
	@RequestMapping ("/welcomeAboard/{id}")
	public Greeting greeting(@PathVariable final String id) {
		return new Greeting(counter.incrementAndGet(), String.format(template, id));
	}
}

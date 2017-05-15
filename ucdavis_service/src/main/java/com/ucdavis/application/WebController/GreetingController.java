package com.ucdavis.application.WebController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;

//import com.ucdavis.application.Greeting;

@Controller
public class GreetingController {

/*
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
*/
    @GetMapping("/")
    public String greeting() {
    	System.out.print("let's go!");
    	return "hello";
    }
}
    
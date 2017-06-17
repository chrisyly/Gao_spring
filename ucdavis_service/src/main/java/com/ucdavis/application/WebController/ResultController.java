package com.ucdavis.application.WebController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ResultController {

	@RequestMapping("/result")
	public String returnResult() {
		return "result";
	}
	
	@RequestMapping("/csv")
	public String returnCSV() {
		return "csv";
	}
}

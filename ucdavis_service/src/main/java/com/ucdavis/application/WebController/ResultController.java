package com.ucdavis.application.WebController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ResultController {

	@RequestMapping("/resultType")
	public String returnResult() {
		return "resultType";
	}
	
	@RequestMapping("/csv")
	public String returnCSV() {
		return "csv";
	}
}

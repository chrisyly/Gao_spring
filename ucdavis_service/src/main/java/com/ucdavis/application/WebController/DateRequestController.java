package com.ucdavis.application.WebController;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.ucdavis.application.DateRequest;

@RestController
@SessionAttributes("id")
public class DateRequestController {
	
	private String requestId;
	
	private String bloomDate;
	
	private String currentDate;
	
	private String county;
	
	private String station;
	
	@RequestMapping("/getDateRequest")
	public DateRequest getDateRequest() {
		return new DateRequest(requestId,bloomDate, currentDate, county, station);
	}

}

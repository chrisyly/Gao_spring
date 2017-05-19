package com.ucdavis.application.WebController;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ucdavis.application.DateRequest;

@RestController
@RequestMapping(DateRequestController.Date_Request_URI)
public class DateRequestController {
	
	public final static String Date_Request_URI = "svc/v1/requests";
	
	@RequestMapping(value = "{requestNumber}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public DateRequest getRequest(@PathVariable final int requestNumber) {
		DateRequest request = new DateRequest();
		request.setBloomDate("2011-01-21");
		request.setPredictDate("2011-03-01");
		System.out.print(request.toString());
		return request;	
	}
}

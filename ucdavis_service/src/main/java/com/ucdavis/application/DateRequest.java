package com.ucdavis.application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class DateRequest {
	
	// id of the requester
	private Long id;

	private String bloomDate;
	
	private String predictDate;
	
	/*
	public DateRequest(String bloomDate, String predDate, String id) throws ParseException {
		this.bloomDate = bloomDate;
		this.predDate = predDate;
		this.id = id;
		GetListOfDaysBy(this.bloomDate, this.predDate);
		this.numberOfDays = listOfDays.length;
		DateRequest.RequestNumber++;
	}
	*/
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBloomDate() {
		return bloomDate;
	}

	public void setBloomDate(String bloomDate) {
		this.bloomDate = bloomDate;
	}

	public String getPredictDate() {
		return predictDate;
	}

	public void setPredictDate(String predictDate) {
		this.predictDate = predictDate;
	}

	public DateRequest() {
		super();
	}	
}
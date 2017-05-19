package com.ucdavis.application;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

public class DateRequest {
	
	private int requestCount;
	
	// id of the requester
	private String id;

	private String bloomDate;
	
	private String predictDate;
	
	
	public DateRequest(int requestCount, String id, String bloomDate, String predictDate) {
		super();
		this.id = id;
		this.bloomDate = bloomDate;
		this.predictDate = predictDate;
	}


	public String getId() {
		return id;
	}



	public void setId(String id) {
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
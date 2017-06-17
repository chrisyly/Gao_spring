package com.ucdavis.application;

public class DateRequest {
	
	
	private String id;

	private String bloomDate;
	
	private String predictDate;
	
	
	public DateRequest(Long requestNumber, String id, String bloomDate, String predictDate) {
		super();
		this.id = id;
		this.bloomDate = bloomDate;
		this.predictDate = predictDate;
	}

	public DateRequest() {
		super();
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

	@Override
	public String toString() {
		return "DateRequest [id=" + id + ", bloomDate=" + bloomDate + ", predictDate=" + predictDate + "]";
	}	
}
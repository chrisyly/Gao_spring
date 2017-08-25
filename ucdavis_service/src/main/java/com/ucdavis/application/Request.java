package com.ucdavis.application;

public class Request {
	
	private String id;
	private String start_date;
	private String end_date;
	private String targets;
	private boolean hourly;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public String getTargets() {
		return targets;
	}
	public void setTargets(String targets) {
		this.targets = targets;
	}
	public boolean isHourly() {
		return hourly;
	}
	public void setHourly(boolean hourly) {
		this.hourly = hourly;
	}
	@Override
	public String toString() {
		return "Request [id=" + id + ", start_date=" + start_date + ", end_date=" + end_date + ", targets=" + targets
				+ ", hourly=" + hourly + "]";
	}
}

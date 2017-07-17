package com.ucdavis.application;

public class DateRequest {
	
	
	private String id;

	private String bloomDate;
	
	private String currentDate;
	
	private String county;
	
	private String station;
	
	public DateRequest(String id, String bloomDate, String currentDate, String county, String station) {
		super();
		this.id = id;
		this.bloomDate = bloomDate;
		this.currentDate = currentDate;
		this.county = county;
		this.station = station;
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

	public String getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}

	/**
	 * @return the county
	 */
	public final String getCounty() {
		return county;
	}

	/**
	 * @param county the county to set
	 */
	public final void setCounty(String county) {
		this.county = county;
	}

	/**
	 * @return the station
	 */
	public final String getStation() {
		return station;
	}

	/**
	 * @param station the station to set
	 */
	public final void setStation(String station) {
		this.station = station;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
s	 */
	@Override
	public String toString() {
		return "DateRequest [id=" + id + ", bloomDate=" + bloomDate + ", currentDate=" + currentDate + ", county="
				+ county + ", station=" + station + "]";
	}

}
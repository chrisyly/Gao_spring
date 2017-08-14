package com.ucdavis.application.Service;

public class Main {
	public static int main(String args[]) {
		CimisService test = new CimisService();
		test.processRequestQuery("http://et.water.ca.gov/api/data?appKey=4a3b5300-f248-4b6a-bb49-8368d1cac187&targets=2,8,127&startDate=2010-01-01&endDate=2010-01-05");
		return 1;
	}
}

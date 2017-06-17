package com.ucdavis.application.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ucdavis.application.DateRequest;

@Service
public class DateRequestService {

	private static List<DateRequest> requestList = new ArrayList<DateRequest>();
	
	private static Long requestNumber = (long) 0;
	
	public List<DateRequest> retrieveRequests(String user) {
		List<DateRequest> filteredRequest = new ArrayList<>();
		for (DateRequest request : requestList) {
			if (request.getId().equals(user))
				filteredRequest.add(request);
		}
		return filteredRequest;
	}
	
	public void addRequest(String id, String bloomDate, String predictDate) {
		requestList.add(new DateRequest(++requestNumber, id, bloomDate, predictDate));
	}
	
	public void deleteRequest(String id) {
		Iterator<DateRequest> iterator = requestList.iterator();
		while (iterator.hasNext()) {
			DateRequest request = iterator.next();
			if(request.getId().equals(id)) {
				iterator.remove();
			}
		}
	}
}

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;


public class Main {

	public static void main(String args[]) throws JsonParseException, JsonMappingException, IOException, JSONException {
		UCDavisService davisService = new UCDavisService();
		CimisService cimisService = new CimisService();
		JsonParser parser = new JsonParser();
		
		// create & update the local csv data
		//String base = "http://et.water.ca.gov/api/data?appKey=4a3b5300-f248-4b6a-bb49-8368d1cac187&targets=";
		//String ext = "&startDate=2017-01-01&endDate=2017-01-10&dataItems=day-air-tmp-avg";
		/*for (int i = 1; i <= 252; i++) {
			JSONObject jsonObject = cimisService.processRequestQuery(base + i + ext);
			List<JSONObject> records = parser.getListFromJson(parser.extractDataItems((parser.jsonStringToMap(jsonObject).get("Records"))));
			davisService.writeToCSV(records, String.valueOf(i));
			
		}*/
		davisService.readLocalCsv();
		System.out.println(davisService.getData().toString());
		
		/*
		String base = "http://et.water.ca.gov/api/data?appKey=4a3b5300-f248-4b6a-bb49-8368d1cac187&targets=2";
		String ext = "&startDate=2017-01-01&endDate=2017-01-10&dataItems=day-air-tmp-avg";
		String target = "2";
		JSONObject jsonObject = cimisService.processRequestQuery(base + ext);
		List<JSONObject> records = parser.getListFromJson(parser.extractDataItems((parser.jsonStringToMap(jsonObject).get("Records"))));
		davisService.writeToMap(records,target);
		System.out.println(davisService.getData().toString());
		System.out.println("Hello SB");
		*/
		//davisService.readLocalCsv();
		//System.out.println(davisService.getData().toString());

		
        // getting all zipcodes
        /*
        String stationQuery = "http://et.water.ca.gov/api/station";
        JSONObject stationObject = cimisService.processRequestQuery(stationQuery);
        System.out.println(stationObject.toString());;
        
        Object stations = stationObject.get("Stations");
        List<JSONObject> stationsList = new ArrayList<>();
    	Map<String, String> zipcodes  = new HashMap<>(); // what we want
        if (stations instanceof JSONArray) {
        	stationsList = parser.getListFromJson((JSONArray)stations);
        	for (JSONObject stationInfo : stationsList) {
        		JSONArray zipcode = stationInfo.getJSONArray("ZipCodes");
        		for (int i = 0; i < zipcode.length(); i++) {
        			if (zipcodes.containsKey(zipcode.get(i))) {
        				continue;
        			} else {
        				zipcodes.put((String)zipcode.get(i),(String) zipcode.get(i));
        			}
        		}
        	}
        }
        System.out.println(zipcodes.keySet().toString());
        System.out.println(zipcodes.keySet().size());
        
        Iterator<?> itr = zipcodes.keySet().iterator();
        while (itr.hasNext()) {
        	String fileName = (String) itr.next();
        	String base = "http://et.water.ca.gov/api/data?appKey=4a3b5300-f248-4b6a-bb49-8368d1cac187&targets=";
    		String ext = "&startDate=2017-01-01&endDate=2017-01-10&dataItems=day-air-tmp-avg";
    		JSONObject jsonObject = cimisService.processRequestQuery(base + fileName + ext);
			List<JSONObject> records = parser.getListFromJson(parser.extractDataItems((parser.jsonStringToMap(jsonObject).get("Records"))));
			davisService.writeToCSV(records, fileName);
        }
        */
	}
}

import java.io.IOException;
import java.util.List;

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
		String base = "http://et.water.ca.gov/api/data?appKey=4a3b5300-f248-4b6a-bb49-8368d1cac187&targets=";
		String ext = "&startDate=2017-01-01&endDate=2017-01-10&dataItems=day-air-tmp-avg";
		for (int i = 1; i <= 252; i++) {
			JSONObject jsonObject = cimisService.processRequestQuery(base + i + ext);
			List<JSONObject> records = parser.getListFromJson(parser.extractDataItems((parser.jsonStringToMap(jsonObject).get("Records"))));
			davisService.writeToCSV(records, String.valueOf(i));
		}
		
		// test csv readers
		String csvPath = "2.csv";
        System.out.println(davisService.getDataMapFromCSV(csvPath).toString());
	}
}

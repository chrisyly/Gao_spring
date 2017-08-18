import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class Main {


	public static void main(String args[]) throws JsonParseException, JsonMappingException, IOException, JSONException {
		CimisService test = new CimisService();
		JsonParser parser = new JsonParser();
		JSONObject jsonObject = test.processRequestQuery("http://et.water.ca.gov/api/data?appKey=4a3b5300-f248-4b6a-bb49-8368d1cac187&targets=2,8,127&startDate=2010-01-01&endDate=2010-01-02&dataItems=day-air-tmp-avg");
		System.out.println(jsonObject.toString());

		Map<String, String> mapping = parser.jsonStringToMap(jsonObject);
		
		Iterator<?> itr = mapping.keySet().iterator();
		while (itr.hasNext()) {
			String key = (String) itr.next();
			//if (key.equals("Records")) {
				//System.out.println(key + mapping.get(key).toString());
			//}
		}
	}
}

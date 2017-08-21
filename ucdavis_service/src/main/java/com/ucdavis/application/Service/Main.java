import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.json.CDL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;


public class Main {
	
	@SuppressWarnings("deprecation")
	public static void createFileWithJSONString(String jsonObject){
        try {

            JSONArray docs = new JSONArray(jsonObject);

            File file=new File("/Users/Mushi/Documents/workspace/Test/fromJSON.csv");
            String csv = CDL.toString(docs);
            FileUtils.writeStringToFile(file, csv);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }        
    }


	public static void main(String args[]) throws JsonParseException, JsonMappingException, IOException, JSONException {
		CimisService test = new CimisService();
		JsonParser parser = new JsonParser();
		JSONObject jsonObject = test.processRequestQuery("http://et.water.ca.gov/api/data?appKey=4a3b5300-f248-4b6a-bb49-8368d1cac187&targets=96035&startDate=2010-01-01&endDate=2010-06-30&dataItems=day-air-tmp-avg");
		// System.out.println(jsonObject.toString());

		//map
		Map<String, String> mapping = parser.jsonStringToMap(jsonObject);
		
		Iterator<?> itr = mapping.keySet().iterator();
		while (itr.hasNext()) {
			String key = (String) itr.next();
			//if (key.equals("Records")) {
				//System.out.println(key + mapping.get(key).toString());
			//}
		}
		
		JSONArray records = parser.extractDataItems(mapping.get("Records").toString());
		UCDavisService service = new UCDavisService(parser.getListFromJson(records));
		service.setAllResponse();
		System.out.println(service.typeResult());
	}
}

package com.ucdavis.application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * @author Mushi
 * date: 09/26/2017
 * OpenWeatherService, operating openWeatherMap forecast in either JSON or XML
 * @parm appKey: registered OpenWeatherMap APP KEY for services access
 * @parm requestQuert: String query for OpenWeatherMap data
 *  */
public class OpenWeatherService {
	
	private static final OpenWeatherService openWeatherService = new OpenWeatherService();
	private final String appKey = "983b025bcbd0eb328924d1117c5d4647";
	
	public String getAppKey() {
		return appKey;
	}

	public OpenWeatherService() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public static OpenWeatherService getService() {
		return openWeatherService;
	}
	
	public JSONObject processRequestQuery(String query) {
		BufferedReader bufferedReader = null;
		try {
			URL url = new URL(query);
			bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
			StringBuilder jsonStringBuilder = new StringBuilder();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				jsonStringBuilder.append(line);
			}
			//System.out.println(jsonStringBuilder.toString());
			JSONObject jsonObject = new JSONObject(jsonStringBuilder.toString());
			// TODO update data using jsonObject
			return jsonObject;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				bufferedReader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
    }
	
}

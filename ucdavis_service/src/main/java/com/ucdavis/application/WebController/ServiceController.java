package com.ucdavis.application.WebController;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.ucdavis.application.CimisService;
import com.ucdavis.application.JsonParser;
import com.ucdavis.application.OpenWeatherService;
import com.ucdavis.application.Request;
import com.ucdavis.application.UCDavisService;

@Controller
@SessionAttributes("id") 
public class ServiceController {
	
	Map<String, CimisService> cimisServiceMap = new HashMap<>();
	Map<String, UCDavisService> ucDavisServiceMap = new HashMap<>();
	
	@RequestMapping(value = "/request", method = RequestMethod.GET)
	public ModelAndView showDateForm() {
		return new ModelAndView("index", "request", new Request());
	}
	
	
	@RequestMapping(value = "/result") 
	public ModelAndView result(@PathVariable final String id) {
		ModelAndView mv = new ModelAndView();
		return mv;
	}
		
	@RequestMapping(value = "/addRequest", method = RequestMethod.POST)
	public String submit(@Valid @ModelAttribute("request") final Request request,final BindingResult result, final ModelMap modelMap, Model model) throws ParseException, Exception {
		if(result.hasErrors()) {
			return "error";
		}
		// services
		UCDavisService davisService = new UCDavisService();
		CimisService cimisService = new CimisService();
		JsonParser parser = new JsonParser();
		// grasp query map
		modelMap.addAttribute("targets", request.getTargets());
		modelMap.addAttribute("bloomDate", request.getStart_date());
		modelMap.addAttribute("currentDate", request.getEnd_date());
		modelMap.addAttribute("hourly", request.isHourly());
		
		// getting data
		String query = request.toString();
		Map<String, String> queryMapping = davisService.processQuery(query);
		JSONObject jsonObject = cimisService.processRequestQuery(parser.returnQueryUrl(queryMapping, cimisService.getAppKey()));
		// JSONObject -> map -> extractDataItems -> list
		List<JSONObject> list = parser.getListFromJson(parser.extractDataItems((parser.jsonStringToMap(jsonObject)).get("Records").toString()));
		// write map
		davisService.writeToMap(list, request);
		davisService.writeToCSV(queryMapping.get("targets"));
		// get result
		String heatUnitResult = davisService.calculateHeatUnitResult(query);
		System.out.println(davisService.getData().toString());
		
		model.addAttribute("result", heatUnitResult);
		
		return "result";
	}
}
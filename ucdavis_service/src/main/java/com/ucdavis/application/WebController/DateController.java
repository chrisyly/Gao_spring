package com.ucdavis.application.WebController;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.ucdavis.application.DateRequest;

@Controller
@SessionAttributes("id") 
public class DateController {
	
	Map<String, DateRequest> dateRequestMap = new HashMap<>();
	
	@RequestMapping(value = "/request", method = RequestMethod.GET)
	public ModelAndView showDateForm() {
		
		return new ModelAndView("dateHome", "dateRequest", new DateRequest());
	}
	

	
	@RequestMapping(value = "/svc/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody DateRequest getEmployeeById(@PathVariable final String id) {
		return dateRequestMap.get(id);
	}

	
	@RequestMapping(value = "/addRequest", method = RequestMethod.POST)
	public String submit(@Valid @ModelAttribute("dateRequest") final DateRequest dateRequest, final BindingResult result, final ModelMap model) {
		if(result.hasErrors()) {
			return "error";
		}
		
		model.addAttribute("id", dateRequest.getId());
		model.addAttribute("bloomDate", dateRequest.getBloomDate());
		System.out.println(dateRequest.getBloomDate());
		model.addAttribute("predictDate", dateRequest.getPredictDate());
		System.out.println(dateRequest.getPredictDate());
		dateRequestMap.put(dateRequest.getId(), dateRequest);
		
		String bloomDate = (String) model.get("bloomDate");
		String predictDate = (String) model.get("predictDate");
		
		return "loader";
	}
}
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
import org.springframework.web.servlet.ModelAndView;

import com.ucdavis.application.DateRequest;

@Controller
public class DateController {

	Map<Long, DateRequest> dateRequestMap = new HashMap<>();
	
	@RequestMapping(value = "/request", method = RequestMethod.GET)
	public ModelAndView showDateForm() {
		return new ModelAndView("dateHome", "dateRequest", new DateRequest());
	}
	
	@RequestMapping(value = "/svc/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody DateRequest getEmployeeById(@PathVariable final long id) {
		return dateRequestMap.get(id);
	}

	
	@RequestMapping(value = "/addRequest", method = RequestMethod.POST)
	public String submit(@Valid @ModelAttribute("dateRequest") final DateRequest dateRequest, final BindingResult result, final ModelMap model) {
		if(result.hasErrors()) {
			return "error";
		}
		
		model.addAttribute("id", dateRequest.getId());
		model.addAttribute("bloomDate", dateRequest.getBloomDate());
		model.addAttribute("predictDate", dateRequest.getPredictDate());
		dateRequestMap.put(dateRequest.getId(), dateRequest);
		System.out.println(dateRequestMap.toString());
		
		String bloomDate = model.get("bloomDate").toString();
		String predictDate = model.get("predictDate").toString();
		System.out.println(bloomDate +"to " + predictDate);
		
		return "DateSubmitted";
	}
}
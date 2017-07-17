package com.ucdavis.application.WebController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.ucdavis.application.Service.HeatUnitService;

@Controller
@SessionAttributes("id") 
public class DateController {
	
	Map<String, DateRequest> dateRequestMap = new HashMap<>();
	Map<String, HeatUnitService> serviceMap = new HashMap<>();
	
	@RequestMapping(value = "/request", method = RequestMethod.GET)
	public ModelAndView showDateForm() {
		
		return new ModelAndView("dateHome", "dateRequest", new DateRequest());
	}
	

	
	@RequestMapping(value = "/svc/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody String getRequestById(@PathVariable final String id) throws ParseException {
		return serviceMap.get(id).toString() + serviceMap.get(id).printDayWithSumHU().toString();
	}
	
	@RequestMapping(value = "/result") 
	public ModelAndView result(@PathVariable final String id) {
		HeatUnitService service = serviceMap.get(id);
		ModelAndView mv = new ModelAndView();
		mv.addObject(service.toString());
		return mv;
	}
		
	@RequestMapping(value = "/addRequest", method = RequestMethod.POST)
	public @ResponseBody String submit(@Valid @ModelAttribute("dateRequest") final DateRequest dateRequest, final BindingResult result, final ModelMap modelMap) throws ParseException, Exception {
		if(result.hasErrors()) {
			return "error";
		}
		
		modelMap.addAttribute("id", dateRequest.getId());
		modelMap.addAttribute("bloomDate", dateRequest.getBloomDate());
		System.out.println(dateRequest.getBloomDate());
		modelMap.addAttribute("currentDate", dateRequest.getCurrentDate());
		System.out.println(dateRequest.getCurrentDate());
		
		// new updates
		modelMap.addAttribute("county", dateRequest.getCounty());
		System.out.println(dateRequest.getCounty());
		modelMap.addAttribute("station", dateRequest.getStation());
		System.out.println(dateRequest.getStation());
		//
		
		dateRequestMap.put(dateRequest.getId(), dateRequest);
		System.out.println(dateRequest.toString());
		
		
		// Jul.10th
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateBloom = sdf.parse(dateRequest.getBloomDate());
		Date dateCurt = sdf.parse(dateRequest.getCurrentDate());
		
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
		String bloom = sdf2.format(dateBloom);
		String current = sdf2.format(dateCurt);
		
		HeatUnitService service = new HeatUnitService();
	
		service.setBloomDate(bloom);
		service.setCurrentDate(current);
		service.setStationName(dateRequest.getStation());
		
		service.setDataList();
		
		service.setHeatUnit();
		
		service.setSumOfHeatUnit();
		
		service.setMaxHeatUnit(2050.00);

		service.setPrediction();
		
		serviceMap.put(dateRequest.getId(), service);
		
		return service.toString() + service.printDayWithSumHU().toString();
	}
}
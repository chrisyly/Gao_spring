package com.ucdavis.application.WebController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.ucdavis.application.Service.DateRequestService;

@Controller
@SessionAttributes("name")
public class DateRequestListController {

	@Autowired
	private DateRequestService service;
	
	@RequestMapping(value="list-dateRequest", method=RequestMethod.GET)
	public String showRequestList(ModelMap model, String name) {
		String user = (String)model.get("name");
		model.addAttribute("requestList", service.retrieveRequests(user));
		return "list-dateRequest";
	}
}

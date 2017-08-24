package com.ucdavis.application.WebController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.ucdavis.application.LoginService;

@Controller
@SessionAttributes("id")
public class LoginController {

	@Autowired
	private LoginService loginService;

	@RequestMapping(value = {"/login", "/"}, method = RequestMethod.GET)
	public String showLoginPage() {
		return "login";
	}

	@RequestMapping(value = {"/login", "/"}, method = RequestMethod.POST)
	public String handleUserLogin(ModelMap model, @RequestParam String id,
			@RequestParam String password) {

		if (!loginService.validateUser(id, password)) {
			model.put("errorMessage", "Invalid Credentials");
			return "login";
		}

		model.put("id", id);
		return "welcome";
	}
}
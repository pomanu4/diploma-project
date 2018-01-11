package one.two.three.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import one.two.three.entity.Product;
import one.two.three.entity.User;

@SessionAttributes("multiLang")
@Controller
public class MainController {


	@RequestMapping(value = {"/", "indexPage"},  method = RequestMethod.GET)
	public String index() {

		return "index";
	}
	
	@RequestMapping(value = "translate", method = RequestMethod.GET)
	public String ukrLanguage(@RequestParam("language") String string, Model model) {
		model.addAttribute("multiLang", string);
		
		return "index";
	}
	
	@RequestMapping(value = "information", method = RequestMethod.GET)
	public String infoPage() {
		
		return "infoPage";
	}

	@RequestMapping(value = "registration", method = RequestMethod.GET)
	public String registrationPage(Model model) {
		
		model.addAttribute("user", new User());
		return "registration";
	}

	@RequestMapping(value = "usr-userpage", method = RequestMethod.GET)
	public String userPage(Model model) {
		model.addAttribute("product", new Product());
		return "userpage";
	}

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String loginPage() {

		return "loginpage";
	}
	
	@RequestMapping(value = "loginFail", method = RequestMethod.GET)
	public String loginFailPage(Model model) {
		String message = "wrong email or password";
		model.addAttribute("errMess", message);
		return "loginpage";
	}

	@RequestMapping(value = "admin", method = RequestMethod.GET)
	public String adminPage() {

		return "admin";
	}
		
}

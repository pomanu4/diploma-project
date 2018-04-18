package ua.com.company.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ua.com.company.entity.Complain;
import ua.com.company.entity.User;
import ua.com.company.service.IcomplainService;

@Controller
public class ComplainObjectController {

	@Autowired
	private IcomplainService complainService;

	private int thisUserId() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return user.getId();
	}

	@RequestMapping(value = "admin-complainList", method = RequestMethod.GET)
	public String complainList(Model model) {
		List<Complain> complainList = complainService.findAll();
		model.addAttribute("list", complainList);
		
		return "complainPage";
	}

	@RequestMapping(value = "admin-todayMessages", method = RequestMethod.GET)
	public String todayComplainList(Model model) {
		List<Complain> complainList = complainService.findTodayComplain();
		model.addAttribute("list", complainList);
		
		return "complainPage";
	}

	@RequestMapping(value = "usr-complain", method = RequestMethod.POST)
	public String createComplain(@RequestParam("message") String text) {
		complainService.addComplain(thisUserId(), text);
		
		return "redirect:usr-userpage";
	}

}

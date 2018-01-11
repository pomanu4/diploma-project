package one.two.three.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import one.two.three.entity.Complain;
import one.two.three.entity.User;
import one.two.three.service.IcomplainService;

@Controller
public class ComplainObjectController {
	
	@Autowired
	private IcomplainService complainService;
	
	private int thisUserId() {
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int user_id = user.getId();
		return user_id;
	}
	
	@RequestMapping(value = "admin-complainList", method = RequestMethod.GET)
	public String complainList(Model model) {
		List<Complain> complainList = complainService.findAll();
		model.addAttribute("list", complainList);
		return "complainPage";
	}
	
	@RequestMapping(value = "admin-todayMessages", method = RequestMethod.GET)
	public String TodayComplainList(Model model) {
		List<Complain> complainList = complainService.findTodayComplain();
		model.addAttribute("list", complainList);
		return "complainPage";
	}
	
////post	
	@RequestMapping(value = "usr-complain", method = RequestMethod.POST)
	public String createBlame(@RequestParam("message") String picture) {
		complainService.addComplain(thisUserId(), picture);
		
		return "redirect:usr-userpage";
	}

}

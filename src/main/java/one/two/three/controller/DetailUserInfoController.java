package one.two.three.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import one.two.three.entity.DetailUserInfo;
import one.two.three.entity.OrderDetails;
import one.two.three.entity.User;
import one.two.three.service.IdetailUserInfo;

@Controller
public class DetailUserInfoController {
	
	@Autowired
	private IdetailUserInfo detailUserInfoService;
	
	private int thisUserId() {
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int user_id = user.getId();
		
		return user_id;
	}
	
	@RequestMapping(value = "usr-orderHistory", method = RequestMethod.GET)
	public String userOrderHistory(Model model) {
		
		DetailUserInfo detailUserInfo = detailUserInfoService.finduserOrderHistory(thisUserId());
		Map<String, OrderDetails> historyMap = detailUserInfo.getHistory();
		model.addAttribute("mapa", historyMap);
		
		return "history";
	}
	
	@RequestMapping(value = "usr-editeProfile", method = RequestMethod.GET)
	public String usrProfile(Model model) {
		DetailUserInfo user = detailUserInfoService.findOneById(thisUserId());
		model.addAttribute("user", user);
		
		return "userprofile";
	}
	
	@RequestMapping(value = "usr-newPhoto", method = RequestMethod.POST)
	public String userNewPhoto(@RequestParam("userid") int user_id, @RequestParam("newPhoto") MultipartFile newPhoto) {
		detailUserInfoService.addNewPhoto(newPhoto, user_id);
		
		return "redirect:usr-editeProfile";
	}
	
	@RequestMapping(value = "usr-newContact", method = RequestMethod.POST)
	public String userNewContact(@RequestParam("userid") int user_id, @RequestParam("newContact") String newContact) {
		detailUserInfoService.addNewContact(newContact, user_id);
		
		return "redirect:usr-editeProfile";
	}
	
	@RequestMapping(value = "admin-adminUserHistory", method = RequestMethod.GET)
	public String adminUserHistoryDetails(Model model, @RequestParam("userId") int user_id) {
		DetailUserInfo detailUserInfo = detailUserInfoService.finduserOrderHistory(user_id);
		Map<String, OrderDetails> historyMap = detailUserInfo.getHistory();
		model.addAttribute("mapa", historyMap);
		
		return "history";
	}

}

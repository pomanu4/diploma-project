package one.two.three.controller;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import one.two.three.Validator.UserValidator;
import one.two.three.entity.User;
import one.two.three.service.IuserService;

@Controller
public class UserObjectController {
	
	@Autowired
	private IuserService userService;
	@Autowired
	private UserValidator userValidator;
	
	private int thisUserId() {
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int user_id = user.getId();
		return user_id;
	}
	
	@RequestMapping(value = "admin-findUserById", method = RequestMethod.GET)
	public String adminFindUserById(@RequestParam("userId") int user_id, Model model) {
		User user = userService.oneUserWithInfo(user_id);
		List<User> userList = new ArrayList<>();
		userList.add(user);
		model.addAttribute("userlist", userList);
		return "uselist";
	}
	
	@RequestMapping(value = "admin-user-{id}", method = RequestMethod.GET)
	public String adminUserDetailInfo(Model model, @PathVariable("id") int id) {
		User user = userService.oneUserWithInfo(id);
		model.addAttribute("user", user);
		return "adminUserDetail";
	}
	
	@RequestMapping(value = "admin-findUserByName", method = RequestMethod.GET)
	public String adminFindUserByName(@RequestParam("userName") String user_name, Model model) {
		List<User> userList = userService.findUserByName(user_name);
		model.addAttribute("userlist", userList);
		return "uselist";
	}
	
	@RequestMapping(value = "admin-findUserByNamePattern", method = RequestMethod.GET)
	public String adminFindUserByNamePattern(@RequestParam("userName") String namePattern, Model model) {
		List<User> userList = userService.findUserByNamePattern(namePattern);
		model.addAttribute("userlist", userList);
		return "uselist";
	}
	
	@RequestMapping(value = "admin-findUserByimail", method = RequestMethod.GET)
	public String adminFindUserByEmail(@RequestParam("userEmail") String email, Model model) {
		List<User> userList = userService.getUserWithInfoByEmail(email);
		model.addAttribute("userlist", userList);
		return "uselist";
	}
	
	@RequestMapping(value = "admin-adminUserDetails-{id}", method = RequestMethod.GET)
	public String adminUserDetails(Model model, @PathVariable("id") int user_id) {
		User user = userService.oneUserWithInfo(user_id);
		model.addAttribute("user", user);
		return "adminUserDetail";
	}
	
	@RequestMapping(value = "admin-author-{id}", method = RequestMethod.GET)
	public String complainAuthor(Model model, @PathVariable("id") int user_id) {
		User user = userService.oneUserWithInfo(user_id);
		model.addAttribute("user", user);
		return "adminUserDetail";
	}
	
	@RequestMapping(value = "admin-adminAllUsers", method = RequestMethod.GET)
	public String adminAllUsers(Model model) {
		List<User> userList = userService.findAllUsers();
		model.addAttribute("userlist", userList);
		return "uselist";
	}
	
	@RequestMapping(value = "admin-inspectUserComment-{id}", method = RequestMethod.GET)
	public String comentUserDetail(Model model, @PathVariable("id") int userId) {
		User user = userService.oneUserWithInfo(userId);
		model.addAttribute("user", user);
		return "adminUserDetail";
	}
	
	
///////// post	
	@RequestMapping(value = "admin-setDefoultPhoto", method = RequestMethod.POST)
	public String adminSetDefoultPhoto(@RequestParam("userId") int user_id, Model model) {
		userService.setDefoultPhoto(user_id);
		User user = userService.oneUserWithInfo(user_id);
		model.addAttribute("user", user);
		return "adminUserDetail";
	}
	
	@RequestMapping(value = "admin-setnewImail", method = RequestMethod.POST)
	public String adminSetNewImail(@RequestParam("userId") int user_id, Model model,
			@RequestParam("newImail") String email) {
		userService.setNewImail(email, user_id);
		User user = userService.oneUserWithInfo(user_id);
		model.addAttribute("user", user);
		return "adminUserDetail";
	}
	
	@RequestMapping(value = "admin-setPassword", method = RequestMethod.POST)
	public String adminSetNewPassword(@RequestParam("userId") int user_id,
			Model model, @RequestParam("newPassword") String password) {
		userService.setNewPassword(password, user_id);
		User user = userService.oneUserWithInfo(user_id);
		model.addAttribute("user", user);
		return "adminUserDetail";
	}
	
	@RequestMapping(value = "admin-setRole", method = RequestMethod.POST)
	public String adminSetNewRole(@RequestParam("userId") int user_id,
			Model model, @RequestParam("userRole") String role) {
		userService.setNewRole(user_id, role);
		User user = userService.oneUserWithInfo(user_id);
		model.addAttribute("user", user);
		return "adminUserDetail";
	}
	
	@RequestMapping(value = "admin-blockAccount", method = RequestMethod.POST)
	public String adminBlockUserAccount(@RequestParam("userId") int user_id,
			Model model, @RequestParam("status") boolean value) {
		userService.bolckUserAccount(user_id, value);
		User user = userService.oneUserWithInfo(user_id);
		model.addAttribute("user", user);
		return "adminUserDetail";
	}
	
	@RequestMapping(value = "userReg", method = RequestMethod.POST)
	public String registrationProcess(
			@ModelAttribute("user") @Valid User user, 
			BindingResult result, 
			@RequestParam("userPhoto") MultipartFile photo,
			@RequestParam("contactInformation") String contactInfo,
			Model model) {
		
		if(result.hasErrors()) {
			return "registration";
		}
		if(contactInfo.trim().equals("") || contactInfo.trim().equals(null)) {
			String message = "if this field be empty people can not contact with you";
			model.addAttribute("emptyField", message);
			return "registration";
		}
		userService.saveUserWithInfo(photo, contactInfo, user);
		return "redirect:/";
	}
	
	
	@InitBinder()
	public void binderValidUser(WebDataBinder binder) {
		binder.addValidators(userValidator);
	}
	
	

}

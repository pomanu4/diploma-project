package one.two.three.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import one.two.three.Validator.DetailUserInfoValidator;
import one.two.three.Validator.ProductValidator;
import one.two.three.Validator.UserValidator;
import one.two.three.components.ActivUserOrderDetailChecker;
import one.two.three.components.ProductOwnerChecker;
import one.two.three.entity.DetailUserInfo;
import one.two.three.entity.OrderDetails;
import one.two.three.entity.Product;
import one.two.three.entity.SimpleOrder;
import one.two.three.entity.User;
import one.two.three.service.IcommentService;
import one.two.three.service.IdetailUserInfo;
import one.two.three.service.IorderDetail;
import one.two.three.service.IproductService;
import one.two.three.service.IserviceUser;
import one.two.three.service.IsimpleOrderService;

@Controller
public class MainController {

	@Autowired
	IserviceUser userservice;
	@Autowired
	IproductService prodService;
	@Autowired
	UserValidator validator;
	@Autowired
	ProductValidator pValid;
	@Autowired
	DetailUserInfoValidator DUIValidator;
	@Autowired
	IcommentService commService;
	@Autowired
	IorderDetail ODS;
	@Autowired
	IsimpleOrderService SOservice;
	@Autowired
	IdetailUserInfo DUIService;
	@Autowired
	ProductOwnerChecker pochecker;
	@Autowired
	ActivUserOrderDetailChecker activOrderChecker;
	

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {

		return "index";
	}

	@RequestMapping(value = "registration", method = RequestMethod.GET)
	public String registrationPage(Model model) {
		
		model.addAttribute("user", new User());
		return "registration";
	}

	@RequestMapping(value = "userpage", method = RequestMethod.GET)
	public String userPage(Model model) {
//		UserDetails u = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		String name = u.getUsername();
//		model.addAttribute("user", name);
		model.addAttribute("product", new Product());
		return "userpage";
	}

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String loginPage() {

		return "loginpage";
	}

	@RequestMapping(value = "admin", method = RequestMethod.GET)
	public String adminPage() {

		return "admin";
	}
	
	@RequestMapping(value = "userReg", method = RequestMethod.POST)
	public String registrationProcess(
			@ModelAttribute("user") @Valid User user, 
			BindingResult result, 
			@RequestParam("userPhoto") MultipartFile photo,
			@RequestParam("contactInformation") String contactInfo) {
		if(result.hasErrors()) {
			return "registration";
		}
		userservice.saveUserWithInfo(photo, contactInfo, user);
		return "redirect:/";
	}
	
	@RequestMapping(value = "addProduct", method = RequestMethod.POST)
	public String addProduct(@ModelAttribute("product") @Valid Product prod,
			BindingResult result,
			@RequestParam("prodPicture") MultipartFile picture,
			@RequestParam("inf") String inf) {
		
		if(result.hasErrors()) {
			return "userpage";
		}
		User contextUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int userID = contextUser.getId();
		prodService.saveProductWithInfo(picture, inf, prod, userID);
		
		return "userpage";
	}
	
	@RequestMapping(value = "showAllProducts", method = RequestMethod.GET)
	public String allProducts(Model model) {
		List<Product> products = new ArrayList<>();
		products = prodService.prodWithPicture();
		model.addAttribute("prodWithPicture", products);
		
		
		return "productpege";
	}
	
	@RequestMapping(value = "showUser", method = RequestMethod.GET)
	public String wachs(Model model) {
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getDetails();
		model.addAttribute("pers", user);
		
		return "userpage";
	}
	
	@RequestMapping(value = "product-{id}", method = RequestMethod.GET)
	public String prodByID(Model model, @PathVariable("id") int id) {
		Product product = prodService.findByIdWithPicture(id);
		Product product2 = prodService.findByIdWithComment(id);
		model.addAttribute("prod", product);
		model.addAttribute("prodComent", product2);
		return "oneproduct";
	}
	
	@RequestMapping(value = "addComment", method = RequestMethod.POST)
	public String addComent(@RequestParam("productId") int id, @RequestParam("commentText") String commentText) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(user.getId() == prodService.productWithOwnerById(id).getOwner().getId()) {
			return "comentDenaed";
		}
		commService.addCommentToProduct(id, commentText);
		
		return "redirect:showAllProducts";
	}
	
	@RequestMapping(value = "showMyProduct", method = RequestMethod.GET)
	public String usersProducts(Model model) {
		User contextUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<Product> usersProduct = prodService.thisUsersProduct(contextUser.getId());
		model.addAttribute("userproducts", usersProduct);
		
		return "userProduct";
	}
	
	@RequestMapping(value = "setpicture", method = RequestMethod.POST)
	public String addPicture(@RequestParam("picture") MultipartFile picture, @RequestParam("prodId") int id) {
		prodService.setPictureToProduct(picture, id);
		
		return "redirect:showMyProduct";
	}
	
	@RequestMapping(value = "myProd-{id}", method = RequestMethod.GET)
	public String userProductByID(Model model, @PathVariable("id") int id) {
		Product productANDcoments = prodService.findByIdWithComment(id);
		model.addAttribute("productWithComents", productANDcoments);
		Product productANDpictures = prodService.findByIdWithPicture(id);
		model.addAttribute("productWithPictures", productANDpictures);
		
		return "userProductDetails";
	}
	
	@RequestMapping(value = "createOrder", method = RequestMethod.GET)
	public String createUseOrder() {
		User contextUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int user_id = contextUser.getId();
		if(activOrderChecker.CheckActiveOrder(user_id)) {
			System.out.println("user have activ order");
			return "redirect:showAllProducts";
		}
		ODS.createOrderDetails(user_id);
		
		return "redirect:showAllProducts";
	}
	
	@RequestMapping(value = "toBox", method = RequestMethod.POST)
	public String customerBoxOperation(@RequestParam("howmany") int number, @RequestParam("product_id") int product_id) {
		User contextUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int user_id = contextUser.getId();
		if(pochecker.checkOwner(user_id, product_id)) {
			return "buyProcessDenied";
		}
		SOservice.createSimpleOrder(number, product_id, user_id);
		
		return "redirect:showAllProducts";
	}
	
	@RequestMapping(value = "wachOrderList", method = RequestMethod.GET)
	public String customerOrderList(Model model) {
		User contextUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int user_id = contextUser.getId();
		Set<SimpleOrder> productSet = new HashSet<>(ODS.findUserActiveOrderDetail(user_id).getSimpleOrders()) ;
		model.addAttribute("productSet", productSet);
		
		return "userActivOrder";
	}
	
	@RequestMapping(value = "orderHistory", method = RequestMethod.GET)
	public String userOrderHistory(Model model) {
		User contextUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int user_id = contextUser.getId();
		DetailUserInfo detailUserInfo = DUIService.finduserOrderHistory(user_id);
		Map<String, OrderDetails> historyMap = detailUserInfo.getHistory();
		model.addAttribute("mapa", historyMap);
		System.out.println(historyMap);
		return "history";
	}
	
	@RequestMapping(value = "order-{id}", method = RequestMethod.GET)
	public String userOrderDetail(Model model, @PathVariable("id") int id) {
		OrderDetails orderDetails = ODS.findUserOrderDetailById(id);
		model.addAttribute("ordDet", orderDetails);
		return "historyDetail";
	}
	
	@RequestMapping(value = "confirmOrder", method = RequestMethod.GET)
	public String confirmUserOrder() {
		User contextUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int user_id = contextUser.getId();
		ODS.confirmUserOrder(user_id);
		
		return "redirect:showAllProducts";
	}
	
	@RequestMapping(value = "removeProductFromOrder", method = RequestMethod.POST)
	public String removeProductFromOrder(@RequestParam("remove") int simOrd_id) {
		SOservice.removeFromOrderList(simOrd_id);
		
		return "redirect:wachOrderList";
	}
	
	@RequestMapping(value = "deleteOrder", method = RequestMethod.GET)
	public String deleteAllOrder() {
		User contextUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int user_id = contextUser.getId();
		ODS.deleteOrder(user_id);
		
		return "redirect:showAllProducts";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	////     slygbovi clasy            ////////////////////////////////
	@InitBinder(value="User")
	public void binderValidUser(WebDataBinder binder) {
		binder.addValidators(validator);
	}
	
	@InitBinder(value="Product")
	public void binderValidProduct(WebDataBinder binder) {
		binder.addValidators(pValid);
	}
	
	@InitBinder(value="DetailUserInfo")
	public void binderDUIValid(WebDataBinder binder) {
		binder.addValidators(DUIValidator);
	}
}

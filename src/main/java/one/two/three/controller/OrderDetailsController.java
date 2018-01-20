package one.two.three.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import one.two.three.components.ActivUserOrderDetailChecker;
import one.two.three.components.TotalSumCalculator;
import one.two.three.entity.SimpleOrder;
import one.two.three.entity.User;
import one.two.three.service.IorderDetail;

@Controller
public class OrderDetailsController {
	
	@Autowired
	private IorderDetail orderDetailsService;
	@Autowired
	private ActivUserOrderDetailChecker activOrderChecker;
	@Autowired
	private TotalSumCalculator tsc;
	
	private int thisUserId() {
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int user_id = user.getId();
		return user_id;
	}
	
	@RequestMapping(value = "usr-createOrder", method = RequestMethod.GET)
	public String createUseOrder() {
		if(activOrderChecker.CheckActiveOrder(thisUserId())) {
			return "redirect:usr-showAllProducts";
		}
		orderDetailsService.createOrderDetails(thisUserId());
		return "redirect:usr-showAllProducts";
	}
	
	@RequestMapping(value = "usr-wachOrderList", method = RequestMethod.GET)
	public String customerOrderList(Model model) {
		if(!activOrderChecker.CheckActiveOrder(thisUserId())) {
			model.addAttribute("noActive", true);
			model.addAttribute("summa", 0);
			
			return "userActivOrder";
		}
		Set<SimpleOrder> productSet = orderDetailsService.findUserActiveOrderDetail(thisUserId()).getSimpleOrders();
		int suma = tsc.findSumm(productSet);
		model.addAttribute("productSet", productSet);
		model.addAttribute("summa", suma);
		
		return "userActivOrder";
	}
		
	@RequestMapping(value = "usr-confirmOrder", method = RequestMethod.GET)
	public String confirmUserOrder() {
		if(activOrderChecker.CheckActiveOrder(thisUserId())) {
			orderDetailsService.confirmUserOrder(thisUserId());
			return "redirect:usr-showAllProducts";
		}
		return "redirect:usr-showAllProducts";
	}
	
	@RequestMapping(value = "usr-deleteOrder", method = RequestMethod.GET)
	public String deleteAllOrder() {
		if (activOrderChecker.CheckActiveOrder(thisUserId())) {
			orderDetailsService.deleteOrder(thisUserId());
			return "redirect:usr-showAllProducts";
		}

		return "redirect:usr-showAllProducts";
	}

}

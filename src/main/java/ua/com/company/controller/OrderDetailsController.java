package ua.com.company.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ua.com.company.components.ActivUserOrderDetailChecker;
import ua.com.company.components.TotalSumCalculator;
import ua.com.company.entity.SimpleOrder;
import ua.com.company.entity.User;
import ua.com.company.service.IorderDetailService;

@Controller
public class OrderDetailsController {

	@Autowired
	private IorderDetailService orderDetailsService;
	@Autowired
	private ActivUserOrderDetailChecker activOrderChecker;
	@Autowired
	private TotalSumCalculator sumCalculator;

	private int thisUserId() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		return user.getId();
	}

	@RequestMapping(value = "usr-createOrder", method = RequestMethod.GET)
	public String createUseOrder() {
		if (activOrderChecker.checkActiveOrder(thisUserId())) {
			return "redirect:usr-showAllProducts";
		}
		orderDetailsService.createOrderDetails(thisUserId());
		return "redirect:usr-showAllProducts";
	}

	@RequestMapping(value = "usr-wachOrderList", method = RequestMethod.GET)
	public String customerOrderList(Model model) {
		if (!activOrderChecker.checkActiveOrder(thisUserId())) {
			model.addAttribute("noActive", true);
			model.addAttribute("summa", 0);

			return "userActivOrder";
		}
		Set<SimpleOrder> productSet = orderDetailsService.findUserActiveOrderDetail(thisUserId()).getSimpleOrders();
		int sum = sumCalculator.findSum(productSet);
		model.addAttribute("productSet", productSet);
		model.addAttribute("summa", sum);

		return "userActivOrder";
	}

	@RequestMapping(value = "usr-confirmOrder", method = RequestMethod.GET)
	public String confirmUserOrder() {
		if (activOrderChecker.checkActiveOrder(thisUserId())) {
			orderDetailsService.confirmUserOrder(thisUserId());
			return "redirect:usr-showAllProducts";
		}
		return "redirect:usr-showAllProducts";
	}

	@RequestMapping(value = "usr-deleteOrder", method = RequestMethod.GET)
	public String deleteAllOrder() {
		if (activOrderChecker.checkActiveOrder(thisUserId())) {
			orderDetailsService.deleteOrder(thisUserId());
			return "redirect:usr-showAllProducts";
		}

		return "redirect:usr-showAllProducts";
	}

}

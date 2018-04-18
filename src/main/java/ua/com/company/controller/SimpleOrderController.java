package ua.com.company.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ua.com.company.components.ProductOwnerChecker;
import ua.com.company.components.TotalSumCalculator;
import ua.com.company.entity.SimpleOrder;
import ua.com.company.entity.User;
import ua.com.company.service.IsimpleOrderService;

@Controller
public class SimpleOrderController {
	
	@Autowired
	private IsimpleOrderService simpleOrderService;
	@Autowired
	private ProductOwnerChecker prodOwnerChecker;
	@Autowired
	private TotalSumCalculator tsc;
	
	private int thisUserId() {
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return user.getId();
	}
	
	@RequestMapping(value = "usr-order-{id}", method = RequestMethod.GET)
	public String userOrderDetail(Model model, @PathVariable("id") int id) {
		List<SimpleOrder> simplOrdList = simpleOrderService.findByorderDetailsId(id);
		int sum = tsc.findSum(simplOrdList);
		model.addAttribute("ordDet", simplOrdList);
		model.addAttribute("summa", sum);
		
		return "historyDetail";
	}
	
	@RequestMapping(value = "usr-toBox", method = RequestMethod.POST)
	public String customerBoxOperation(@RequestParam("howmany") int number, @RequestParam("product_id") int product_id) {
		if(prodOwnerChecker.checkOwner(thisUserId(), product_id)) {
			return "buyProcessDenied";
		}
		simpleOrderService.createSimpleOrder(number, product_id, thisUserId());
		
		return "redirect:usr-showAllProducts";
	}
	
	@RequestMapping(value = "usr-removeProductFromOrder", method = RequestMethod.POST)
	public String removeProductFromOrder(@RequestParam("remove") int simOrd_id) {
		simpleOrderService.removeFromOrderList(simOrd_id);
		
		return "redirect:usr-wachOrderList";
	}

}

package ua.com.company.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.com.company.entity.OrderDetails;
import ua.com.company.service.IorderDetailService;

@Component
public class ActivUserOrderDetailChecker {
	
	@Autowired
	private IorderDetailService odService;
	
	public boolean checkActiveOrder(int user_id) {
		OrderDetails orderDetails = odService.findActiveUserOrdrForComponent(user_id);
		if(orderDetails == null) {
			return false;
		}
		return true;
	}

}

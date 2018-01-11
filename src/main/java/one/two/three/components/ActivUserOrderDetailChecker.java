package one.two.three.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import one.two.three.entity.OrderDetails;
import one.two.three.service.IorderDetail;

@Component
public class ActivUserOrderDetailChecker {
	
	@Autowired
	private IorderDetail odService;
	
	public boolean CheckActiveOrder(int user_id) {
		OrderDetails orderDetails = odService.findActiveUserOrdrForComponent(user_id);
		if(orderDetails == null) {
			return false;
		}
		return true;
	}

}

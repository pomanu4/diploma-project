package one.two.three.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import one.two.three.entity.Product;
import one.two.three.entity.User;
import one.two.three.service.IproductService;

@Component
public class ProductOwnerChecker {
	
	@Autowired
	private IproductService prodServoce;
	
	public boolean checkOwner(int owner_id, int product_id) {
		Product product = prodServoce.productWithOwnerById(product_id);
		User user = product.getOwner();
		if(user.getId() == owner_id) {
			return true;
		}
		return false;
	}

}

package ua.com.company.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.com.company.entity.Product;
import ua.com.company.entity.User;
import ua.com.company.service.IproductService;

@Component
public class ProductOwnerChecker {

	@Autowired
	private IproductService prodServoce;

	public boolean checkOwner(int owner_id, int product_id) {
		Product product = prodServoce.productWithOwnerById(product_id);
		User user = product.getOwner();
		if (user.getId() == owner_id) {
			return true;
		}
		return false;
	}

}

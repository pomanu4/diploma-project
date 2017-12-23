package one.two.three.service.servisImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import one.two.three.DAO.IsimpleOrderDAO;
import one.two.three.entity.OrderDetails;
import one.two.three.entity.Product;
import one.two.three.entity.SimpleOrder;
import one.two.three.service.IorderDetail;
import one.two.three.service.IproductService;
import one.two.three.service.IserviceUser;
import one.two.three.service.IsimpleOrderService;

@Service
@Transactional
public class SimpleOrderService implements IsimpleOrderService {
	
	@Autowired
	IsimpleOrderDAO soDAO;
	@Autowired
	IproductService prodService;
	@Autowired
	IserviceUser userService;
	@Autowired
	IorderDetail ordDetService;

	@Override
	public void saveSimpleOrder(SimpleOrder simpleOrder) {
		
	}

	@Override
	public void createSimpleOrder(int number, int product_id, int user_id) {
		SimpleOrder simpleOrder = new SimpleOrder(number);
		Product product = prodService.productWithOwnerById(product_id);
		product.addSimpleOrder(simpleOrder);
		OrderDetails orderDetails = ordDetService.findUserActiveOrderDetail(user_id);
		orderDetails.addSimpleOrder(simpleOrder);
		soDAO.save(simpleOrder);
	}

	@Override
	public void removeFromOrderList(int simOrd_id) {
		SimpleOrder simpleOrder = soDAO.findOne(simOrd_id);
		simpleOrder.setProduct(null);
		simpleOrder.setOrderDetails(null);
		soDAO.delete(simpleOrder);
	}
	
	

}

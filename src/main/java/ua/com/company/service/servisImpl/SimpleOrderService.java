package ua.com.company.service.servisImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.com.company.dao.IsimpleOrderDAO;
import ua.com.company.entity.OrderDetails;
import ua.com.company.entity.Product;
import ua.com.company.entity.SimpleOrder;
import ua.com.company.service.IorderDetailService;
import ua.com.company.service.IproductService;
import ua.com.company.service.IsimpleOrderService;

@Service
@Transactional
public class SimpleOrderService implements IsimpleOrderService {

	@Autowired
	private IsimpleOrderDAO simpleOrderDAO;
	@Autowired
	private IproductService prodService;
	@Autowired
	private IorderDetailService ordDetService;

	@Override
	public void saveSimpleOrder(SimpleOrder simpleOrder) {
		simpleOrderDAO.save(simpleOrder);
	}

	@Override
	public void createSimpleOrder(int number, int product_id, int user_id) {
		SimpleOrder simpleOrder = new SimpleOrder(number);
		Product product = prodService.productWithOwnerById(product_id);
		product.addSimpleOrder(simpleOrder);
		OrderDetails orderDetails = ordDetService.findUserActiveOrderDetail(user_id);
		orderDetails.addSimpleOrder(simpleOrder);
		simpleOrderDAO.save(simpleOrder);
	}

	@Override
	public void removeFromOrderList(int simOrd_id) {
		SimpleOrder simpleOrder = simpleOrderDAO.findOne(simOrd_id);
		simpleOrder.setProduct(null);
		simpleOrder.setOrderDetails(null);
		simpleOrderDAO.delete(simpleOrder);
	}

	@Override
	public List<SimpleOrder> findByorderDetailsId(int ordDetId) {
		List<SimpleOrder> soList = simpleOrderDAO.findOneByOrderDetailsId(ordDetId);
		return soList;
	}

}

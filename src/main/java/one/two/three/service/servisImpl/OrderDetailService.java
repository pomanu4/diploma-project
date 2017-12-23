package one.two.three.service.servisImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import one.two.three.DAO.OrderDetailDAO;
import one.two.three.entity.DetailUserInfo;
import one.two.three.entity.OrderDetails;
import one.two.three.entity.OrderStatus;
import one.two.three.entity.SimpleOrder;
import one.two.three.entity.User;
import one.two.three.service.IdetailUserInfo;
import one.two.three.service.IorderDetail;
import one.two.three.service.IserviceUser;

@Service
@Transactional
public class OrderDetailService implements IorderDetail {
	@Autowired
	OrderDetailDAO odDAO;
	@Autowired
	IserviceUser userService;
	@Autowired
	IdetailUserInfo duiService;

	@Override
	public void saveOrderDetail(OrderDetails orderDetails) {
		odDAO.save(orderDetails);
	}

	@Override
	public void createOrderDetails(int user_id) {
		String dateANDtime = new SimpleDateFormat("hh:mm--dd-MM-YYYY").format(new Date());
		User user = userService.findOneById(user_id);
		DetailUserInfo dui = user.getUserInfo();
		OrderDetails orderDetails = new OrderDetails();
		orderDetails.addUserInfo(dateANDtime, dui);
		orderDetails.setDateOfOrder(dateANDtime);
		odDAO.save(orderDetails);
	}

	@Override
	public OrderDetails findUserActiveOrderDetail(int user_id) {
		OrderDetails orderDetails = odDAO.findActiveOrderDetails(user_id);
		return orderDetails;
	}

	@Override
	public OrderDetails findUserOrderDetailById(int od_id) {
		OrderDetails orderDetails = odDAO.findOrderDetailsById(od_id);
		return orderDetails;
	}

	@Override
	public void confirmUserOrder(int user_id) {
		OrderDetails orderDetails = odDAO.findActiveOrderDetails(user_id);
		orderDetails.setStatus(OrderStatus.CONFIRMED);
		for(SimpleOrder so : orderDetails.getSimpleOrders()) {
			int so_number = so.getHowMany();
			int prod_number = so.getProduct().getHowMany();
			so.getProduct().setHowMany(prod_number-so_number);
		}
		saveOrderDetail(orderDetails);
	}

	@Override
	public OrderDetails findActiveUserOrdrForComponent(int user_id) {
		OrderDetails orderDetails = odDAO.getActivOrderToConfirm(user_id);
		return orderDetails;
	}

	@Override
	public void deleteOrder(int user_id) {
		int delete_id =  odDAO.getActivOrderToConfirm(user_id).getId();
		OrderDetails orderDetails = odDAO.findOrderDetailsById(delete_id);
		orderDetails.setUserInfo(null);
		for(SimpleOrder simOrd : orderDetails.getSimpleOrders()) {
			simOrd.setProduct(null);
		}
		DetailUserInfo detailUserInfo = duiService.finduserOrderHistory(user_id);
		detailUserInfo.getHistory().remove(orderDetails.getDateOfOrder());
		odDAO.delete(delete_id);
		
	}
	
	
	

}

package ua.com.company.service;

import ua.com.company.entity.OrderDetails;

public interface IorderDetailService {

	public void saveOrderDetail(OrderDetails orderDetails);

	public void createOrderDetails(int user_id);

	public OrderDetails findUserActiveOrderDetail(int user_id);

	public OrderDetails findUserOrderDetailById(int od_id);

	public void confirmUserOrder(int user_id);

	public OrderDetails findActiveUserOrdrForComponent(int user_id);

	public void deleteOrder(int order_id);

}

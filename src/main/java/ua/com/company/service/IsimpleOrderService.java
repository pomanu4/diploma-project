package ua.com.company.service;

import java.util.List;

import ua.com.company.entity.SimpleOrder;

public interface IsimpleOrderService {

	public void saveSimpleOrder(SimpleOrder simpleOrder);

	public void createSimpleOrder(int number, int product_id, int user_id);

	public void removeFromOrderList(int simOrd_id);

	public List<SimpleOrder> findByorderDetailsId(int ordDetId);

}

package one.two.three.service;

import one.two.three.entity.SimpleOrder;

public interface IsimpleOrderService {
	
	public void saveSimpleOrder(SimpleOrder simpleOrder);
	
	public void createSimpleOrder(int number, int product_id, int user_id);
	
	public void removeFromOrderList(int simOrd_id);
	
	

}

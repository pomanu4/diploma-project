package one.two.three.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import one.two.three.entity.OrderDetails;

public interface IorderDetailDAO extends JpaRepository<OrderDetails, Integer> {
	
	@Query("SELECT o FROM OrderDetails o LEFT JOIN FETCH o.simpleOrders s LEFT JOIN FETCH s.product "
			+ "WHERE o.status='ACTIVE' AND userInfo_id=(:id)")
	public OrderDetails findActiveOrderDetails(@Param("id") Integer id);
	
	@Query("SELECT o FROM OrderDetails o LEFT JOIN FETCH o.simpleOrders s LEFT JOIN FETCH s.product"
			+ " WHERE  o.id=(:id)")
	public OrderDetails findOrderDetailsById(@Param("id") Integer id);
	
	@Query("SELECT o FROM OrderDetails o WHERE o.status='ACTIVE' AND userInfo_id=(:id)")
	public OrderDetails getActivOrderToConfirm(@Param("id") Integer id);
	
}

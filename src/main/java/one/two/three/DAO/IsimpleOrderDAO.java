package one.two.three.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import one.two.three.entity.SimpleOrder;

public interface IsimpleOrderDAO extends JpaRepository<SimpleOrder, Integer> {
	
	@Query("SELECT c FROM SimpleOrder c LEFT JOIN FETCH c.product p LEFT JOIN FETCH p.owner o LEFT JOIN FETCH o.userInfo WHERE "
			+ "orderDetails_id=(:id)")
	public List<SimpleOrder> findOneByorderDetailsId(@Param("id") Integer od_id); 

}

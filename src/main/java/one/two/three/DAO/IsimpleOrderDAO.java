package one.two.three.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import one.two.three.entity.SimpleOrder;

public interface IsimpleOrderDAO extends JpaRepository<SimpleOrder, Integer> {

}

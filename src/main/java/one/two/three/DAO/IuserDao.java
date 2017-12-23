package one.two.three.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import one.two.three.entity.User;

public interface IuserDao extends JpaRepository<User, Integer> {
	
	@Query("from User u where u.email = :email")
	public User findByEmail(@Param("email") String email);
	
	@Query("SELECT u FROM User u LEFT JOIN FETCH u.products WHERE u.id = (:userid)")
	public List<User> searchByID(@Param("userid") Integer id);
	
}

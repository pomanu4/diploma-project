package ua.com.company.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ua.com.company.entity.User;

public interface IuserDAO extends JpaRepository<User, Integer> {
	
	@Query("from User u where u.email = :email")
	public User findByEmail(@Param("email") String email);
	
	@Query("SELECT u FROM User u LEFT JOIN FETCH u.products WHERE u.id = (:userid)")
	public List<User> searchUserWithProductById(@Param("userid") Integer id);
	
	@Query("SELECT u FROM User u LEFT JOIN FETCH u.userInfo WHERE u.id = (:userid)")
	public List<User> searchUserWithInfoByID(@Param("userid") Integer id);
	
	@Query("SELECT u FROM User u LEFT JOIN FETCH u.userInfo WHERE u.name LIKE (:namePattern)")
	public List<User> searchUserWithInfoBynamePattern(@Param("namePattern") String namePattern);
	
	@Query("SELECT u FROM User u LEFT JOIN FETCH u.userInfo WHERE u.name=(:name)")
	public List<User> searchUserWithInfoByName(@Param("name") String name);
	
	@Query("SELECT u FROM User u LEFT JOIN FETCH u.userInfo WHERE u.email = (:email)")
	public List<User> searchUserWithInfoByEmail(@Param("email") String email);
	
	@Query("SELECT u FROM User u LEFT JOIN FETCH u.myComments WHERE u.id=(:userId)")
	public User oneUserWithComment(@Param("userId") Integer userId);
	
	@Query("SELECT u FROM User u LEFT JOIN FETCH u.myComplains WHERE u.id=(:userId)")
	public User oneUserWithComplain(@Param("userId") Integer userId );
	
	
}

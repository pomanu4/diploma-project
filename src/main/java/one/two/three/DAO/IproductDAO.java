package one.two.three.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import one.two.three.entity.Product;

public interface IproductDAO extends JpaRepository<Product, Integer>{
	
	@Query("SELECT p FROM Product p  LEFT JOIN FETCH p.pictures ")
	public List<Product> productsWithPicture();
	
	@Query("SELECT p FROM Product p  LEFT JOIN FETCH p.pictures WHERE p.id = (:id) ")
	public Product oneProductWithPicture(@Param("id") Integer id);

	@Query("SELECT p FROM Product p  LEFT JOIN FETCH p.comments WHERE p.id = (:id) ")
	public Product oneProductWithComment(@Param("id") Integer id);
	
	@Query("SELECT p FROM Product p LEFT JOIN FETCH p.comments WHERE owner_id=(:id) ")
	public List<Product> thisUserProducts(@Param("id") Integer id);
	
	@Query("SELECT p FROM Product p LEFT JOIN FETCH p.owner o LEFT JOIN FETCH o.userInfo WHERE p.id=(:id) ")
	public Product productWithOwnerById(@Param("id") Integer id);
	
	@Query("SELECT p FROM Product p  LEFT JOIN FETCH p.comments c LEFT JOIN FETCH c.user WHERE p.id = (:id) ")
	public Product oneProductWithCommentandComUser(@Param("id") Integer id);
	
	@Query("SELECT p FROM Product p  WHERE p.productName LIKE (:pattern)")
	public List<Product> findByProductNamePattern(@Param("pattern") String pattern);
	
}

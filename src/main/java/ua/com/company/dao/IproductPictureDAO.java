package ua.com.company.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ua.com.company.entity.ProductPicture;

public interface IproductPictureDAO extends JpaRepository<ProductPicture, Integer> {

	@Query("SELECT p FROM ProductPicture p LEFT JOIN FETCH p.productToShow WHERE p.id=(:pictureId)")
	public ProductPicture onyPictureWithProductById(@Param("pictureId") Integer pictureId);

}

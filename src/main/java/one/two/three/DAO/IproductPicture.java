package one.two.three.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import one.two.three.entity.ProductPicture;

public interface IproductPicture extends JpaRepository<ProductPicture, Integer> {

}

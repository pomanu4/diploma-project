package one.two.three.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import one.two.three.entity.Product;

public interface IproductService {
	
	public void saveProduct(Product product);
	
	public List<Product>prodWithPicture();
	
	public void productToDB(Product product, int user_id);
	
	public Product findByIdWithPicture(int id);
	
	public Product findByIdWithComment(int id);
	
	public List<Product> thisUsersProduct(int id);
	
	public void setPictureToProduct(MultipartFile file, int product_id);
	
	public void saveProductWithInfo(MultipartFile file, String info, Product product, int owner_id);
	
	public Product productWithOwnerById(int id);

}

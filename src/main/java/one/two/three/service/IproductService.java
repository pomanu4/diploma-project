package one.two.three.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import one.two.three.entity.Product;

public interface IproductService {
	
	public Product productWithAllInfo(int podId);
	
	
	
	
	public void saveProduct(Product product);
	
	public List<Product>prodWithPicture();
	
	public void productToDB(Product product, int user_id);
	
	
	///////////
	public Product findByIdWithPicture(int id);
	//////////
	
	
	
	public Product findByIdWithComment(int id);
	
	public List<Product> thisUsersProducts(int id);
	
	public void setPictureToProduct(MultipartFile file, int product_id);
	
	public void saveProductWithInfo(MultipartFile file, Product product, int owner_id);
	
	
	
	/////////
	public Product productWithOwnerById(int id);
	/////////
	
	
	
	
	public void addProductQantity(int prod_id, int newQantity);
	
	public void setNewPrice(int prod_id, int newPrice);
	
	
	
	//////////
	public Product oneProdWithCommentandComUser(int product_id);
	/////////
	
	
	
	
	public List<Product> findByProductNamePattern(String pattern);
	
	public List<Product> sortByName(List<Product> productList);
	
	public  List<Product> sortByPrice(List<Product> productList);
	
	public void setNewProductName(String newName, int prodId);
	
	public void setNewInfoToProduct(int prodId, String newInfo);

}

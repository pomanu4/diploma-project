package one.two.three.service.servisImpl;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import one.two.three.DAO.IproductDAO;
import one.two.three.components.DefoultPhotoHandler;
import one.two.three.entity.Product;
import one.two.three.entity.ProductPicture;
import one.two.three.entity.User;
import one.two.three.service.IproductService;
import one.two.three.service.IuserService;

@PropertySource("classpath:productPhoto.properties")
@Service
@Transactional
public class ProductServiceImpl implements IproductService {

	@Autowired
	private IproductDAO productDAO;
	@Autowired
	private IuserService userService;
	@Autowired
	private DefoultPhotoHandler DPH;
	@Autowired
	private Environment env;
	
	
	
	

	@Override
	public Product productWithAllInfo(int podId) {
		Product product = productDAO.productWithAllFields(podId);
		
		return product;
	}

	@Override
	public void saveProduct(Product product) {
		productDAO.save(product);
	}

	@Override
	public List<Product> prodWithPicture() {

		return productDAO.findAll();
	}

	@Override
	public void productToDB(Product product, int user_id) {
		User user = userService.searchUserWithProductById(user_id);
		product.addOwner(user);
		productDAO.save(product);
	}

	
	/////////
	@Override
	public Product findByIdWithPicture(int id) {
		Product product = productDAO.oneProductWithPicture(id);
		return product;
	}
	///////
	
	
	
	

	@Override
	public Product findByIdWithComment(int id) {
		Product product = productDAO.oneProductWithComment(id);
		return product;
	}

	@Override
	public List<Product> thisUsersProducts(int id) {
		List<Product> userProduct = productDAO.thisUserProducts(id);
		return userProduct;
	}

	@Override
	public void setPictureToProduct(MultipartFile file, int product_id) {
		String filePath = env.getProperty("product.photo.path");
		String webFilePath = env.getProperty("product.web.path");
		File prodPhoto = new File(filePath + File.separator + file.getOriginalFilename());
		try {
			file.transferTo(prodPhoto);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		ProductPicture prodPicture = new ProductPicture(webFilePath+file.getOriginalFilename());
		Product product = productDAO.oneProductWithPicture(product_id);
		prodPicture.setProductToShow(product);
		product.getPictures().add(prodPicture);
		saveProduct(product);
	}

	@Override
	public void saveProductWithInfo(MultipartFile file, Product product, int owner_id) {
		ProductPicture productPicture = new ProductPicture();
		productPicture.setPicturePath(DPH.productDefoultPhoto(file));
		product.addPicture(productPicture);
		productToDB(product, owner_id);
	}

	
	
	////////////
	@Override
	public Product productWithOwnerById(int id) {
		return productDAO.productWithOwnerById(id);
	}
	/////////
	
	
	
	
	////////////
	@Override
	public Product oneProdWithCommentandComUser(int product_id) {
		Product product = productDAO.oneProductWithCommentandComUser(product_id);
		return product;
	}
	////////////
	
	
	
	
	
	

	@Override
	public void addProductQantity(int prod_id, int newQantity) {
		Product product = productDAO.findOne(prod_id);
		product.setHowMany(newQantity);
		saveProduct(product);
	}

	@Override
	public void setNewPrice(int prod_id, int newPrice) {
		Product product = productDAO.findOne(prod_id);
		product.setPrice(newPrice);
		saveProduct(product);
		
	}

	@Override
	public List<Product> findByProductNamePattern(String pattern) {
		String namePattern = "%" + pattern + "%";
		List<Product> prodList = productDAO.findByProductNamePattern(namePattern);
		return prodList;
	}

	@Override
	public List<Product> sortByName(List<Product> productList) {
		List<Product> pl = productList;
		Collections.sort(pl, new Comparator<Product>() {

			@Override
			public int compare(Product p1, Product p2) {
				int val = p1.getProductName().compareTo(p2.getProductName());
				return val;
			}
		});
		return pl;
	}

	@Override
	public List<Product> sortByPrice(List<Product> productList) {
		List<Product> pl = productList;
		Collections.sort(pl, new Comparator<Product>() {

			@Override
			public int compare(Product p1, Product p2) {
				int val = ((Integer)p1.getPrice()).compareTo((Integer)p2.getPrice());
				return val;
			}
		});
		return pl;
	}

	@Override
	public void setNewProductName(String newName, int prodId) {
		Product product = productDAO.findOne(prodId);
		product.setProductName(newName);
		saveProduct(product);
	}

	@Override
	public void setNewInfoToProduct(int prodId, String newInfo) {
		Product product = productDAO.findOne(prodId);
		product.setAbout(newInfo);
		saveProduct(product);
	}
	
	
	

}

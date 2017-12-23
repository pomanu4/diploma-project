package one.two.three.service.servisImpl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import one.two.three.DAO.IproductDAO;
import one.two.three.DAO.IuserDao;
import one.two.three.components.DefoultPhotoHandler;
import one.two.three.entity.Product;
import one.two.three.entity.ProductPicture;
import one.two.three.entity.User;
import one.two.three.service.IproductService;

@PropertySource("classpath:productPhoto.properties")
@Service
@Transactional
public class ProductServiceImpl implements IproductService {

	@Autowired
	IproductDAO prodDAO;
	@Autowired
	IuserDao userDAO;
	@Autowired
	DefoultPhotoHandler DPH;
	@Autowired
	Environment env;

	@Override
	public void saveProduct(Product product) {
		prodDAO.save(product);
	}

	@Override
	public List<Product> prodWithPicture() {

		return prodDAO.findAll();
	}

	@Override
	public void productToDB(Product product, int user_id) {
		User user = userDAO.searchByID(user_id).get(0);
		product.addOwner(user);
		prodDAO.save(product);
	}

	@Override
	public Product findByIdWithPicture(int id) {
		Product product = prodDAO.oneProductWithPicture(id);
		return product;
	}

	@Override
	public Product findByIdWithComment(int id) {
		Product product = prodDAO.oneProductWithComment(id);
		return product;
	}

	@Override
	public List<Product> thisUsersProduct(int id) {
		List<Product> userProduct = prodDAO.thisUserProductwithpictures(id);
		return userProduct;
	}

	@Override
	public void setPictureToProduct(MultipartFile file, int product_id) {
		String filePath = env.getProperty("product.photo.path");
		File prodPhoto = new File(filePath + File.separator + file.getOriginalFilename());
		try {
			file.transferTo(prodPhoto);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		ProductPicture prodPicture = new ProductPicture(prodPhoto.getAbsolutePath());
		Product product = prodDAO.oneProductWithPicture(product_id);
		prodPicture.setProductToShow(product);
		product.getPictures().add(prodPicture);
		saveProduct(product);
	}

	@Override
	public void saveProductWithInfo(MultipartFile file, String info, Product product, int owner_id) {
		ProductPicture productPicture = new ProductPicture();
		productPicture.setPicturePath(DPH.productDefoultPhoto(file).getAbsolutePath());
		product.setAbout(info);
		product.addPicture(productPicture);
		productToDB(product, owner_id);
	}

	@Override
	public Product productWithOwnerById(int id) {
		return prodDAO.productWithOwnerById(id);
	}
	
	

}

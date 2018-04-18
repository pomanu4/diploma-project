package ua.com.company.service.servisImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.com.company.components.DefoultPhotoHandler;
import ua.com.company.dao.IproductPictureDAO;
import ua.com.company.entity.ProductPicture;
import ua.com.company.service.IpictureService;

@Service
@Transactional
public class ProductPictureService implements IpictureService {

	@Autowired
	private IproductPictureDAO pictureDAO;
	@Autowired
	private DefoultPhotoHandler defaultPH;

	@Override
	public ProductPicture findOneById(int pic_id) {
		ProductPicture prodPicture = pictureDAO.findOne(pic_id);
		return prodPicture;
	}

	@Override
	public void deletePicture(ProductPicture picture) {
		pictureDAO.delete(picture);

	}

	@Override
	public void deleteById(int pic_id) {
		ProductPicture prodPicture = pictureDAO.onyPictureWithProductById(pic_id);
		prodPicture.getProductToShow().getPictures().remove(prodPicture);
		prodPicture.setProductToShow(null);
		deletePicture(prodPicture);
	}

	@Override
	public void setDefault(int pictureId) {
		String newPhoto = defaultPH.productDefaultPhotoPath();
		ProductPicture prodPicture = findOneById(pictureId);
		prodPicture.setPicturePath(newPhoto);
		pictureDAO.save(prodPicture);
	}

}

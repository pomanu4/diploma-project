package one.two.three.service.servisImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import one.two.three.DAO.IproductPictureDAO;
import one.two.three.components.DefoultPhotoHandler;
import one.two.three.entity.ProductPicture;
import one.two.three.service.IpictureService;

@Service
@Transactional
public class ProductPictureService implements IpictureService {
	
	@Autowired
	private IproductPictureDAO pictureDAO;
	@Autowired
	private DefoultPhotoHandler DPH;

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
		String newPhoto = DPH.productDefaultPhotoPath();
		ProductPicture prodPicture = findOneById(pictureId);
		prodPicture.setPicturePath(newPhoto);
		pictureDAO.save(prodPicture);
	}
	
	

}

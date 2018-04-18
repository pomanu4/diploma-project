package ua.com.company.service;

import ua.com.company.entity.ProductPicture;

public interface IpictureService {

	public ProductPicture findOneById(int pic_id);

	public void deletePicture(ProductPicture picture);

	public void deleteById(int pic_id);

	public void setDefault(int pictureId);

}

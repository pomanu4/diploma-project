package one.two.three.components;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@PropertySource({"classpath:userphoto.properties", "classpath:productPhoto.properties"})
public class DefoultPhotoHandler {
	
	@Autowired
	Environment env;
	
	public File usrDefoultPhoto(MultipartFile file) {
		String path = env.getProperty("userphoto.sorce.folder");
		File userPhoto;
		if (file.getSize() == 0) {
			userPhoto = new File(path + File.separator + "simple.png");
			return userPhoto;
		} else {
			userPhoto = new File(path + File.separator + file.getOriginalFilename());
			try {
				file.transferTo(userPhoto);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			return userPhoto;
		}
	}
	
	public File productDefoultPhoto(MultipartFile file) {
		String path = env.getProperty("product.photo.path");
		File productPhoto;
		if (file.getSize() == 0) {
			productPhoto = new File(path + File.separator + "uncnowproduct.png");
			return productPhoto;
		} else {
			productPhoto = new File(path + File.separator + file.getOriginalFilename());
			try {
				file.transferTo(productPhoto);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			return productPhoto;
		}
	}

}

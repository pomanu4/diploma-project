package ua.com.company.components;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@PropertySource({ "classpath:userphoto.properties", "classpath:productPhoto.properties" })
public class DefoultPhotoHandler {

	@Autowired
	private Environment env;

	public String userDefoultPhoto(MultipartFile file) {
		String path = env.getProperty("userphoto.source.folder");
		String webFilePath = env.getProperty("userphoto.web.source") + file.getOriginalFilename();
		File userPhoto;
		if (file.getSize() == 0) {
			userPhoto = new File(path + File.separator + "simple.png");
			String webFile = env.getProperty("userphoto.web.source") + "simple.png";
			return webFile;
		} else {
			userPhoto = new File(path + File.separator + file.getOriginalFilename());
			try {
				file.transferTo(userPhoto);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			return webFilePath;
		}
	}

	public String productDefoultPhoto(MultipartFile file) {
		String path = env.getProperty("product.photo.path");
		String webFilePath = env.getProperty("product.web.path") + file.getOriginalFilename();
		File productPhoto;
		if (file.getSize() == 0) {
			productPhoto = new File(path + File.separator + "uncnowproduct.png");
			String filePath = env.getProperty("product.web.path") + "noimage.png";
			return filePath;
		} else {
			productPhoto = new File(path + File.separator + file.getOriginalFilename());
			try {
				file.transferTo(productPhoto);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			return webFilePath;
		}
	}

	public String defaultPhotoPath() {
		String path = env.getProperty("userphoto.web.source") + "simple.png";
		return path;
	}

	public String productDefaultPhotoPath() {
		String path = env.getProperty("product.web.path") + "noimage.png";
		return path;
	}

}

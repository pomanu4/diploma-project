package ua.com.company.components;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:userphoto.properties")
public class DeletePhotoChecker {
	@Autowired
	private Environment userEnv;

	public boolean checkPhotoBeforeDelete(File file) {
		String fileToCheck = userEnv.getProperty("userphoto.source.folder") + File.separator + "simple.png";
		if (file.getAbsolutePath().equals(fileToCheck)) {
			return false;
		}
		return true;
	}

}

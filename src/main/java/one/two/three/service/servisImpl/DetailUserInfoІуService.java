package one.two.three.service.servisImpl;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import one.two.three.DAO.IdetailUserInfoDAO;
import one.two.three.components.DeletePhotoChecker;
import one.two.three.entity.DetailUserInfo;
import one.two.three.service.IdetailUserInfo;

@Service
@Transactional
@PropertySource("classpath:userphoto.properties")
public class DetailUserInfoІуService implements IdetailUserInfo {
	
	@Autowired
	private IdetailUserInfoDAO duiDAO;
	@Autowired
	private Environment env;
	@Autowired
	private DeletePhotoChecker photoChecker;

	@Override
	public void saveUserDetailInfo(DetailUserInfo detailUserInfo) {
		duiDAO.save(detailUserInfo);
		
	}

	@Override
	public DetailUserInfo finduserOrderHistory(int user_id) {
		DetailUserInfo detailUserInfo = duiDAO.findUserOrderHistory(user_id);
		return detailUserInfo;
	}

	@Override
	public DetailUserInfo findOneById(int user_id) {
		DetailUserInfo detailUserInfo = duiDAO.findOne(user_id);
		return detailUserInfo;
	}

	@Override
	public void addNewPhoto(MultipartFile photo, int user_id) {
		String filePath = env.getProperty("userphoto.sorce.folder");
		String webFile = env.getProperty("userphoto.web.sorce");
		DetailUserInfo detailUserInfo = duiDAO.findOne(user_id);
		String[] splittedPath = detailUserInfo.getFoto().split("/");
		String fileName = splittedPath[splittedPath.length - 1];
		File fileToDelete = new File(filePath + File.separator + fileName);
		File newFile = new File(filePath + File.separator + photo.getOriginalFilename());
		try {
			if (photoChecker.checkPhotoBeforeDelete(fileToDelete)) {
				fileToDelete.delete();
			}

		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
		try {
			photo.transferTo(newFile);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		detailUserInfo.setFoto(webFile + photo.getOriginalFilename());

		duiDAO.save(detailUserInfo);
	}

	@Override
	public void addNewName(String newName, int user_id) {
		DetailUserInfo detailUserInfo = duiDAO.findOne(user_id);
		detailUserInfo.getUser().setName(newName);
		duiDAO.save(detailUserInfo);
	}

	@Override
	public void addNewContact(String contact, int user_id) {
		DetailUserInfo detailUserInfo = duiDAO.findOne(user_id);
		detailUserInfo.setContactInformation(contact);
		duiDAO.save(detailUserInfo);
		
	}
	
	

}

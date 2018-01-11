package one.two.three.service;

import org.springframework.web.multipart.MultipartFile;

import one.two.three.entity.DetailUserInfo;

public interface IdetailUserInfo {
	
	public void saveUserDetailInfo(DetailUserInfo detailUserInfo);
	
	public DetailUserInfo finduserOrderHistory(int user_id);
	
	public DetailUserInfo findOneById(int user_id);
	
	public void addNewPhoto(MultipartFile photo, int user_id);
	
	public void addNewName(String newName, int user_id);
	
	public void addNewContact(String contact, int user_id);
	
}

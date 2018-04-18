package ua.com.company.service;

import org.springframework.web.multipart.MultipartFile;

import ua.com.company.entity.DetailUserInfo;

public interface IdetailUserInfoService {

	public void saveUserDetailInfo(DetailUserInfo detailUserInfo);

	public DetailUserInfo finduserOrderHistory(int user_id);

	public DetailUserInfo findOneById(int user_id);

	public void addNewPhoto(MultipartFile photo, int user_id);

	public void addNewName(String newName, int user_id);

	public void addNewContact(String contact, int user_id);

}

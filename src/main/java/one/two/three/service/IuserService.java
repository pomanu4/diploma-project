package one.two.three.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import one.two.three.entity.User;

public interface IuserService {
		
	public User findByEmail(String email);
	
	public User searchUserWithProductById(int id);
	
	public void saveUser(User user);
	
	public void saveUserWithInfo(MultipartFile file, String contact, User user);
	
	public User findOneById(int id);
	
	public List<User> findAllUsers();
	
	public User oneUserWithInfo(int usr_id);
	
	public void setDefoultPhoto(int user_id);
	
	public void setNewImail(String imail, int user_id);
	
	public void setNewPassword(String password, int user_id);
	
	public void setNewRole(int user_id, String role);
	
	public void bolckUserAccount(int user_id, boolean value);
	
	public List<User> findUserByNamePattern(String namePattern);
	
	public List<User> findUserByName(String name);
	
	public List<User> getUserWithInfoByEmail(String email);
	
	public User oneUserWithComment(int userId);;
	
	public User oneUserWithComplain(int userId);
	
}

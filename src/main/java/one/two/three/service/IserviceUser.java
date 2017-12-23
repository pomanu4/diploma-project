package one.two.three.service;

import org.springframework.web.multipart.MultipartFile;

import one.two.three.entity.User;

public interface IserviceUser {
		
	public User findByEmail(String email);
	
	public User searchById(int id);
	
	public void saveUser(User user);
	
	public void saveUserWithInfo(MultipartFile file, String contact, User user);
	
	public User findOneById(int id);
	
}

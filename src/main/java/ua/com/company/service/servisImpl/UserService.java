package ua.com.company.service.servisImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import ua.com.company.components.DefoultPhotoHandler;
import ua.com.company.dao.IuserDAO;
import ua.com.company.entity.Authurity;
import ua.com.company.entity.DetailUserInfo;
import ua.com.company.entity.User;
import ua.com.company.service.IuserMailSender;
import ua.com.company.service.IuserService;

@Service("userDetailServiceImpl")
@Transactional
public class UserService implements IuserService, UserDetailsService {

	@Autowired
	private IuserDAO userDao;
	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private DefoultPhotoHandler defaultPH;
	@Autowired
	private IuserMailSender sender;

	@Override
	public User findByEmail(String email) {
		User user = userDao.findByEmail(email);

		return user;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		return findByEmail(email);
	}

	@Override
	public User searchUserWithProductById(int id) {
		User user = userDao.searchUserWithProductById(id).get(0);
		return user;
	}

	@Override
	public void saveUser(User user) {
		String codedPassword = encoder.encode(user.getPassword().trim());
		user.setPassword(codedPassword);
		userDao.save(user);
	}

	@Override
	public void saveUserWithInfo(MultipartFile file, String contact, User user) {
		String date = new SimpleDateFormat("dd MM YYYY").format(new Date());
		DetailUserInfo userInfo = new DetailUserInfo();
		userInfo.setSince(date);
		userInfo.setContactInformation(contact);
		userInfo.setFoto(defaultPH.userDefoultPhoto(file));
		user.addUserInfo(userInfo);
		saveUser(user);
		sender.sendLetter(user);
	}

	@Override
	public User findOneById(int id) {
		User user = userDao.findOne(id);
		return user;
	}

	@Override
	public List<User> findAllUsers() {
		List<User> userList = userDao.findAll();
		return userList;
	}

	@Override
	public User oneUserWithInfo(int usr_id) {
		User user = null;
		if (userDao.searchUserWithInfoByID(usr_id).size() == 0) {
			return user;
		}
		user = userDao.searchUserWithInfoByID(usr_id).get(0);

		return user;
	}

	@Override
	public void setDefoultPhoto(int user_id) {
		String defoultPhoto = defaultPH.defaultPhotoPath();
		User user = userDao.searchUserWithInfoByID(user_id).get(0);
		user.getUserInfo().setFoto(defoultPhoto);
		userDao.save(user);
	}

	@Override
	public void setNewEmail(String imail, int user_id) {
		User user = userDao.findOne(user_id);
		user.setEmail(imail);
		userDao.save(user);

	}

	@Override
	public void setNewPassword(String password, int user_id) {
		User user = userDao.findOne(user_id);
		user.setPassword(password);
		saveUser(user);
	}

	@Override
	public void setNewRole(int user_id, String role) {
		User user = userDao.findOne(user_id);
		user.setRole(Authurity.valueOf(role));
		userDao.save(user);
	}

	@Override
	public void bolckUserAccount(int user_id, boolean value) {
		User user = userDao.getOne(user_id);
		user.setAccountNonLocked(value);
		userDao.save(user);
	}

	@Override
	public List<User> findUserByNamePattern(String namePattern) {
		String pattern = "%" + namePattern + "%";
		List<User> userList = userDao.searchUserWithInfoBynamePattern(pattern);
		return userList;
	}

	@Override
	public List<User> findUserByName(String name) {
		List<User> userList = userDao.searchUserWithInfoByName(name);
		return userList;
	}

	@Override
	public List<User> getUserWithInfoByEmail(String email) {
		List<User> userList = userDao.searchUserWithInfoByEmail(email);
		return userList;
	}

	@Override
	public User oneUserWithComment(int userId) {
		User user = userDao.oneUserWithComment(userId);
		return user;
	}

	@Override
	public User oneUserWithComplain(int userId) {
		User user = userDao.oneUserWithComplain(userId);
		return user;
	}

}

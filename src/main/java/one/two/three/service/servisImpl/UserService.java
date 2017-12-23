package one.two.three.service.servisImpl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import one.two.three.DAO.IuserDao;
import one.two.three.components.DefoultPhotoHandler;
import one.two.three.entity.DetailUserInfo;
import one.two.three.entity.User;
import one.two.three.service.IserviceUser;
import one.two.three.service.IuserMailSender;

@Service("userDetailServiceImpl")
@Transactional
public class UserService implements IserviceUser, UserDetailsService{
	
	@Autowired
	IuserDao userDao;
	@Autowired
	PasswordEncoder encoder;
	@Autowired
	DefoultPhotoHandler DPH;
	@Autowired
	IuserMailSender sender;

	@Override
	public User findByEmail(String email) {
		User user =  userDao.findByEmail(email);
		
		return user;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		return findByEmail(email);
	}

	@Override
	public User searchById(int id) {
		User user = userDao.searchByID(id).get(0);
		return user;
	}

	@Override
	public void saveUser(User user) {
		String codedPassword = encoder.encode(user.getPassword());
		user.setPassword(codedPassword);
		userDao.save(user);
	}

	@Override
	public void saveUserWithInfo(MultipartFile file, String contact, User user) {
		String date = new SimpleDateFormat("dd MM YYYY").format(new Date());
		DetailUserInfo userInfo = new DetailUserInfo();
		userInfo.setSince(date);
		userInfo.setContactInformation(contact);
		userInfo.setFoto(DPH.usrDefoultPhoto(file).getAbsolutePath());
		user.addUserInfo(userInfo);
		saveUser(user);
		// sender.sendLetter(user);
	}

	@Override
	public User findOneById(int id) {
		User user = userDao.findOne(id);
		return user;
	}

	
	
}

package one.two.three.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import one.two.three.entity.User;
import one.two.three.service.IserviceUser;

@Component
public class UserValidator implements Validator {
	
	@Autowired
	IserviceUser userService;

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(User.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		User user = (User)target;
		
		if(user.getName().equals(null) || user.getName().trim().equals("")){
			errors.rejectValue("name", "User.name.blank", "name is uncorrect");
		}
		if(!checkIdentity(user.getEmail())) {
			errors.rejectValue("email", "mess from property file", "user with this email is already exist");
		}
		
	}
	
	private boolean checkIdentity(String email) {
		User user = userService.findByEmail(email);
		if (user == null) {
			return true;
		} else {
			return false;
		}
	}
}

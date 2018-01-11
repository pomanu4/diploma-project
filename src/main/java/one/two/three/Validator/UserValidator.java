package one.two.three.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import one.two.three.entity.User;
import one.two.three.service.IuserService;

@Component
public class UserValidator implements Validator {
	
	@Autowired
	private IuserService userService;

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(User.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		User user = (User)target;
		
		if(user.getName().equals(null) || user.getName().trim().equals("")){
			errors.rejectValue("name", "", "this field can not be empty");
		}
		
		if(user.getName().trim().length()==1){
			errors.rejectValue("name", "", "add more characters to yor name ");
		}
		
		if(user.getPassword().trim().length()<4) {
			errors.rejectValue("password", "", "add more characters to yor password ");
		}
		
		if(checkIdentity(user.getEmail())) {
			errors.rejectValue("email", "", "user with this email is already exist");
		}
		
	}
	
	private boolean checkIdentity(String email) {
		User user = userService.findByEmail(email);
		if (user == null) {
			return false;
		} else {
			return true;
		}
	}
}

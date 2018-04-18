package ua.com.company.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ua.com.company.entity.DetailUserInfo;

@Component
public class DetailUserInfoValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {

		return clazz.equals(DetailUserInfo.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
	}

}

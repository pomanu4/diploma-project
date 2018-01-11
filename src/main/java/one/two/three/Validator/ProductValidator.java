package one.two.three.Validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import one.two.three.entity.Product;

@Component
public class ProductValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {

		return clazz.equals(Product.class);
	}

	@Override
	public void validate(Object target, Errors errors) {

		Product product = (Product) target;

		if (product.getHowMany() <= 0) {
			errors.rejectValue("howMany", "", "this field can not be 0 or negative");
		}

		if (product.getPrice() <= 0) {
			errors.rejectValue("price", "", "this field can not be 0 or negative");
		}

		if (product.getAbout().trim().equals(null) || product.getAbout().trim().equals("")) {
			errors.rejectValue("about", "", "would be nice to have some information about your product");
		}

	}

}

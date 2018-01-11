package one.two.three.components;

import java.util.ArrayList;
import java.util.List;

import one.two.three.entity.Product;

public class ProductListWrapper {
	
	private List<Product> pl = new ArrayList<>();

	public ProductListWrapper() {
	}

	public List<Product> getPl() {
		return pl;
	}

	public void setPl(List<Product> pl) {
		this.pl = pl;
	}
	
	
	

}

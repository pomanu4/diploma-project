package ua.com.company.components;

import java.util.ArrayList;
import java.util.List;

import ua.com.company.entity.Product;

public class ProductListWrapper {

	 List<Product> prodlist = new ArrayList<>();

	public ProductListWrapper() {
	}

	public List<Product> getProdlist() {
		return prodlist;
	}

	public void setProdlist(List<Product> prodlist) {
		this.prodlist = prodlist;
	}

	

}

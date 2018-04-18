package ua.com.company.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ProductPicture {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String picturePath;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Product productToShow;

	public ProductPicture() {
	}

	public ProductPicture(String picturePath) {
		this.picturePath = picturePath;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPicturePath() {
		return picturePath;
	}

	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}

	public Product getProductToShow() {
		return productToShow;
	}

	public void setProductToShow(Product productToShow) {
		this.productToShow = productToShow;
	}

	@Override
	public String toString() {
		return "ProductPicture [id=" + id + ", picturePath=" + picturePath + "]";
	}

}

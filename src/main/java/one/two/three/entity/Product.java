package one.two.three.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotBlank(message="this field can not be empty")
	private String productName;
	
	@NotNull(message="this field can not be empty")
	private int howMany;
	
	@NotNull(message="this field can not be empty")
	private int price;
	
	private String about;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="owner_id")
	private User owner;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "productToComent")
	private List<Comment> comments = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "productToShow")
	private List<ProductPicture> pictures = new ArrayList<>();
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="product")
	private List<SimpleOrder> simpleOrder = new ArrayList<>();

	public Product() {
	}

	public Product(String productName, int howMany, int prise, String about) {
		this.productName = productName;
		this.howMany = howMany;
		this.price = prise;
		this.about = about;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getHowMany() {
		return howMany;
	}

	public void setHowMany(int howMany) {
		this.howMany = howMany;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<ProductPicture> getPictures() {
		return pictures;
	}

	public void setPictures(List<ProductPicture> pictures) {
		this.pictures = pictures;
	}

	public int getPrice() {
		return price;
	}
	
	public void addOwner(User user) {
		user.getProducts().add(this);
		this.setOwner(user);
	}

	public void setPrice(int prise) {
		this.price = prise;
	}
	
	public void addComment(Comment comment) {
		comment.setProductToComent(this);
		this.getComments().add(comment);
	}
	
	public void addPicture(ProductPicture picture) {
		picture.setProductToShow(this);
		this.getPictures().add(picture);
	}

	public List<SimpleOrder> getSimpleOrder() {
		return simpleOrder;
	}

	public void setSimpleOrder(List<SimpleOrder> simpleOrder) {
		this.simpleOrder = simpleOrder;
	}
	
	public void addSimpleOrder(SimpleOrder simpleOrder) {
		simpleOrder.setProduct(this);
		this.getSimpleOrder().add(simpleOrder);
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", productName=" + productName + ", howMany=" + howMany + ", price=" + price
				+ ", about=" + about + "]";
	}

}

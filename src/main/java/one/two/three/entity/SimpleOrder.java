package one.two.three.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class SimpleOrder {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private int howMany;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private OrderDetails orderDetails;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private Product product;

	public SimpleOrder() {
	}

	public SimpleOrder(int howMany) {
		this.howMany = howMany;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getHowMany() {
		return howMany;
	}

	public void setHowMany(int howMany) {
		this.howMany = howMany;
	}

	public OrderDetails getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(OrderDetails orderDetails) {
		this.orderDetails = orderDetails;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + howMany;
		result = prime * result + id;
		return result;
	}

	@Override
	public String toString() {
		return "SimpleOrder [id=" + id + ", howMany=" + howMany + ", product=" + product + "]";
	}

}

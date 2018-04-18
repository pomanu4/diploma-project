package ua.com.company.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class OrderDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private DetailUserInfo userInfo;

	private String dateOfOrder;

	@Enumerated(EnumType.STRING)
	private OrderStatus status = OrderStatus.ACTIVE;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy="orderDetails")
	private Set<SimpleOrder> simpleOrders = new HashSet<>();

	public OrderDetails() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public DetailUserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(DetailUserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public String getDateOfOrder() {
		return dateOfOrder;
	}

	public void setDateOfOrder(String dateOfOrder) {
		this.dateOfOrder = dateOfOrder;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public Set<SimpleOrder> getSimpleOrders() {
		return simpleOrders;
	}

	public void setSimpleOrders(Set<SimpleOrder> simpleOrders) {
		this.simpleOrders = simpleOrders;
	}

	public void addUserInfo(String key, DetailUserInfo info) {
		info.getHistory().put(key, this);
		this.setUserInfo(info);
	}

	public void addSimpleOrder(SimpleOrder simpleOrder) {
		simpleOrder.setOrderDetails(this);
		this.getSimpleOrders().add(simpleOrder);
	}

	@Override
	public String toString() {
		return "OrderDetails [id=" + id + ", dateOfOrder=" + dateOfOrder + ", status=" + status + "]";
	}
}

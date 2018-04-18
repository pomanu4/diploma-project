package ua.com.company.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String comment;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Product productToComent;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private User user;

	public Comment() {
	}

	public Comment(String comment) {
		this.comment = comment;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Product getProductToComent() {
		return productToComent;
	}

	public void setProductToComent(Product productToComent) {
		this.productToComent = productToComent;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", comment=" + comment + "]";
	}

}

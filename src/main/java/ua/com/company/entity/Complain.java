package ua.com.company.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Complain {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String date;

	private String message;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private User author;

	public Complain() {
		super();
	}

	public Complain(int id, String message) {
		super();
		this.id = id;
		this.message = message;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User user) {
		this.author = user;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Complain [id=" + id + ", date=" + date + ", message=" + message + "]";
	}

}

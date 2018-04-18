package ua.com.company.entity;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class DetailUserInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String since;

	private String contactInformation;

	private String foto;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userInfo")
	private Map<String, OrderDetails> history = new HashMap<>();

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private User user;

	public DetailUserInfo() {
	}

	public DetailUserInfo(String since, String contactInformation, String foto) {
		super();
		this.since = since;
		this.contactInformation = contactInformation;
		this.foto = foto;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSince() {
		return since;
	}

	public void setSince(String since) {
		this.since = since;
	}

	public String getContactInformation() {
		return contactInformation;
	}

	public void setContactInformation(String contactInformation) {
		this.contactInformation = contactInformation;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public Map<String, OrderDetails> getHistory() {
		return history;
	}

	public void setHistory(Map<String, OrderDetails> history) {
		this.history = history;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "DetailUserInfo [id=" + id + ", since=" + since + ", contactInformation=" + contactInformation
				+ ", foto=" + foto + "]";
	}
}

package ua.com.company.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;

	@NotNull
	@NotEmpty(message = "email can not be empty field")
	@Email(message = "wrong email adress")
	private String email;

	@NotBlank(message = "enter somthing else please")
	@Size(min = 4, message = "password has to be not shorter then 4 signs ")
	private String password;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy="owner")
	private List<Product> products = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	private List<Comment> myComments = new ArrayList<>();

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy="user")
	private DetailUserInfo userInfo;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "author")
	private List<Complain> myComplains = new ArrayList<>();

	@Enumerated(EnumType.STRING)
	private Authurity role = Authurity.ROLE_USER;

	private boolean accountNonExpired = true;
	private boolean accountNonLocked = true;
	private boolean credentialsNonExpired = true;
	private boolean enabled = true;

	public User() {
	}

	public User(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Authurity getRole() {
		return role;
	}

	public DetailUserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(DetailUserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public void setRole(Authurity role) {
		this.role = role;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public void addProduct(Product product) {
		product.setOwner(this);
		this.getProducts().add(product);
	}

	public void addUserInfo(DetailUserInfo info) {
		info.setUser(this);
		this.setUserInfo(info);
	}

	public void addMyComment(Comment comment) {
		comment.setUser(this);
		this.getMyComment().add(comment);
	}

	public List<Comment> getMyComment() {
		return myComments;
	}

	public void setMyComment(List<Comment> myComment) {
		this.myComments = myComment;
	}

	public List<Complain> getMyComplains() {
		return myComplains;
	}

	public void setMyComplains(List<Complain> myComplains) {
		this.myComplains = myComplains;
	}

	public void addComplain(Complain complain) {
		complain.setAuthor(this);
		this.getMyComplains().add(complain);
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", email=" + email + ", password=" + password + "]";
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		ArrayList<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
		simpleGrantedAuthorities.add(new SimpleGrantedAuthority(role.name()));
		return simpleGrantedAuthorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return name;
	}

	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

}

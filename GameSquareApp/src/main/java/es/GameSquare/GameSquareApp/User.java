package es.GameSquare.GameSquareApp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
//import java.util.Calendar;
//import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Null;


@Entity
public class User {
	
	//Attributes
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected long id;
	
	protected String userName;
	protected String password;
	protected String regDate;
	protected String email = "email";
	
	@OneToMany
	protected List<Comment> comments;
	@OneToOne
	private GameDeveloper developer;
	@OneToOne
	private ModDeveloper modder;
	
	
	//To take the date
	//DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	//Date date = Calendar.getInstance().getTime();
	
	
	
	//Constructors
	public User(String userName, String password) {
		this.userName = userName;
		this.password = password;
		this.regDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
		this.comments = new ArrayList<>();	
	}
	
	public User() {
		
	}
	
// Getters & Setters
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public int getComments() {
		return comments.size();
	}
	public void addToCommentList(Comment comment) {
		this.comments.add(comment);
	}
	public GameDeveloper getDeveloper() {
		return developer;
	}

	public void setDeveloper(GameDeveloper developer) {
		this.developer = developer;
	}

	public ModDeveloper getModder() {
		return modder;
	}

	public void setModder(ModDeveloper modder) {
		this.modder = modder;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public long getId() {
		return id;
	}
	

}

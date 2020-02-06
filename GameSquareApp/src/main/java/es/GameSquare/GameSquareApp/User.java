package es.GameSquare.GameSquareApp;

//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class User {
	
	//Attributes
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String userName;
	private String password;
	private String regDate;
	@OneToMany
	private List<Comment> comments;
	
	/*
	//To take the date
	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	Date date = Calendar.getInstance().getTime();
	*/
	
	//Constructors
	public User(String userName, String password) {
		this.userName = userName;
		this.password = password;
		this.regDate = "Today";
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
	

}

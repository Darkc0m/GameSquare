package es.GameSquare.GameSquareApp;

//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Comment {
	
	//Attributes
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String author;
	private String body;
	private String pubDate;
	private String VidOrModName;
	
	/*
	//To take the date
	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	Date date = Calendar.getInstance().getTime();
	*/
	
	//Constructors
	public Comment(String author, String body, String VidOrModName) {
		this.author = author;
		this.body = body;
		this.VidOrModName = VidOrModName;
		this.pubDate = "Today";
	}
	
	public Comment() {
		
	}
	
	//Getters & Setters
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getPubDate() {
		return pubDate;
	}
	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}
	public String getVidOrModName() {
		return VidOrModName;
	}
	public void setVidOrModName(String vidOrModName) {
		VidOrModName = vidOrModName;
	}

}

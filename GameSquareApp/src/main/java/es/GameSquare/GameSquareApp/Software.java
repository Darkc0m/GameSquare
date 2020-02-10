package es.GameSquare.GameSquareApp;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

@MappedSuperclass
public class Software {
	
	//Attributes
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected long id;
	
	protected String name;
	protected String pubDate;
	protected String genre;
	protected String[] authors;
	protected String description;
	protected String developer;
	@OneToMany
	protected List<Comment> comments;
	
	//Constructs
	public Software(String name, String genre, String description, String developer) {
		this.name = name;
		this.genre = genre;
		this.description = description;
		this.developer = developer;
		this.pubDate = "Today";
		comments = new ArrayList<Comment>();		
	}
	
	public Software() {
		
	}
	
	//Getters & Setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPubDate() {
		return pubDate;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String[] getAuthors() {
		return authors;
	}

	public void setAuthors(String[] authors) {
		this.authors = authors;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDeveloper() {
		return developer;
	}

	public void setDeveloper(String developer) {
		this.developer = developer;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

}


package es.GameSquare.GameSquareApp;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="`mod`")
public class Mod extends Software{
	
	//Attributes
	@ManyToOne
	private Videogame videogame;
	
	//Constructors
	public Mod(String name, String genre, String description, String developer) {
		super(name, genre, description, developer);
	}
	
	public Mod() {
		
	}
  
	//Getters & Setters
	public Videogame getVideogame() {
		return videogame;
	}

	public void setVideogame(Videogame videogame) {
		this.videogame = videogame;
	}

}

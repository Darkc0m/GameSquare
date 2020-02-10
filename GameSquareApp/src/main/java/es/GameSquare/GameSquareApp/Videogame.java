package es.GameSquare.GameSquareApp;

import javax.persistence.Entity;

@Entity
public class Videogame extends Software{
	
	//Constructors
	public Videogame(String name, String genre, String description, String developer) {
		super(name, genre, description, developer);
	}
	
	public Videogame() {
		
	}

}

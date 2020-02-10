package es.GameSquare.GameSquareApp;

import javax.persistence.Entity;

@Entity
public class Mod extends Software{
	
	//Attributes
	private String game;
	
	//Constructors
	public Mod(String name, String genre, String description, String developer,String game) {
		super(name, genre, description, developer);
		this.game = game;
	}
	
	public Mod() {
		
	}
  
	//Getters & Setters
	public String getGame() {
		return game;
	}

	public void setGame(String game) {
		this.game = game;
	}

}

package es.GameSquare.GameSquareApp;

import javax.persistence.Entity;

@Entity
public class Mod extends Software{
	
	//Attributes
	private String game;
	
	//Constructors
	public Mod(String game) {
		super();
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

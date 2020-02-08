package es.GameSquare.GameSquareApp;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.OneToMany;

public class GameDeveloper extends User{
	
	//Attributes
	@OneToMany
	List<Videogame> games;
	
	//Constructors
	public GameDeveloper() {
		super();
		games = new ArrayList<>();	
	}

	//Getters & Setters
	public List<Videogame> getGames() {
		return games;
	}

	public void setGames(List<Videogame> games) {
		this.games = games;
	}

}

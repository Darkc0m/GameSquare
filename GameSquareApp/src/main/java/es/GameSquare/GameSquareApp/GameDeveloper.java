package es.GameSquare.GameSquareApp;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class GameDeveloper extends User{
	
	//Attributes
	@OneToMany
	private List<Videogame> games;
	
	//Constructors
	public GameDeveloper(String userName, String password) {
		super(userName, password);
		games = new ArrayList<>();	
	}
	
	public GameDeveloper() {
		
	}

	//Getters & Setters
	public List<Videogame> getGames() {
		return games;
	}

	public void setGames(List<Videogame> games) {
		this.games = games;
	}
	
	public void addGame(Videogame game) {
		games.add(game);
	}

}

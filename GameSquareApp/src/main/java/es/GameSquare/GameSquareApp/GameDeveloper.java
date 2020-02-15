package es.GameSquare.GameSquareApp;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class GameDeveloper{
	
	//Attributes
	@Id
	private long id;
	@OneToMany
	private List<Videogame> games;

	
	//Constructors
	public GameDeveloper(long id) {
		this.id = id;
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

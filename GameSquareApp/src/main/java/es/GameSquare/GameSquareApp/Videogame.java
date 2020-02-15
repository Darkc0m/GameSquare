package es.GameSquare.GameSquareApp;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Videogame extends Software{
	
	@OneToMany(mappedBy="videogame")
	private List<Mod> mods;
	
	//Constructors
	public Videogame(String name, String genre, String description, String developer) {
		super(name, genre, description, developer);
		mods = new ArrayList<Mod>();
	}
	
	public Videogame() {
		
	}

	public List<Mod> getMods() {
		return mods;
	}

	public void setMods(List<Mod> mods) {
		this.mods = mods;
	}
	
}

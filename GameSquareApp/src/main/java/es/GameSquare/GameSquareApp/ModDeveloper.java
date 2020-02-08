package es.GameSquare.GameSquareApp;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.OneToMany;

public class ModDeveloper extends User{
	
	//Attributes
	@OneToMany
	List<Mod> mods;
	
	//Constructors
	public ModDeveloper() {
		super();
		mods = new ArrayList<>();	
	}

	//Getters & Setters
	public List<Mod> getMods() {
		return mods;
	}

	public void setGames(List<Mod> mods) {
		this.mods = mods;
	}

}



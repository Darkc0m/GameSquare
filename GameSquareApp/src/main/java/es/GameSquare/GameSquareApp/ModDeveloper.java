package es.GameSquare.GameSquareApp;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class ModDeveloper extends User{
	
	//Attributes
	@OneToMany
	List<Mod> mods;
	
	//Constructors
	public ModDeveloper(String userName, String password) {
		super(userName, password);
		mods = new ArrayList<>();	
	}
	
	public ModDeveloper() {
		
	}

	//Getters & Setters
	public List<Mod> getMods() {
		return mods;
	}

	public void setGames(List<Mod> mods) {
		this.mods = mods;
	}
	
	public void addMod(Mod mod) {
		mods.add(mod);
	}

}



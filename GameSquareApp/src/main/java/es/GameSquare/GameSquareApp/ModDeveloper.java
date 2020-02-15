package es.GameSquare.GameSquareApp;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class ModDeveloper{
	
	//Attributes
	@OneToMany
	List<Mod> mods;
	@Id
	private long id;
	
	//Constructors
	public ModDeveloper(long id) {
		this.id = id;
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



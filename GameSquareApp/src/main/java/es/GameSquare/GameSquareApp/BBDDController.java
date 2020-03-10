package es.GameSquare.GameSquareApp;

//import java.util.List;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;

@Controller
public class BBDDController {
	
	@Autowired
	private UsersRepository UsersRpo;
	
	@Autowired
	private CommentsRepository CommentsRpo;
	
	@Autowired
	private VideogamesRepository VideogamesRpo;
	
	@Autowired
	private ModsRepository ModsRpo;
	
	@PostConstruct
	public void init() {
		
		User user1 = new User("Alejuandro", new BCryptPasswordEncoder().encode("1"));
		user1.addRole("ROLE_USER");
		GameDeveloper user2 = new GameDeveloper(user1.getId());
		ModDeveloper user3 = new ModDeveloper(user1.getId());
		
		Videogame juego_1 = new Videogame("Nekopara", "SliceofLife", "Kawaii", "Juez");
		
		Comment c1 = new Comment(user1.getUserName(), "Me gusta mucho", juego_1.getName());
		
				
		CommentsRpo.save(c1);
		
		juego_1.getComments().add(c1);
		
		VideogamesRpo.save(juego_1);
		
		Mod m1 = new Mod("El mod 1","H","Un mod to flama","Mayro");
		
		m1.setVideogame(juego_1);
		
		ModsRpo.save(m1);
		
		for(int i = 0; i < 9; i++) {
			user2.addGame(new Videogame("Juego"+"_"+i, "SliceofLife", "Kawaii", "Juez"));
			Mod m = new Mod("x2"+"_"+i, "SliceofLife", "Kawaiix2", "Javi");
			m.setVideogame(juego_1);
			ModsRpo.save(m);
			user3.addMod(m);
		}
		
		
		
		
		
		
		
		VideogamesRpo.save(user2.getGames());
		
		
		
		ModsRpo.save(user3.getMods());
		
		user1.addToCommentList(c1);
		
		UsersRpo.save(user1);
		
		//List<User> users = UsersRpo.findAll();
	}

}

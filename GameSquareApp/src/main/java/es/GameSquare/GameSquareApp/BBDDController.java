package es.GameSquare.GameSquareApp;

//import java.util.List;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
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
		
		User user1 = new User("Alejuandro-Chan", "Comorobarunaloli13");
		GameDeveloper user2 = new GameDeveloper("Juez", "Kimochi");
		ModDeveloper user3 = new ModDeveloper("Javi", "2B9S");
		
		for(int i = 0; i < 10; i++) {
			user2.addGame(new Videogame("Juego"+"_"+i, "SliceofLife", "Kawaii", "Juez"));
			user3.addMod(new Mod("x2"+"_"+i, "SliceofLife", "Kawaiix2", "Javi", "Lolis"));
		}
		
		Comment c1 = new Comment("Alejuandro-Chan", "Pero vaya puto imbécil", "Nekopara");
		
		CommentsRpo.save(c1);
		
		VideogamesRpo.save(user2.getGames());
		
		ModsRpo.save(user3.getMods());
		
		user1.addToCommentList(c1);
		
		UsersRpo.save(user1);
		
		//List<User> users = UsersRpo.findAll();
	}

}

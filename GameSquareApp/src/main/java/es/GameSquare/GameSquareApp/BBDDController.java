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
	
	@PostConstruct
	public void init() {
		
		User user1 = new User("Alejuandro-Chan", "Comorobarunaloli13");
		
		Comment c1 = new Comment("Alejuandro-Chan", "Pero vaya puto imbécil", "Nekopara");
		
		CommentsRpo.save(c1);
		
		user1.addToCommentList(c1);
		
		UsersRpo.save(user1);
		
		//List<User> users = UsersRpo.findAll();
	}
}

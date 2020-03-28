package es.GameSquare.GameSquareApp;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailRestController {
	
	@Autowired
	private MailService notificationService;
	
	@Autowired
	private UsersRepository UsersRpo;
	
	@Autowired
	private VideogamesRepository VideogamesRpo;
	
	@Autowired
	private ModsRepository ModsRpo;
	
	@Autowired
	private ModDeveloperRepository ModdersRpo;
	
	@PostMapping("/publish/p_mod")
	public String publish_mod(HttpServletRequest request, Model model, @RequestParam String name, @RequestParam String genre, @RequestParam String description, @RequestParam String game) {
		String username = request.getUserPrincipal().getName();
		

		Mod mod = new Mod(name, genre, description, username);
		
		Videogame vg = VideogamesRpo.findByName(game);
		
		if(vg == null) {
			model.addAttribute("message", "The game wasn't found. Please try again.");
			model.addAttribute("link", "/publish/mod");
			return "template";
		}
		
		mod.setVideogame(vg);
		
		ModsRpo.save(mod);
		
		User current = UsersRpo.findByUserName(username);
		
		//If modder doesnt exist we create it
		if(current.getModder() == null) {
			ModDeveloper modder = new ModDeveloper(current.getId());
			ModdersRpo.save(modder);
			current.setModder(modder);
			UsersRpo.save(current);
		}
		
		current.getModder().addMod(mod);
		ModdersRpo.save(current.getModder());		
		UsersRpo.save(current);
		
		//Email Service
		String dev = vg.getDeveloper();
		User developer = UsersRpo.findByUserName(dev);
		
		try {
			notificationService.sendEmailMod(developer, username, vg.getName());
		} catch (MailException mailException) {
			System.out.println(mailException);
		}
		
		model.addAttribute("message", "Mod added successfully.");
		model.addAttribute("link", "/mods/"+mod.getId()+"?page=0");
		return "template";
	}

}

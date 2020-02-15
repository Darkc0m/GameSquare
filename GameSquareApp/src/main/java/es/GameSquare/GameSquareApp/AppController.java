package es.GameSquare.GameSquareApp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AppController {
	
	@Autowired
	private UsersRepository UsersRpo;
	
	@Autowired
	private VideogamesRepository VideogamesRpo;
	
	@Autowired
	private ModsRepository ModsRpo;
	
	@Autowired
	private CommentsRepository CommentsRpo;
	
	@GetMapping("/login")
	public String login() {
		return "session";
	}
	
	@PostMapping("/login")
	public String sumbit(HttpSession session, @RequestParam String username, @RequestParam String password) {
		session.setAttribute("username", username);
		session.setAttribute("password", password);
		return "template";
	}
	
	@GetMapping("/")
	public String index(Model model, HttpSession session) {
		
		String username = "Login";
		String user_mapping = "/login";
		
		if(session.getAttributeNames().hasMoreElements()) {
			username = session.getAttribute("username").toString();
			user_mapping = "/users/"+username;
		}
		
		List<Videogame> games = VideogamesRpo.findFirst10ByOrderByPubDateDesc();
		
		
		List<Mod> mods = ModsRpo.findAll(new Sort(new Order(Sort.Direction.ASC, "pubDate")));
		
		model.addAttribute("username", username);
		model.addAttribute("user_mapping", user_mapping);		
		model.addAttribute("games", games);
		model.addAttribute("mods", mods);
		return "index";
	}
	
	@GetMapping("/profile/modify")
	public String modifyProfile(Model model) {
		//model.addAttribute("user", user);
		return "modify_profile";
	}
	
	@PostMapping("/profile/modified")
	public String modifiedProfile(Model model, String username, String password, String email) {
		//Coger el usuario actual 
		/*
		user.setUserName(username);
		user.setPassword(password);
		user.setEmail(email);
		UsersRpo.save(user);
		*/
		return "profile";
	}
	
	@GetMapping("/publish/{software}")
	public String publish(Model model, @PathVariable String software) {
		boolean isMod = false;
		boolean isGame = false;
		switch(software) {
		case "game":
			isGame = true;
			break;
		case "mod":
			isMod = true;
			break;
		}
		model.addAttribute("isMod", isMod);
		model.addAttribute("isGame", isGame);
		return "publish";
	}
	
	@PostMapping("/publish/p_game")
	public String publish_game(Model model, String name, String genre, String description) {
		
		//Videogame vg = new Videogame(name, genre, description);
		//VideogamesRpo.save(vg);
		
		return "publish_game";
	}
	
	@PostMapping("/publish/p_mod")
	public String publish_mod(Model model, String name, String genre, String description, String game) {
		
		Videogame vg = VideogamesRpo.findByName(game);
		
		//Mod mod = new Mod(name, genre, description);
		//ModsRpo.save(mod);
		//vg.getMods().add(mod);
		//VideogamesRpo.save(vg);
		
		return "publish_game";
	}
	
	@GetMapping("/games/{game_id}")
	public String game(Model model, @PathVariable String game_id, @RequestParam int page) {
		
		Videogame videogame = VideogamesRpo.getOne(Long.parseLong(game_id));
		Page<Comment> comments = CommentsRpo.findBySoftwareNameOrderByPubDateDesc(videogame.name, new PageRequest(page,1));
		model.addAttribute("vg", videogame);
		model.addAttribute("comments", comments);
		model.addAttribute("nextPage", game_id + "?page=" + (page + 1));
		model.addAttribute("previousPage", game_id + "?page=" + (page - 1));
		
		return "games";
	}
	
	@PostMapping("/games/sent_comment/{game_id}")
	public String game_comment(Model model, @PathVariable String game_id, String body) {
		
		Videogame videogame = VideogamesRpo.getOne(Long.parseLong(game_id));
		Comment comment = new Comment("Mayro",body, videogame.getName());
		CommentsRpo.save(comment);
		videogame.getComments().add(comment);
		model.addAttribute("software", videogame);
		VideogamesRpo.save(videogame);
		
		return "sent_comment";
	}
	
	@PostMapping("/mods/sent_comment/{mod_id}")
	public String mod_comment(Model model, @PathVariable String mod_id, String body) {
		
		Mod mod = ModsRpo.getOne(Long.parseLong(mod_id));
		Comment comment = new Comment("Mayro",body, mod.getName());
		CommentsRpo.save(comment);
		mod.getComments().add(comment);
		model.addAttribute("software", mod);
		ModsRpo.save(mod);
		
		return "sent_comment";
	}
	
	@GetMapping("/mods/{mod_id}")
	public String mod(Model model, @PathVariable String mod_id, @RequestParam int page) {
		
		Mod mod = ModsRpo.getOne(Long.parseLong(mod_id));
		Page<Comment> comments = CommentsRpo.findBySoftwareNameOrderByPubDateDesc(mod.name, new PageRequest(page,1));
		model.addAttribute("mod", mod);
		model.addAttribute("comments", comments);
		model.addAttribute("nextPage", mod_id + "?page=" + (page + 1));
		model.addAttribute("previousPage", mod_id + "?page=" + (page - 1));
		
		return "mods";
	}
	
	
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "index";
	}
}
	
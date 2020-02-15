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
	private GameDeveloperRepository DevelopersRpo;
	
	@Autowired
	private ModDeveloperRepository ModdersRpo;
	
	@Autowired
	private VideogamesRepository VideogamesRpo;
	
	@Autowired
	private ModsRepository ModsRpo;
	
	@Autowired
	private CommentsRepository CommentsRpo;
	
	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("link", "/login");
		model.addAttribute("action", "Login");
		return "session";
	}
	
	@PostMapping("/login")
	public String sumbit(HttpSession session, Model model, @RequestParam String username, @RequestParam String password) {
		User u = UsersRpo.findByUserName(username);
		
		String message = "Incorrect username or password.";
		if(u == null) {
			
		}			
		else if(u.getUserName().equals(username) && u.getPassword().equals(password)) {
			message = "Succesfully logged as "+username;
				session.setAttribute("username", username);
				session.setAttribute("password", password);		
		}

		model.addAttribute("message", message);
		return "template";
	}
	
	@GetMapping("/register")
	public String register(Model model) {
		
		model.addAttribute("action", "Register");
		model.addAttribute("link", "/register");		
		return "session";
	}
	
	@PostMapping("/register")
	public String register_sumbit(HttpSession session, Model model, @RequestParam String username, @RequestParam String password) {
		User existing_user = UsersRpo.findByUserName(username);
		
		String message = "User succesfully registered!";
		
		if(existing_user == null) {
			User u = new User(username, password);
			UsersRpo.save(u);
		}
		else {
			message = "The username already exists. Try again.";
		}

		model.addAttribute("message", message);
		return "template";
	}
	
	
	@GetMapping("/")
	public String index(Model model, HttpSession session) {
		
		session_params(model, session);
		
		List<Videogame> games = VideogamesRpo.findFirst10ByOrderByPubDateDesc();	
		
		List<Mod> mods = ModsRpo.findFirst10ByOrderByPubDateDesc();		

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
	
	@GetMapping("/profile")
	public String profile(HttpSession session, Model model) {
		User user = UsersRpo.findByUserName(session.getAttribute("username").toString());
		
		if(user == null) {
			model.addAttribute("message", "User not found.");
			return "template";
		}				
		
		model.addAttribute("user", user);
		model.addAttribute("mods", ModsRpo.findByDeveloperOrderByPubDateDesc(user.getUserName()));
		model.addAttribute("games", VideogamesRpo.findByDeveloperOrderByPubDateDesc(user.getUserName()));
		model.addAttribute("total_comments", user.getComments());
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
	public String publish_game(HttpSession session, Model model, @RequestParam String name, @RequestParam String genre, @RequestParam String description) {
		
		String username = session.getAttribute("username").toString();
		
		Videogame vg = new Videogame(name, genre, description, username);
		VideogamesRpo.save(vg);
		
		User current = UsersRpo.findByUserName(username);
		
		//If developer doesnt exist we create it
		if(current.getDeveloper() == null) {
			GameDeveloper dev = new GameDeveloper(current.getId());
			DevelopersRpo.save(dev);
			current.setDeveloper(dev);
			UsersRpo.save(current);
		}
		
		current.getDeveloper().addGame(vg);
		DevelopersRpo.save(current.getDeveloper());		
		UsersRpo.save(current);
		
		model.addAttribute("message", "Game added successfully.");
		return "template";
	}
	
	@PostMapping("/publish/p_mod")
	public String publish_mod(HttpSession session, Model model, @RequestParam String name, @RequestParam String genre, @RequestParam String description, @RequestParam String game) {
		
		String username = session.getAttribute("username").toString();
		

		Mod mod = new Mod(name, genre, description, username);
		//Falta asociar el juego para el que es
		mod.setVideogame(VideogamesRpo.findByName("Nekopara"));
		
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
		
		model.addAttribute("message", "Mod added successfully.");
		return "template";
	}
	
	@GetMapping("/games/{game_id}")
	public String game(HttpSession session, Model model, @PathVariable String game_id, @RequestParam int pageComments, @RequestParam int pageMods) {
		
		Videogame videogame = VideogamesRpo.getOne(Long.parseLong(game_id));
		Page<Comment> comments = CommentsRpo.findByOwnerOrderByPubDateDesc(videogame.getName(), new PageRequest(pageComments,5));
		Page<Mod> mods = ModsRpo.findByVideogameNameOrderByPubDateDesc(videogame.getName(), new PageRequest(pageMods, 5));
		model.addAttribute("vg", videogame);
		model.addAttribute("comments", comments);
		model.addAttribute("mods", mods);
		model.addAttribute("nextPageComments", game_id + "?pageComments=" + (pageComments + 1) + "&pageMods=" + pageMods);
		model.addAttribute("previousPageComments", game_id + "?pageComments=" + (pageComments - 1) + "&pageMods=" + pageMods);
		model.addAttribute("nextPageMods", game_id + "?pageComments=" + pageComments + "&pageMods=" + (pageMods + 1));
		model.addAttribute("previousPageMods", game_id + "?pageComments=" + pageComments + "&pageMods=" + (pageMods - 1));
		model.addAttribute("isLogged", session.getAttribute("username") != null);
		
		model.addAttribute("first_comment_page", !comments.isFirst());
		model.addAttribute("last_comment_page", !comments.isLast());
		model.addAttribute("first_mods_page", !mods.isFirst());
		model.addAttribute("last_mods_page", !mods.isLast());
		
		return "games";
	}
	
	@PostMapping("/{software}/sent_comment/{software_id}")
	public String game_comment(HttpSession session, Model model, @PathVariable String software, @PathVariable String software_id, String body) {
		switch(software) {
			case "games":
				Videogame videogame = VideogamesRpo.getOne(Long.parseLong(software_id));
				Comment comment_game = new Comment(session.getAttribute("username").toString(),body, videogame.getName());
				CommentsRpo.save(comment_game);
				videogame.getComments().add(comment_game);
				model.addAttribute("software", videogame);
				model.addAttribute("isGame", true);
				VideogamesRpo.save(videogame);
				
				break;
			case "mods":
				Mod mod = ModsRpo.getOne(Long.parseLong(software_id));
				Comment comment_mod = new Comment(session.getAttribute("username").toString(),body, mod.getName());
				CommentsRpo.save(comment_mod);
				mod.getComments().add(comment_mod);
				model.addAttribute("software", mod);
				model.addAttribute("isGame", false);
				ModsRpo.save(mod);
				break;
		}
		
		
		return "sent_comment";
	}
	
	@GetMapping("/mods/{mod_id}")
	public String mod(HttpSession session, Model model, @PathVariable String mod_id, @RequestParam int page) {
		
		Mod mod = ModsRpo.getOne(Long.parseLong(mod_id));
		Page<Comment> comments = CommentsRpo.findByOwnerOrderByPubDateDesc(mod.getName(), new PageRequest(page,5));
		model.addAttribute("mod", mod);
		model.addAttribute("comments", comments);
		model.addAttribute("nextPage", mod_id + "?page=" + (page + 1));
		model.addAttribute("previousPage", mod_id + "?page=" + (page - 1));
		model.addAttribute("first_comment_page", !comments.isFirst());
		model.addAttribute("last_comment_page", !comments.isLast());
		model.addAttribute("isLogged", session.getAttribute("username") != null);
		
		return "mods";
	}
	
	@GetMapping("/all_games{page}")
	public String games_list(HttpSession session, Model model, @RequestParam int page) {
		session_params(model, session);
		
		Page<Videogame> games = VideogamesRpo.findAllByOrderByPubDateDesc(new PageRequest(page,10));
		model.addAttribute("games", games);
		model.addAttribute("nextPage", "?page=" + (page + 1));
		model.addAttribute("previousPage", "?page=" + (page - 1));
		model.addAttribute("firstPage", !games.isFirst());
		model.addAttribute("lastPage", !games.isLast());
		return "games_list";
	}
	
	@GetMapping("/all_mods{page}")
	public String mods_list(HttpSession session, Model model, @RequestParam int page) {
		session_params(model, session);
		
		Page<Mod> mods = ModsRpo.findAllByOrderByPubDateDesc(new PageRequest(page,10));
		model.addAttribute("mods", mods);
		model.addAttribute("nextPage", "?page=" + (page + 1));
		model.addAttribute("previousPage", "?page=" + (page - 1));
		model.addAttribute("firstPage", !mods.isFirst());
		model.addAttribute("lastPage", !mods.isLast());
		return "mods_list";
	}
	
	@GetMapping("/search{name}{game_page}{mod_page}")
	public String search(Model model, HttpSession session, @RequestParam String name, @RequestParam int game_page, @RequestParam int mod_page) {		
		session_params(model, session);
		
		Page<Videogame> games_by_name = VideogamesRpo.findByNameContainingIgnoreCaseOrderByPubDateDesc(name, new PageRequest(game_page, 10));
		Page<Mod> mods_by_name = ModsRpo.findByNameContainingIgnoreCaseOrderByPubDateDesc(name, new PageRequest(mod_page, 10));
		
		boolean game_empty = games_by_name.getTotalElements() == 0;
		boolean mod_empty = games_by_name.getTotalElements() == 0;
		
		if(game_empty && mod_empty) {
			model.addAttribute("message", "The search hasn't found any result containing '"+name+"'.");
			return "template";
		}
		
		model.addAttribute("game_page_empty", games_by_name.getNumberOfElements()==0);
		model.addAttribute("mod_page_empty", mods_by_name.getNumberOfElements()==0);
		model.addAttribute("first_game_page", games_by_name.isFirst() || game_empty);
		model.addAttribute("first_mod_page", mods_by_name.isFirst() || mod_empty);	
		model.addAttribute("no_more_games", games_by_name.isLast() || game_empty);
		model.addAttribute("no_more_mods", mods_by_name.isLast() || mod_empty);
		
		model.addAttribute("games", games_by_name);
		model.addAttribute("mods", mods_by_name);
		model.addAttribute("nextGamePage", "/search?name="+name+"&game_page=" + (game_page +1)+"&mod_page=" + mod_page);
		model.addAttribute("previousGamePage", "/search?name="+name+"&game_page=" + (game_page -1)+"&mod_page=" +mod_page);
		model.addAttribute("nextModPage", "/search?name="+name+"&game_page=" + game_page +"&mod_page=" + (mod_page+1));
		model.addAttribute("previousModPage", "/search?name="+name+"&game_page=" + game_page+"&mod_page=" +(mod_page-1));
		return "search_list";
	}
	
	
	@GetMapping("/logout")
	public String logout(HttpSession session, Model model) {
		model.addAttribute("message", "Successfully logged out!");
		session.invalidate();
		return "template";
	}
	
	//Setting up session and top bar on each url
	private void session_params(Model model, HttpSession session) {
		String username = "Login";
		String user_mapping = "/login";
		String register_logout = "Register";
		String rl_link = "/register"; 
		
		// Logged
		if(session.getAttribute("username") != null) {
			username = session.getAttribute("username").toString();
			user_mapping = "/profile";
			register_logout = "Logout";
			rl_link = "/logout";
		}
		
		model.addAttribute("rl", register_logout);
		model.addAttribute("rl_link", rl_link);
		model.addAttribute("username", username);
		model.addAttribute("user_mapping", user_mapping);		
	}
}
	
package es.GameSquare.GameSquareApp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
	private VideogamesRepository VideogamesRpo;
	
	@Autowired
	private ModsRepository ModsRpo;
	
	@Autowired
	private UsersRepository UsersRpo;
	
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
	
	@GetMapping("/games/{game_id}")
	public String game(Model model, @PathVariable String game_id) {
		
		Videogame videogame = VideogamesRpo.getOne(Long.parseLong(game_id));
		//Page<Comment> comments = 
		model.addAttribute("vg", videogame);
		//model.addAttribute("comments", comments);
		
		return "games";
	}
	
	@GetMapping("/mods/{mod_id}")
	public String mod(Model model, @PathVariable String mod_id) {
		
		Mod mod = ModsRpo.getOne(Long.parseLong(mod_id));
		//Page<Comment> comments = 
		model.addAttribute("mod", mod);
		//model.addAttribute("comments", comments);
		
		return "mods";
	}
	
	@GetMapping("/all_games{page}")
	public String games_list(HttpSession session, Model model, @RequestParam int page) {
		session_params(model, session);
		
		Page<Videogame> games = VideogamesRpo.findAllByOrderByPubDateDesc(new PageRequest(page,10));
		model.addAttribute("games", games);
		model.addAttribute("nextPage", "?page=" + (page + 1));
		model.addAttribute("previousPage", "?page=" + (page - 1));
		return "games_list";
	}
	
	@GetMapping("/all_mods{page}")
	public String mods_list(HttpSession session, Model model, @RequestParam int page) {
		session_params(model, session);
		
		Page<Mod> mods = ModsRpo.findAllByOrderByPubDateDesc(new PageRequest(page,10));
		model.addAttribute("mods", mods);
		model.addAttribute("nextPage", "?page=" + (page + 1));
		model.addAttribute("previousPage", "?page=" + (page - 1));
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
		if(session.getAttributeNames().hasMoreElements()) {
			username = session.getAttribute("username").toString();
			user_mapping = "/users/"+username;
			register_logout = "Logout";
			rl_link = "/logout";
		}
		
		model.addAttribute("rl", register_logout);
		model.addAttribute("rl_link", rl_link);
		model.addAttribute("username", username);
		model.addAttribute("user_mapping", user_mapping);		
	}
}
	
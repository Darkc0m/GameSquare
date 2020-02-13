package es.GameSquare.GameSquareApp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
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
		
		List<Videogame> games = VideogamesRpo.findFirst10ByOrderByPubDateDesc();
		
		
		List<Mod> mods = ModsRpo.findFirst10ByOrderByPubDateDesc();
		
		model.addAttribute("rl", register_logout);
		model.addAttribute("rl_link", rl_link);
		model.addAttribute("username", username);
		model.addAttribute("user_mapping", user_mapping);		
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
	
	@GetMapping("/logout")
	public String logout(HttpSession session, Model model) {
		model.addAttribute("message", "Successfully logged out!");
		session.invalidate();
		return "template";
	}
}
	
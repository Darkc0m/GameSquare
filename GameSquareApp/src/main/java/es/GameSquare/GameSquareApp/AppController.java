package es.GameSquare.GameSquareApp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AppController {
	
	@Autowired
	private VideogamesRepository VideogamesRpo;
	
	@Autowired
	private ModsRepository ModsRpo;
	
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
		List<String> game_names = new ArrayList<String>();
		for(int i = 0; i < games.size(); i++) {
			game_names.add(games.get(i).getName());
		}
		
		List<Mod> mods = ModsRpo.findAll(new Sort(new Order(Sort.Direction.ASC, "pubDate")));
		List<String> mod_names = new ArrayList<String>();
		for(int i = 0; i < mods.size(); i++) {
			mod_names.add(mods.get(i).getName());
		}
		
		model.addAttribute("username", username);
		model.addAttribute("user_mapping", user_mapping);		
		model.addAttribute("games", game_names);
		model.addAttribute("mods", mod_names);
		return "index";
	}
	
	@GetMapping("/game/{param}")
	public String game(Model model) {
		return "game";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "index";
	}
}
	
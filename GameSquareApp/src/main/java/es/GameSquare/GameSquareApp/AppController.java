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
	
	@GetMapping("/games/{game_id}")
	public String game(Model model, @PathVariable String game_id, @RequestParam int page) {
		
		Videogame videogame = VideogamesRpo.getOne(Long.parseLong(game_id));
		Page<Comment> comments = CommentsRpo.findBySoftwareNameOrderByPubDate(videogame.name, new PageRequest(page,20));
		model.addAttribute("vg", videogame);
		model.addAttribute("comments", comments);
		model.addAttribute("nextPage", game_id + "?page=" + (page + 1));
		model.addAttribute("previousPage", game_id + "?page=" + (page - 1));
		
		return "games";
	}
	
	@GetMapping("/mods/{mod_id}")
	public String mod(Model model, @PathVariable String mod_id, @RequestParam int page) {
		
		Mod mod = ModsRpo.getOne(Long.parseLong(mod_id));
		Page<Comment> comments = CommentsRpo.findBySoftwareNameOrderByPubDate(mod.name, new PageRequest(page,20));
		model.addAttribute("mod", mod);
		model.addAttribute("comments", comments);
		
		return "mods";
	}
	
	
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "index";
	}
}
	
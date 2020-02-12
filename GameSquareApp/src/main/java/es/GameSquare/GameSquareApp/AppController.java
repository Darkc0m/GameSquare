package es.GameSquare.GameSquareApp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AppController {
	
	@Autowired
	private VideogamesRepository VideogamesRpo;
	
	@Autowired
	private ModsRepository ModsRpo;
	
	@GetMapping("/login")
	public String session() {
		return "session";
	}
	
	@GetMapping("/template")
	public String greeting(Model model, @RequestParam String username, @RequestParam String password) {
		model.addAttribute("username", username);
		model.addAttribute("password", password);
		return "template";
	}
	
	@GetMapping("/")
	public String index(Model model) {
		
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
		
		model.addAttribute("games", game_names);
		model.addAttribute("mods", mod_names);
		return "index";
	}
}
	
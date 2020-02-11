package es.GameSquare.GameSquareApp;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AppController {
	
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
		
		List<String> games = Arrays.asList("Nier: Automata","Persona 3","League of Legends");
		List<String> mods = Arrays.asList("Mod1","Mod2","Mod3");
		model.addAttribute("games", games);
		model.addAttribute("mods", mods);
		return "index";
	}
}
	
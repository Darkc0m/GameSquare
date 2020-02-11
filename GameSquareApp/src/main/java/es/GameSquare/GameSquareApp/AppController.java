package es.GameSquare.GameSquareApp;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AppController {
	
	@GetMapping("/")
	public String session() {
		return "session";
	}
	@GetMapping("/template")
	public String greeting(Model model, @RequestParam String username, @RequestParam String password) {
		model.addAttribute("username", username);
		model.addAttribute("password", password);
		return "template";
	}	
}
	
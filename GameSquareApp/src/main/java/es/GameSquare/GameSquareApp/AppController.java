package es.GameSquare.GameSquareApp;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {
	@GetMapping("/")
	 public String greeting() {
		return "template";
	}	
}
	
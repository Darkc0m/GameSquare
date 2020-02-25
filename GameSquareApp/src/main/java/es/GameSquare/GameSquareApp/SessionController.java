package es.GameSquare.GameSquareApp;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SessionController {
	
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
		model.addAttribute("link", "/");
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
		model.addAttribute("link", "/");
		return "template";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session, Model model) {
		model.addAttribute("message", "Successfully logged out!");
		model.addAttribute("link", "/");
		session.invalidate();
		return "template";
	}

}

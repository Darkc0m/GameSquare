package es.GameSquare.GameSquareApp;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SessionController {
	
	@Autowired
	private UsersRepository UsersRpo;
	
	private String defaultRole = "USER";
	
	@GetMapping("/login")
	public String login(Model model, HttpServletRequest request) {
		model.addAttribute("link", "/login");
		model.addAttribute("action", "Login");
		return "session";
	}
	
	@GetMapping("/register")
	public String register(Model model, HttpServletRequest request) {
		model.addAttribute("action", "Register");
		model.addAttribute("link", "/register");		
		return "session";
	}
	
	@PostMapping("/register")
	public String register_sumbit(Model model, HttpServletRequest request, @RequestParam String username, @RequestParam String password) {
		User existing_user = UsersRpo.findByUserName(username);
		
		String message = "User succesfully registered!";
		
		if(existing_user == null) {
			User u = new User(username, new BCryptPasswordEncoder().encode(password));
			u.addRole(defaultRole);
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
	public String logout(Model model, HttpServletRequest request) {
		model.addAttribute("message", "Successfully logged out!");
		model.addAttribute("link", "/");
		request.getSession().invalidate();
		return "template";
	}
	
	@GetMapping("/login?error")
	public String loginerror(Model model, HttpServletRequest request) {
		model.addAttribute("message", "Incorrect username or password.");
		model.addAttribute("link", "/");
		return "template";
	}

}

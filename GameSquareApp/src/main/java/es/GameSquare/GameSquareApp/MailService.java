package es.GameSquare.GameSquareApp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
	
	private JavaMailSender javaMailSender;
	
	@Autowired
	public MailService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	
	public void sendEmailMod(User user, String modDev, String game) throws MailException {

		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(user.getEmail());
		mail.setSubject("Mod published");
		mail.setText(modDev + " has created a mod for yor game " + game);

		javaMailSender.send(mail);
	}
	
	public void notifyAll(String gameDev, List<String> others, String game) throws MailException{
		String[] othersArray = new String[others.size()];
		others.toArray(othersArray);
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(othersArray);
		mail.setSubject("New game published");
		mail.setText(gameDev + " has published a new game: " + game);
		
		javaMailSender.send(mail);
	}
}

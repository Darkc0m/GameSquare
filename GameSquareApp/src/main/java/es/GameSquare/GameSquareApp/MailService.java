package es.GameSquare.GameSquareApp;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MailService {
	
	private RestTemplate restTemplate = new RestTemplate();
	private ObjectMapper mapper = new ObjectMapper();
	
	public void sendEmailMod(User user, String modDev, String game) {
		EmailInfo emailInfo = new EmailInfo(modDev, game, user.getEmail());
		try {
			String jsonString = mapper.writeValueAsString(emailInfo);
			restTemplate.postForEntity("http://127.0.0.1:8080/sendEmailMod", jsonString, boolean.class);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void notifyAll(String gameDev, List<String> others, String game) throws JsonProcessingException {
		for(String email: others) {
			EmailInfo emailInfo = new EmailInfo(gameDev, game, email);
			String jsonString = mapper.writeValueAsString(emailInfo);
			restTemplate.postForEntity("http://127.0.0.1:8080/sendEmailGame", jsonString, boolean.class);
		}
		
		
	}
}

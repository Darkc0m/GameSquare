package es.GameSquare.GameSquareApp;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class EmailInfo implements Serializable{
	
	private String devName;
	private String softwareName;
	private String email;
	
	public EmailInfo(String devName, String softwareName, String email) {
		this.devName = devName;
		this.softwareName = softwareName;
		this.email = email;
	}
	
	public String getDevName() {
		return devName;
	}

	public void setDevName(String devName) {
		this.devName = devName;
	}

	public String getSoftwareName() {
		return softwareName;
	}

	public void setSoftwareName(String softwareName) {
		this.softwareName = softwareName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	
}

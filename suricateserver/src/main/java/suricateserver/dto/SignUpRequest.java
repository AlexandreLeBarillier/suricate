package suricateserver.dto;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * POJO for Get SignUp request
 * 
 */
@XmlRootElement
public class SignUpRequest {
	
	private String firstname;
	
	private String lastname;
	
	private String email;
	
	private byte[] password;

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public byte[] getPassword() {
		return password;
	}

	public void setPassword(byte[] password) {
		this.password = password;
	}
}

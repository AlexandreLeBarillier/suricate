package suricateserver.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PhotoResponse {

	private boolean result;
	
	private String message;
	
	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}

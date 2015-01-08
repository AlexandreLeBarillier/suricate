package suricateserver.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PhotoRequest {

	private String photo;
	
	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
}

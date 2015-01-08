package suricateserver.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Pincode {

	private String pincode;
	
	private String validity;
	
	private String validity_rule;
	
	private String owner;

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getValidity() {
		return validity;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}

	public String getValidity_rule() {
		return validity_rule;
	}

	public void setValidity_rule(String validity_rule) {
		this.validity_rule = validity_rule;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

}

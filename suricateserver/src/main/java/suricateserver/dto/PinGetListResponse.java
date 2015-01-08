package suricateserver.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PinGetListResponse {

	private List<Pincode> pincodes;

	public List<Pincode> getPincodes() {
		return pincodes;
	}

	public void setPincodes(List<Pincode> pincodes) {
		this.pincodes = pincodes;
	}
}

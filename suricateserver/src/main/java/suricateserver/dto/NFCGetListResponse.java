package suricateserver.dto;

import java.util.HashMap;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class NFCGetListResponse {

	private HashMap<String, String> badges;

	public HashMap<String, String> getBadges() {
		return badges;
	}

	public void setBadges(HashMap<String, String> badges) {
		this.badges = badges;
	}
}

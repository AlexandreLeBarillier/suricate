package suricateserver.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AccessLogResponse {

	private List<AccessLogDTO> accesslogs;

	public List<AccessLogDTO> getAccesslogs() {
		return accesslogs;
	}

	public void setAccesslogs(List<AccessLogDTO> accesslogs) {
		this.accesslogs = accesslogs;
	}

}

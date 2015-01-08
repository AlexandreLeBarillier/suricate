package suricateserver.dto;

import java.util.Date;

public class AccessLogDTO {

	private Date date;
	
	private String content;
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}

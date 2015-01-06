package suricateserver.dto;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * POJO for Get Information response
 * 
 * @author Etienne
 *
 */
@XmlRootElement
public class InformationResponse {

	/** content */
	private String content;

	/**
	 * @return content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 */
	public void setContent(String content) {
		this.content = content;
	}

}

package suricateserver.ws;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import suricateserver.dto.InformationResponse;

/**
 * Information web service
 * 
 * @author Etienne
 *
 */
@Path("information")
public class Information {
	/**
	 * Method handling HTTP GET requests. The returned object will be sent to
	 * the client as "text/plain" media type.
	 *
	 * @return String that will be returned as a text/plain response.
	 */
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String ping() {
		return "Hello the server is working!";
	}

	/**
	 * @return information
	 */
	@GET
	@Path("/getinformation")
	@Produces(MediaType.APPLICATION_JSON)
	public InformationResponse simplejson() {
		InformationResponse information = new InformationResponse();
		information.setContent("hello world getinformation");
		return information;
	}
}

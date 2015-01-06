package suricateserver.ws;

import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import suricateserver.dao.InformationDAO;
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
	 * Get version of the server
	 * 
	 * @return version
	 */
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String ping() {
		try {
			String version = InformationDAO.getInformation().getContent();
			return "Meerkat Server is running in version " + version;
		} catch (SQLException e) {
			e.printStackTrace();
			return "an internal error occured";
		}
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

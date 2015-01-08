package suricateserver.ws;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import suricateserver.dao.AccessLogDAO;
import suricateserver.dto.AccessLogResponse;

@Path("accesslog")
public class AccessLog {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response accessLog() {
		try {
			AccessLogResponse result = AccessLogDAO.getAccessLog();
			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("internal error").build();
		}
	}
}

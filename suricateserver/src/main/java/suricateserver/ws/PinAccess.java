package suricateserver.ws;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import suricateserver.dao.PinAccessDAO;
import suricateserver.dto.PinCreateAccessRequest;
import suricateserver.dto.PinGetListResponse;
import suricateserver.dto.PinVerifyAccessRequest;
import suricateserver.dto.PinVerifyAccessResponse;

@Path("pinaccess")
public class PinAccess {

	@POST
	@Path("/createpinaccess")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(PinCreateAccessRequest request) {
 
		try {
			PinAccessDAO.createPinAccess(request);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("internal error").build();
		}
		return Response.status(201).entity("ok").build();
 
	}
	
	/**
	 * @return autorisation d'accès
	 */
	@GET
	@Path("/verifypinaccess/pincode/{code}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response verify(@PathParam("code") String code) {
		PinVerifyAccessResponse result = new PinVerifyAccessResponse();
		PinVerifyAccessRequest request = new PinVerifyAccessRequest();
		request.setPincode(code);
		try {
			result =PinAccessDAO.verify(request);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("internal error").build();
		}
		return Response.status(200).entity(result).build();
	}
	
	/**
	 * @return autorisation d'accès
	 */
	@GET
	@Path("/getlist")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getList() {
		PinGetListResponse result = new PinGetListResponse();
		try {
			result = PinAccessDAO.getList();
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(500).entity("internal error").build();
		}
		return Response.status(200).entity(result).build();
	}
}

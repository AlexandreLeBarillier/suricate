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

import suricateserver.dao.NFCAccessDAO;
import suricateserver.dto.NFCCreateAccessRequest;
import suricateserver.dto.NFCGetListResponse;
import suricateserver.dto.NFCVerifyAccessRequest;
import suricateserver.dto.NFCVerifyAccessResponse;

@Path("nfcaccess")
public class NFCAccess {

	@POST
	@Path("/createnfcaccess")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(NFCCreateAccessRequest request) {
 
		try {
			NFCAccessDAO.createNfcAccess(request);
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
	@Path("/verifynfcaccess/nfccode/{code}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response verify(@PathParam("code") String code) {
		NFCVerifyAccessResponse result = new NFCVerifyAccessResponse();
		NFCVerifyAccessRequest request = new NFCVerifyAccessRequest();
		request.setNfccode(code);
		try {
			result = NFCAccessDAO.verify(request);
		} catch (SQLException e) {
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
		NFCGetListResponse result = new NFCGetListResponse();
		try {
			NFCAccessDAO.getList();
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(500).entity("internal error").build();
		}
		return Response.status(200).entity(result).build();
	}
}

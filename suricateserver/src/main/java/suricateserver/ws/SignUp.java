package suricateserver.ws;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import suricateserver.dao.SuricateUserDAO;
import suricateserver.dto.SignUpRequest;

@Path("signup")
public class SignUp {
	

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response signup(SignUpRequest request) {
 
		try {
			SuricateUserDAO.createUser(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(201).entity("ok").build();
 
	}
	
}

package suricateserver.ws;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import suricateserver.dto.PhotoRequest;
import suricateserver.matching.PhotoService;

@Path("matching")
public class Photo {

	@POST
	@Path("/verifyphoto")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response verifyphoto(PhotoRequest request) {
 
		try {
			String path = PhotoService.savePhoto(request);
			PhotoService.performMatching(path);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("internal error").build();
		}
		return Response.status(201).entity("ok").build();
 
	}
}

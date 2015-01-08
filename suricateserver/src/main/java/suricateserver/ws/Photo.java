package suricateserver.ws;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import suricateserver.dto.PhotoRequest;
import suricateserver.dto.PhotoResponse;
import suricateserver.matching.PhotoService;

@Path("matching")
public class Photo {

	@POST
	@Path("/verifyphoto")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response verifyphoto(PhotoRequest request) {
		PhotoResponse response = new PhotoResponse();
		try {
			String path = PhotoService.savePhoto(request);
			boolean result = PhotoService.performMatching(path);
			if(result) {
				response.setResult(true);
				response.setMessage("Individu reconnu");
				return Response.status(200).entity(response).build();
			} else {
				response.setResult(false);
				response.setMessage("Individu non reconnu");
				return Response.status(200).entity(response).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("internal error").build();
		}
		
	}
	
}

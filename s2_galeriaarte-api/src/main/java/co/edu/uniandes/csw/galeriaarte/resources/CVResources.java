package co.edu.uniandes.csw.galeriaarte.resources;

import co.edu.uniandes.csw.galeriaarte.dtos.CVDTO;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
@Path("/cvs")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class CVResources {


	/**
	 * Crea una hoja de vida 
	 * @param fdbDTO
	 * @return
	 */
    @POST
	public CVDTO createCV(CVDTO cv)
	{
		return cv;
	}
}
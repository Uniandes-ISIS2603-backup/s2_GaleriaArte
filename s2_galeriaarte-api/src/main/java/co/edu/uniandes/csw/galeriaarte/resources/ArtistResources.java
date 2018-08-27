package co.edu.uniandes.csw.galeriaarte.resources;

import co.edu.uniandes.csw.galeriaarte.dtos.ArtistDTO;
import co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
/**
 * @author Anderson Barragan
 */
@Path(artist)
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ArtistResources {

    @Inject
    ArtistLogic artistLogic;

    @POST
    public ArtistDTO createArtist(ArtistDTO artista) throws BusinessLogicException{
        return null;
    }
    
    @GET
    @Path("artists://d+")
    public ArtistDTO getArtistsById(@PathParam("artist") Long idArtist) throws WebApplicationException{
        return null;
    }
}

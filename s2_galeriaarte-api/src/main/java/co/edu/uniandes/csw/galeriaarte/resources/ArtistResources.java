package co.edu.uniandes.csw.galeriaarte.resources;

import co.edu.uniandes.csw.galeriaarte.dtos.ArtistDTO;
import co.edu.uniandes.csw.galeriaarte.ejb.ArtistLogic;
import co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
/**
 * @author Anderson Barragan a.barragan
 */
@Path("artists")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ArtistResources {

    @Inject
    ArtistLogic artistLogic;
private static final Logger LOGGER = Logger.getLogger(ArtistResources.class.getName());
    @POST
    public ArtistDTO createArtist(ArtistDTO artist) throws BusinessLogicException{
       LOGGER.log(Level.INFO, "ArtistResources createArtist: input: {0}", artist.toString());
        ArtistDTO artistDTO = new ArtistDTO(artistLogic.createAuthor(artist.toEntity()));
        LOGGER.log(Level.INFO, "ArtistResources createArtist: output: {0}", artistDTO.toString());
        return artistDTO;
    }
    
    @GET
    @Path("artists://d+")
    public ArtistDTO getArtistsById(@PathParam("/artists") Long idArtist) throws WebApplicationException{
        return null;
    }
}

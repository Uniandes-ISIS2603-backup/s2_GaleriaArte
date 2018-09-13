package co.edu.uniandes.csw.galeriaarte.resources;

import co.edu.uniandes.csw.galeriaarte.dtos.ArtistDTO;
import co.edu.uniandes.csw.galeriaarte.ejb.ArtistLogic;
import co.edu.uniandes.csw.galeriaarte.entities.ArtistEntity;
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
import javax.ws.rs.core.MediaType;

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
    public ArtistDTO createArtist(ArtistDTO artist) throws BusinessLogicException {
        //LOGGER.log(Level.INFO, "ArtistResources createArtist: input: {0}", artist.toString());
        //ArtistDTO artistDTO = new ArtistDTO(artistLogic.createArtist(artist.toEntity()));
        //LOGGER.log(Level.INFO, "ArtistResources createArtist: output: {0}", artistDTO.toString());
        return artist;
    }

    //@GET
    //@Path("artistsId://d+")
    //public ArtistDTO getArtists(@PathParam("/artistsId") Long artistId) throws WebApplicationException {
    //    LOGGER.log(Level.INFO, "ArtistResource getartist: input: {0}", artistId);
    //    ArtistEntity artistEntity = artistLogic.getArtist(artistId);
    //    if (artistEntity == null) {
    //        throw new WebApplicationException("El recurso /authors/" + artistId + " no existe.", 404);
    //    }
    //    ArtistDTO artistDTO = new ArtistDTO(artistEntity);
    //    LOGGER.log(Level.INFO, "AuthorResource getAuthor: output: {0}", artistDTO.toString());
    //    return artistDTO;
    //}
}

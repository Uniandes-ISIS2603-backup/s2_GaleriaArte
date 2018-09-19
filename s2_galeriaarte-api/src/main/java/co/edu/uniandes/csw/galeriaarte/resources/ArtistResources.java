package co.edu.uniandes.csw.galeriaarte.resources;

import co.edu.uniandes.csw.galeriaarte.dtos.ArtistDTO;
import co.edu.uniandes.csw.galeriaarte.ejb.ArtistLogic;
import co.edu.uniandes.csw.galeriaarte.entities.ArtistEntity;
import co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

/**
 * @author a.barragan Anderson Barragan 
 */
@Path("artists")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ArtistResources {

    private static final Logger LOGGER = Logger.getLogger(ArtistResources.class.getName());
    
    @Inject
    ArtistLogic artistLogic;
    

    @POST
    public ArtistDTO createArtist(ArtistDTO artist) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ArtistResource createArtist: input: {0}", artist.toString());
        ArtistDTO artistDTO = new ArtistDTO(artistLogic.createArtist(artist.toEntity()));
        LOGGER.log(Level.INFO, "ArtistResource createArtist: output: {0}", artistDTO.toString());
        return artistDTO;
      }

    /**
     * Busca y devuelve todos los artistas que existen en la aplicacion.
     *
     * @return JSONArray {@link ArtistDTO} - Los artistas encontrados en la
     * aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<ArtistDTO> getArtists() {
        LOGGER.info("ArtistResource getArtists: input: void");
        List<ArtistDTO> listaArtistas = listEntityToDTO(artistLogic.getArtists());
        LOGGER.log(Level.INFO, "ArtistResource getArtists: output: {0}", listaArtistas.toString());
        return listaArtistas;
    }
    
    @GET
    @Path("artistsId://d+")
    public ArtistDTO getArtist(@PathParam("/artistsId") Long artistId) throws WebApplicationException {
        LOGGER.log(Level.INFO, "ArtistResource getArtist: input: {0}", artistId);
        ArtistEntity artistEntity = artistLogic.getArtist(artistId);
        if (artistEntity == null) {
            throw new WebApplicationException("El recurso /artists/" + artistId + " no existe.", 404);
        }
       ArtistDTO artistDTO = new ArtistDTO(artistEntity);
        LOGGER.log(Level.INFO, "ArtistResource getArtist: output: {0}", artistDTO.toString());
        return artistDTO;
    }
    
    /**
     * Borra el artista con el id asociado recibido en la URL.
     *
     * @param artistId Identificador del artista que se desea borrar. Este debe
     * ser una cadena de dígitos.
     * @throws BusinessLogicException
     * si el artista tiene obras asociados
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra el artista a borrar.
     */
    @DELETE
    @Path("{artistsId: \\d+}")
    public void deleteArtist(@PathParam("artistsId") Long artistId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ArtistResource deleteAartist: input: {0}", artistId);
        if (artistLogic.getArtist(artistId) == null) {
            throw new WebApplicationException("El recurso /artists/" + artistId + " no existe.", 404);
        }
        artistLogic.deleteArtist(artistId);
        LOGGER.info("rtistResource deleteArtist: output: void");
    }
    
    /**
     * Actualiza el artista con el id recibido en la URL con la información que se
     * recibe en el cuerpo de la petición.
     *
     * @param artistId Identificador del artista que se desea actualizar. Este
     * debe ser una cadena de dígitos.
     * @param artista {@link ArtistDTO} El artista que se desea guardar.
     * @return JSON {@link ArtistDTO} - El artista guardado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el artista a
     * actualizar.
     */
    @PUT
    @Path("{artistsId: \\d+}")
    public ArtistDTO updateArtist(@PathParam("artistsId") Long artistId, ArtistDTO artista) {
        LOGGER.log(Level.INFO, "ArtistResource updateArtist: input: artistsId: {0} , artist: {1}", new Object[]{artistId, artista.toString()});
        artista.setId(artistId);
        if (artistLogic.getArtist(artistId) == null) {
            throw new WebApplicationException("El recurso /artists/" + artistId + " no existe.", 404);
        }
        ArtistDTO artistDTO = new ArtistDTO(artistLogic.updateArtist(artistId, artista.toEntity()));
        LOGGER.log(Level.INFO, "ArtistResource updateArtist: output: {0}", artistDTO.toString());
        return artistDTO;
    }
    
    
    /**
     * Convierte una lista de ArtistEntity a una lista de ArtistDTO.
     *
     * @param entityList Lista de ArtistEntity a convertir.
     * @return Lista de ArtistDTO convertida.
     */
    private List<ArtistDTO> listEntityToDTO(List<ArtistEntity> entityList) {
        List<ArtistDTO> list = new ArrayList<>();
        for (ArtistEntity entity : entityList) {
            list.add(new ArtistDTO(entity));
        }
        return list;
    }
}

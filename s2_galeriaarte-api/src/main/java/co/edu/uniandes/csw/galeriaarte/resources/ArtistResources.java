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
 * Clase que implementa el recurso de "Artista".
 * @author a.barragan
 */
@Path("artists")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ArtistResources {
    private static final Logger LOGGER = Logger.getLogger(ArtistResources.class.getName());
    
@Inject 
ArtistLogic artistLogic;
    /**
     * Crea un Artista
     *
     * @param Artist
     * @return el dto del artista
     */
    @POST
    public ArtistDTO createArtist(ArtistDTO Artist) throws BusinessLogicException {
         LOGGER.log(Level.INFO, "ArtistResources createArtist: input: {0}", Artist.toString());
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        ArtistEntity artistEntity = Artist.toEntity();
        // Invoca la lógica para crear la pintura nueva
      
      ArtistEntity   nuevoArtistEntity = artistLogic.createArtist(artistEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        ArtistDTO nuevoArtistDTO = new ArtistDTO(nuevoArtistEntity);
        LOGGER.log(Level.INFO, "ArtistResoruces createArtist: input: {0}", nuevoArtistDTO.toString());
        return nuevoArtistDTO;
    }

       /**
     * Busca y devuelve todas los artists que existen en la aplicacion.
     *
     * @return JSONArray {@link ArtistDTO} - Los artists encontradas en
     * la aplicación. Si no hay ninguno retorna una lista vacía.
     */
    
    
    @GET
    public List<ArtistDTO> getArtists() {
        LOGGER.info("ArtistResource getArtists: input: void");
        List<ArtistDTO> listartistas = listEntity2DTO(artistLogic.getArtists());
        LOGGER.log(Level.INFO, "ArtistResource getArtists: output: {0}", listartistas.toString());
        return listartistas;
    }

    /**
     * Busca el Artist con el id asociado recibido en la URL y la devuelve.
     *
     * @param artistId Identificador de la Artist que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link ArtistDTO} - La artista buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la artista.
     */
    @GET
    @Path("{artistsId: \\d+}")
    public ArtistDTO getArtist(@PathParam("artistsId") Long artistId) throws WebApplicationException {
        LOGGER.log(Level.INFO, "ArtistResource getArtist: input: {0}", artistId);
        ArtistEntity artistEntity;
        artistEntity = artistLogic.getArtist(artistId);
        if (artistEntity == null) {
            throw new WebApplicationException("El recurso /artists/" + artistId + " no existe.", 404);
        }
        ArtistDTO detailDTO = new ArtistDTO(artistEntity);
        LOGGER.log(Level.INFO, "ArtistResource getArtist: output: {0}", detailDTO.toString());
        return detailDTO;
    }

    /**
     * Actualiza la Artist con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param artistId Identificador de la Artist que se desea
     * actualizar. Este debe ser una cadena de dígitos.
     * @param artistdto {@link ArtistDTO} La artista que se desea guardar.
     * @return JSON {@link ArtistDTO} - La artista guardada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la artista a
     * actualizar.
     */
    @PUT
    @Path("{artistsId: \\d+}")
    public ArtistDTO updateArtist(@PathParam("artistsId") Long artistId, ArtistDTO artistdto) throws WebApplicationException {
        LOGGER.log(Level.INFO, "ArtistResource updateArtist: input: id:{0} , Artist: {1}", new Object[]{artistId, artistdto.toString()});
        artistdto.setId(artistId);
        if (artistLogic.getArtist(artistId) == null) {
            throw new WebApplicationException("El recurso /artists/" + artistId + " no existe.", 404);
        }
        ArtistDTO detailDTO = new ArtistDTO(artistLogic.updateArtist(artistdto.toEntity()));
        LOGGER.log(Level.INFO, "ArtistResource updateArtist: output: {0}", detailDTO.toString());
        return detailDTO;
    }

    /**
     * Borra la Artist con el id asociado recibido en la URL.
     *
     * @param artistId Identificador de la artista que se desea borrar.
     * Este debe ser una cadena de dígitos.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar la artista.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la artista.
     */
    @DELETE
    @Path("{artistsId: \\d+}")
    public void deleteArtist(@PathParam("artistsId") Long artistId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ArtistResource updateArtist: input: {0}", artistId);
        if (artistLogic.getArtist(artistId) == null) {
            throw new WebApplicationException("El recurso /artists/" + artistId + " no existe.", 404);
        }
        artistLogic.deleteArtist(artistId);
        LOGGER.info("ArtistResource updateArtist: output: void");
    }

    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos ArtistEntity a una lista de
     * objetos ArtistDTO (json)
     *
     * @param entityList corresponde a la lista de artistas de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de artistas en forma DTO (json)
     */
    private List<ArtistDTO> listEntity2DTO(List<ArtistEntity> entityList) {
        List<ArtistDTO> list = new ArrayList<>();
        for (ArtistEntity entity : entityList) {
            list.add(new ArtistDTO(entity));
        }
        return list;
    }
    
}



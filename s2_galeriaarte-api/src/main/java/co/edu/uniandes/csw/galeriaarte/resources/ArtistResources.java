/*
MIT License

Copyright (c) 2017 Universidad de los Andes - ISIS2603

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package co.edu.uniandes.csw.galeriaarte.resources;

import co.edu.uniandes.csw.galeriaarte.dtos.ArtistDTO;
import co.edu.uniandes.csw.galeriaarte.dtos.ArtistDetailDTO;
import co.edu.uniandes.csw.galeriaarte.ejb.ArtistLogic;
import co.edu.uniandes.csw.galeriaarte.entities.ArtistEntity;
import co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.galeriaarte.mappers.WebApplicationExceptionMapper;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;

/**
 * Clase que implementa el recurso "artists".
 *
 * @author Anderson Barragan    
 */
@Path("/artists")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class ArtistResources {

    private static final Logger LOGGER = Logger.getLogger(ArtistResources.class.getName());

    @Inject
    private ArtistLogic artistLogic;

    /**
     * Crea un nuevo artista con la informacion que se recibe en el cuerpo de la
     * petición y se regresa un objeto identico con un id auto-generado por la
     * base de datos.
     *
     * @param artist {@link ArtistDTO} - EL artista que se desea guardar.
     * @return JSON {@link ArtistDTO} - El artista guardado con el atributo id
     * autogenerado.
     */
    @POST
    public ArtistDTO createArtist(ArtistDTO artist) {
        LOGGER.log(Level.INFO, "ArtistResource createArtist: input: {0}", artist.toString());
        ArtistDTO artistDTO = new ArtistDTO(artistLogic.createArtist(artist.toEntity()));
        LOGGER.log(Level.INFO, "ArtistResource createArtist: output: {0}", artistDTO.toString());
        return artistDTO;
    }

    /**
     * Busca y devuelve todos los artistaes que existen en la aplicacion.
     *
     * @return JSONArray {@link ArtistDetailDTO} - Los artistaes encontrados en la
     * aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<ArtistDetailDTO> getArtists() {
        LOGGER.info("ArtistResource getArtists: input: void");
        List<ArtistDetailDTO> listaArtists = listEntity2DTO(artistLogic.getArtists());
        LOGGER.log(Level.INFO, "ArtistResource getArtists: output: {0}", listaArtists.toString());
        return listaArtists;
    }

    /**
     * Busca el artista con el id asociado recibido en la URL y lo devuelve.
     *
     * @param artistsId Identificador del artista que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link ArtistDetailDTO} - El artista buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el artista.
     */
    @GET
    @Path("{artistsId: \\d+}")
    public ArtistDetailDTO getArtist(@PathParam("artistsId") Long artistsId) {
        LOGGER.log(Level.INFO, "ArtistResource getArtist: input: {0}", artistsId);
        ArtistEntity artistEntity = artistLogic.getArtist(artistsId);
        if (artistEntity == null) {
            throw new WebApplicationException("El recurso /artists/" + artistsId + " no existe.", 404);
        }
        ArtistDetailDTO detailDTO = new ArtistDetailDTO(artistEntity);
        LOGGER.log(Level.INFO, "ArtistResource getArtist: output: {0}", detailDTO.toString());
        return detailDTO;
    }

    /**
     * Actualiza el artista con el id recibido en la URL con la información que se
     * recibe en el cuerpo de la petición.
     *
     * @param artistsId Identificador del artista que se desea actualizar. Este
     * debe ser una cadena de dígitos.
     * @param artist {@link ArtistDetailDTO} El artista que se desea guardar.
     * @return JSON {@link ArtistDetailDTO} - El artista guardado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el artista a
     * actualizar.
     */
    @PUT
    @Path("{artistsId: \\d+}")
    public ArtistDetailDTO updateArtist(@PathParam("artistsId") Long artistsId, ArtistDetailDTO artist) {
        LOGGER.log(Level.INFO, "ArtistResource updateArtist: input: artistsId: {0} , artist: {1}", new Object[]{artistsId, artist.toString()});
        artist.setId(artistsId);
        if (artistLogic.getArtist(artistsId) == null) {
            throw new WebApplicationException("El recurso /artists/" + artistsId + " no existe.", 404);
        }
        ArtistDetailDTO detailDTO = new ArtistDetailDTO(artistLogic.updateArtist(artistsId, artist.toEntity()));
        LOGGER.log(Level.INFO, "ArtistResource updateArtist: output: {0}", detailDTO.toString());
        return detailDTO;
    }

    /**
     * Borra el artista con el id asociado recibido en la URL.
     *
     * @param artistsId Identificador del artista que se desea borrar. Este debe
     * ser una cadena de dígitos.
     * @throws co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException
     * si el artista tiene obras asociados
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra el artista a borrar.
     */
    @DELETE
    @Path("{artistsId: \\d+}")
    public void deleteArtist(@PathParam("artistsId") Long artistsId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ArtistResource deleteArtist: input: {0}", artistsId);
        if (artistLogic.getArtist(artistsId) == null) {
            throw new WebApplicationException("El recurso /artists/" + artistsId + " no existe.", 404);
        }
        artistLogic.deleteArtist(artistsId);
        LOGGER.info("ArtistResource deleteArtist: output: void");
    }
//
//    /**
//     * Conexión con el servicio de obras para un artista.
//     * {@link ArtistPaintworksResource}
//     *
//     * Este método conecta la ruta de /artists con las rutas de /paintworks que
//     * dependen del artista, es una redirección al servicio que maneja el segmento
//     * de la URL que se encarga de los obras.
//     *
//     * @param artistsId El ID del artista con respecto al cual se accede al
//     * servicio.
//     * @return El servicio de Libros para ese artista en paricular.
//     */
//    @Path("{artistsId: \\d+}/paintworks")
//    public Class<ArtistPaintworksResource> getArtistPaintworksResource(@PathParam("artistsId") Long artistsId) {
//        if (artistLogic.getArtist(artistsId) == null) {
//            throw new WebApplicationException("El recurso /artists/" + artistsId + " no existe.", 404);
//        }
//        return ArtistPaintworksResource.class;
//    }

    /**
     * Convierte una lista de ArtistEntity a una lista de ArtistDetailDTO.
     *
     * @param entityList Lista de ArtistEntity a convertir.
     * @return Lista de ArtistDetailDTO convertida.
     */
    private List<ArtistDetailDTO> listEntity2DTO(List<ArtistEntity> entityList) {
        List<ArtistDetailDTO> list = new ArrayList<>();
        for (ArtistEntity entity : entityList) {
            list.add(new ArtistDetailDTO(entity));
        }
        return list;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.resources;
import co.edu.uniandes.csw.galeriaarte.dtos.ArtistDTO;
import co.edu.uniandes.csw.galeriaarte.dtos.PaintworkDetailDTO;
import co.edu.uniandes.csw.galeriaarte.ejb.ArtistLogic;
import co.edu.uniandes.csw.galeriaarte.ejb.PaintworkLogic;
import co.edu.uniandes.csw.galeriaarte.ejb.PaintworksArtistLogic;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 * Clase que implementa el recurso "Artist/{id}/Paintworks".
 *
 * @author ISIS2603
 * @version 1.0
 */
@Path("Paintworks/{PaintworksId: \\d+}/Artist")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PaintworkArtistResource 
{

    private static final Logger LOGGER = Logger.getLogger(PaintworkArtistResource.class.getName());

    @Inject
    private PaintworkLogic paintworkLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private PaintworksArtistLogic paintworkArtistLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private ArtistLogic artistLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Remplaza la instancia de Artist asociada a un Paintwork.
     *
     * @param paintworksId Identificador del obraque se esta actualizando. Este
     * debe ser una cadena de dígitos.
     * @param artist La Artist que se será del libro.
     * @return JSON {@link PaintworkDetailDTO} - El arreglo de libros guardado en la
     * Artist.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la Artist o el
     * libro.
     */
    @PUT
    public PaintworkDetailDTO replaceEditorial(@PathParam("PaintworksId") Long paintworksId, ArtistDTO artist) 
    {
        LOGGER.log(Level.INFO, "PaintworkEditorialResource replaceEditorial: input: PaintworksId{0} , Artist:{1}", new Object[]{paintworksId, artist});
        if (paintworkLogic.getPaintWork(paintworksId) == null)
        {
            throw new WebApplicationException("El recurso /Paintworks/" + paintworksId + " no existe.", 404);
        }
        if (artistLogic.getArtist(artist.getId()) == null) 
        {
            throw new WebApplicationException("El recurso /artist/" + artist.getId() + " no esta.", 404);
        }
        PaintworkDetailDTO PaintworkDetailDTO = new PaintworkDetailDTO(paintworkArtistLogic.replaceArtist(paintworksId, artist.getId()));
        LOGGER.log(Level.INFO, "PaintworkEditorialResource replaceEditorial: output: {0}", PaintworkDetailDTO);
        return PaintworkDetailDTO;
    }
}

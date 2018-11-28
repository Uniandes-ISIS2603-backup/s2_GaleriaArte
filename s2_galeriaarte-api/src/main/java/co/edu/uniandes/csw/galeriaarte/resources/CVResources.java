package co.edu.uniandes.csw.galeriaarte.resources;

import co.edu.uniandes.csw.galeriaarte.dtos.CVDTO;
import co.edu.uniandes.csw.galeriaarte.ejb.CVLogic;
import co.edu.uniandes.csw.galeriaarte.entities.CVEntity;
import co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * Clase que implementa el recurso "reviews".
 *
 * @author ja.penat
 * @version 1.0
 */
@Produces("application/json")
@Consumes("application/json")
public class CVResources
{
    private static final Logger LOGGER = Logger.getLogger(CVResources.class.getName());

    @Inject
    private CVLogic cvLogic;
    
     /**
     * Crea una nueva hoja de vida  con la informacion que se recibe en el cuerpo de la
     * petición y se regresa un objeto identico con un id auto-generado por la
     * base de datos.
     *
     * @param artistsId El ID del artista del cual se le agrega la reseña
     * @param cvDTO {@link CVDTO} - La hoja de vida  que se desea guardar.
     * @return JSON {@link CVDTO} - La hoja de vida guardada con el atributo id
     * autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando el autor ya tiene una hoja de vida asociada.
     */
    @POST
    public CVDTO createCV(@PathParam("artistsId") Long artistsId, CVDTO cvDTO) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "CVResource createCV: input: {0}", cvDTO);
        CVDTO nuevoCVDTO = new CVDTO(cvLogic.createCV(artistsId, cvDTO.toEntity()));
        LOGGER.log(Level.INFO, "CVResource createCV: output: {0}", nuevoCVDTO);
        return nuevoCVDTO;
    }

    /**
     * Busca y devuelve la hoja de vida  con el ID recibido en la URL, relativa a el 
     * artista.
     *
     * @param artistsId El ID del artista del cual se busca su hoja de vida
     * @return {@link ReviewDTO} - La reseña encontradas en el libro.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el artista.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la hoja de vida.
     */
    @GET
    public CVDTO getCV(@PathParam("artistsId") Long artistsId) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "CVResource getCV: inicia el proceso de obtener la hoja de vida");
        CVEntity entity = cvLogic.getCV(artistsId);
        if (entity == null) 
        {
            throw new WebApplicationException("El recurso /artist/" + artistsId + "  no existe.", 404);
        }
        CVDTO cvDTO = new CVDTO(entity);
        LOGGER.log(Level.INFO, "CVResource getCV: output: {0}", cvDTO);
        return cvDTO;
    }
   
     /**
     * Actualiza una hoja de vida con la informacion que se recibe en el cuerpo de la
     * petición y se regresa el objeto actualizado.
     *
     * @param artistsId El ID del artista del cual se guarda la reseña
     * @param cvId El ID de la hoja que se va a actualizar
     * @param cvDTO {@link CVDTO} - La hoja de vida que se desea guardar.
     * @return JSON {@link CVDTO} - La hoja de vida actualizada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la hoja de vida
     */
    @PUT
    @Path("{cvId: \\d+}")
    public CVDTO updateCV(@PathParam("artistsId") Long artistsId, @PathParam("cvId") Long cvId, CVDTO cvDTO) 
    {
        LOGGER.log(Level.INFO, "CVResource updateCV: input:artistId: {0} , cvId: {1} , cvDTO:{2}", new Object[]{artistsId, cvId, cvDTO});
        CVEntity entity = cvLogic.getCV(artistsId);
        if (entity == null)
        {
            throw new WebApplicationException("El recurso /artists/" + artistsId + "/cvId/" + cvId + " no existe.", 404);
        }
        CVDTO newCVDTO = new CVDTO(cvLogic.updateCV(cvId, cvDTO.toEntity()));
        LOGGER.log(Level.INFO, "CVResource updateCV: output:{0}", cvDTO);
        return newCVDTO;
    }

     /**
     * Borra la hoja de vida con el id asociado recibido en la URL.
     *
     * @param artistsId El ID del artista del cual se va a eliminar la hoja de vida.
     * @param cvId El ID de la hoja de vida  que se va a eliminar.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar la hoja de vida.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la hoja de vida..
     */
    @DELETE
    @Path("{cvsId: \\d+}")
    public void deleteCV(@PathParam("artistsId") Long artistsId, @PathParam("cvId") Long cvId) throws BusinessLogicException
    {
        CVEntity entity = cvLogic.getCV(artistsId);
        if (entity == null) 
        {
            throw new WebApplicationException("El recurso /artist/" + artistsId + "/cv/" + cvId + " no existe.", 404);
        }
        cvLogic.deleteCV(artistsId);
    }
}



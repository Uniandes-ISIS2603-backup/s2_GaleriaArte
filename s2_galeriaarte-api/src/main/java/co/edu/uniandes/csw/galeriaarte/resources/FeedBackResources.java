/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package co.edu.uniandes.csw.galeriaarte.resources;
import co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.inject.Inject;
import java.util.logging.Logger;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import java.util.logging.Level;
import javax.ws.rs.PathParam;
import co.edu.uniandes.csw.galeriaarte.dtos.FeedBackDTO;
import co.edu.uniandes.csw.galeriaarte.ejb.FeedBackLogic;
import co.edu.uniandes.csw.galeriaarte.entities.FeedBackEntity;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author  ja.penat
 */
@Produces("application/json")
@Consumes("application/json")
public class FeedBackResources
{

    private static final Logger LOGGER = Logger.getLogger(FeedBackResources.class.getName());

    @Inject
    private FeedBackLogic feedBackLogic;

    /**
     * Crea un nuevo comentario con la informacion que se recibe en el cuerpo de la
     * petición y se regresa un objeto identico con un id auto-generado por la
     * base de datos.
     *
     * @param paintworksId El ID del obra del cual se le agrega la comentario
     * @param feedBack {@link FeedBackDTO} - El comentario que se desea guardar.
     * @return JSON {@link FeedBackDTO} - El comentario guardada con el atributo id
     * autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe la comentario.
     */
    @POST
    public FeedBackDTO createFeedBack(@PathParam("paintworksId") Long paintworksId, FeedBackDTO feedBack) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "feedbackResource createfeedback: input: {0}", feedBack);
        FeedBackDTO nuevoFeedBackDTO = new FeedBackDTO(feedBackLogic.createFeedBack(paintworksId, feedBack.toEntity()));
        LOGGER.log(Level.INFO, "feedbackResource createfeedback: output: {0}", nuevoFeedBackDTO);
        return nuevoFeedBackDTO;
    }

    /**
     * Busca y devuelve todas las comentarios que existen en un obra.
     *
     * @param paintworksId El ID del obra del cual se buscan las comentarios
     * @return JSONArray {@link FeedBackDTO} - Las comentarios encontradas en el
     * obra. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<FeedBackDTO> getFeedBacks(@PathParam("paintworksId") Long paintworksId)
    {
        LOGGER.log(Level.INFO, "feedBackResource getfeedbacks: input: {0}", paintworksId);
        List<FeedBackDTO> listaDTOs =  listEntity2DTO(feedBackLogic.getFeedBacks(paintworksId));
        LOGGER.log(Level.INFO, "lpaintworksResource getpaintworks: output: {0}", listaDTOs);
        return listaDTOs;
    }

    /**
     * Busca y devuelve la comentario con el ID recibido en la URL, relativa a una
     * obra.
     *
     * @param paintworksId El ID del obra del cual se buscan las comentarios
     * @param feedBacksId El ID de la comentario que se busca
     * @return {@link FeedBackDTO} - La comentario encontradas en el obra.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el obra.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la comentario.
     */
    @GET
    @Path("{feedBacksId: \\d+}")
    public FeedBackDTO getFeedBack(@PathParam("paintworksId") Long paintworksId, @PathParam("feedBacksId") Long feedBacksId) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "feedbackResource getfeedback: input: {0}", feedBacksId);
        FeedBackEntity entity = feedBackLogic.getFeedBack(paintworksId, feedBacksId);
        if (entity == null) 
        {
            throw new WebApplicationException("El recurso con el id" + paintworksId + "/feedbacks/" + feedBacksId + " no existe.", 404);
        }
        FeedBackDTO feedBackDTO = new FeedBackDTO(entity);
        LOGGER.log(Level.INFO, "feedbackResource getfeedback: output: {0}", feedBackDTO);
        return feedBackDTO;
    }

    /**
     * Actualiza una comentario con la informacion que se recibe en el cuerpo de la
     * petición y se regresa el objeto actualizado.
     *
     * @param paintworksId El ID del obra del cual se guarda la comentario
     * @param feedBacksId El ID de la comentario que se va a actualizar
     * @param feedBack {@link FeedBackDTO} - La comentario que se desea guardar.
     * @return JSON {@link FeedBackDTO} - La comentario actualizada.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe la comentario.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la comentario.
     */
    @PUT
    @Path("{feedBacksId: \\d+}")
    public FeedBackDTO updateFeedBack(@PathParam("paintworksId") Long paintworksId, @PathParam("feedBacksId") Long feedBacksId, FeedBackDTO feedBack) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "feedbackResource updatefeedback: input: paintworksId: {0} , feedbacksId: {1} , feedback:{2}", new Object[]{paintworksId, feedBacksId, feedBack});
        if (!feedBacksId.equals(feedBack.getId()))
        {
            throw new BusinessLogicException("Los ids del feedback no coinciden.");
        }
        FeedBackEntity entity = feedBackLogic.getFeedBack(paintworksId, feedBacksId);
        if (entity == null) 
        {
            throw new WebApplicationException("No se encuentra el  recurso  con el path/paintworks/" + paintworksId + "/feedbacks/" + feedBacksId + " no existe.", 404);

        }
        FeedBackDTO feedBackDTO = new FeedBackDTO(feedBackLogic.updateFeedBack(paintworksId, feedBack.toEntity()));
        LOGGER.log(Level.INFO, "feedbackResource updatefeedback: output:{0}", feedBackDTO);
        return feedBackDTO;

    }

    /**
     * Borra la comentario con el id asociado recibido en la URL.
     *
     * @param paintworksId El ID del obra del cual se va a eliminar la comentario.
     * @param feedBacksId El ID de la comentario que se va a eliminar.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar la comentario.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la comentario.
     */
    @DELETE
    @Path("{feedBacksId: \\d+}")
    public void deleteFeedBack(@PathParam("paintworksId") Long paintworksId, @PathParam("feedBacksId") Long feedBacksId) throws BusinessLogicException 
    {
        FeedBackEntity entity = feedBackLogic.getFeedBack(paintworksId, feedBacksId);
        if (entity == null)
        {
            throw new WebApplicationException("El recurso no se enconttro", 404);
        }
        feedBackLogic.deleteFeedBack(paintworksId, feedBacksId);
    }

    /**
     * Lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos PrizeEntity a una lista de
     * objetos feedbackDTO (json)
     *
     * @param entityList corresponde a la lista de comentarios de tipo Entity que
     * vamos a convertir a DTO.
     * @return la lista de comentarios en forma DTO (json)
     */
    private List<FeedBackDTO> listEntity2DTO(List<FeedBackEntity> entityList)
    {
        List<FeedBackDTO> list = new ArrayList<>();
        for (FeedBackEntity entity : entityList)
        {
            list.add(new FeedBackDTO(entity));
        }
        return list;
    }
  
}

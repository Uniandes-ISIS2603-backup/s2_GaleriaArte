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
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.logging.Logger;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import java.util.logging.Level;
import javax.ws.rs.PathParam;
/**
 *
 * @author s.restrepos1 y ja.penat (Metodos get, update)
 */
import co.edu.uniandes.csw.galeriaarte.dtos.FeedBackDTO;
import co.edu.uniandes.csw.galeriaarte.ejb.FeedBackLogic;
import co.edu.uniandes.csw.galeriaarte.entities.FeedBackEntity;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.WebApplicationException;
@Path("feedBacks")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class FeedBackResources 
{
    private static final Logger LOGGER=Logger.getLogger(FeedBackResources.class.getName());
    
   @Inject
    FeedBackLogic feedBackLogic;
   
   
     /**
     * Crea una nueva calificacion con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     * @return JSON {@link FeedBackDTO} - La calificacion  guardada con el atributo
     * id autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe comentario con el mismo id.
     */
    @POST
    public FeedBackDTO createFeedBack(FeedBackDTO feedBack) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "FeedBackResource createFeedBack: input: {0}",feedBack .toString());
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        FeedBackEntity feedBackEntity = feedBack.toEntity();
        // Invoca la lógica para crear el feedback nueva
       feedBackLogic.createFeedBack(feedBack.toEntity());
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        FeedBackDTO nuevoFeedBackDTO = new FeedBackDTO(feedBackEntity);
        LOGGER.log(Level.INFO, "CategoryResource createCategory: output: {0}", nuevoFeedBackDTO.toString());
        return nuevoFeedBackDTO;
    }
    @GET
    @Path("{feedBackId: \\d+}")
    public FeedBackDTO getFeedBack(@PathParam("feedBackId") Long feedBackId) throws WebApplicationException
    {
        LOGGER.log(Level.INFO, "FeedBackResource getFeedBack: input: {0}", feedBackId);
        FeedBackEntity feedEntity = feedBackLogic.getFeedBack(feedBackId);
        if (feedEntity == null) 
        {
            throw new WebApplicationException("El recurso /feedbacks/" + feedBackId + " no existe.", 404);
        }
        FeedBackDTO detailDTO = new FeedBackDTO(feedEntity);
        LOGGER.log(Level.INFO, "FeedBackResource getFeedBack: output: {0}", detailDTO.toString());
        return detailDTO;
    }
    
    
    /**
     * Busca y devuelve todas las categorias que existen en la aplicacion.
     *
     * @return JSONArray {@link EditorialDTO} - Las categorias encontradas en
     * la aplicación. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<FeedBackDTO> getFeedBacks() 
    {
        LOGGER.info("FeedBackResource getFeedBacks: input: void");
        List<FeedBackDTO> listaFeedBacks = listEntity2DetailDTO(feedBackLogic.getFeedBacks());
        LOGGER.log(Level.INFO, "FeedBackResource getCategories: output: {0}", listaFeedBacks.toString());
        return listaFeedBacks;
    }
    
    /**
     * Actualiza feedBack con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param feedBackId Identificador del feedBack que se desea
     * actualizar. Este debe ser una cadena de dígitos.
     * @param feedBack {@link FeedBackDTO} El feedBack que se desea guardar.
     * @return JSON {@link FeedBackDTO} - El feedBack  guardad.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el feedBack a
     * actualizar.
     */
    @PUT
    @Path("{feedBackId: \\d+}")
    public FeedBackDTO updateFeedBack(@PathParam("feedBackId") Long feedBackId, FeedBackDTO feedBack) throws WebApplicationException, BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "FeedBackResource updateFeedBack: input: id:{0} , feedBack: {1}", new Object[]{feedBackId, feedBack.toString()});
        feedBack.setId(feedBackId);
        if (feedBackLogic.getFeedBack(feedBackId) == null)
        {
            throw new WebApplicationException("El recurso /feedBack/" + feedBackId + " no existe.", 404);
        }
        FeedBackDTO detailDTO = new FeedBackDTO(feedBackLogic.updateFeedBack(feedBackId, feedBack.toEntity()));
        LOGGER.log(Level.INFO, "FeedBackResource  updateFeedBack: output: {0}", detailDTO.toString());
        return detailDTO;
    }

     /**
     * Borra la calificacion con el id asociado recibido en la URL.
     * @param feedId Identificador de la calificacion que se desea borrar.
     * Este debe ser una cadena de dígitos.
     */
    @DELETE
    @Path("{feedBackId: \\d+}")
    public void deleteFeedBack(@PathParam("feedBackId") Long feedBackId)
    {
     LOGGER.log(Level.INFO, "FeedBackResource deleteFeedBack: input: {0}", feedBackId);
    
        if (feedBackLogic.getFeedBack(feedBackId) == null)
        {
            throw new WebApplicationException("El recurso /feedBacks/" + feedBackId + " no existe.", 404);
        }
        feedBackLogic.deleteFeedBack(feedBackId);
       
        LOGGER.info("FeedBackResource deleteFeedBack: output: void");
    }
      
    
    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos FeedBacklEntity a una lista de
     * objetos FeedBackDTO (json)
     *
     * @param entityList corresponde a la lista de comentarios de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de comentarios en forma DTO (json)
     */
    private List<FeedBackDTO> listEntity2DetailDTO(List<FeedBackEntity> entityList)
    {
        List<FeedBackDTO> list = new ArrayList<>();
        for (FeedBackEntity entity : entityList) 
        {
            list.add(new FeedBackDTO(entity));
        }
        return list;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.resources;

import co.edu.uniandes.csw.galeriaarte.dtos.PaintworkDTO;
import co.edu.uniandes.csw.galeriaarte.entities.PaintworkEntity;
import co.edu.uniandes.csw.galeriaarte.ejb.PaintworkLogic;
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
 *
 * @author jf.copete
 */

@Path("paintwork")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class PaintworkResource {
    
private static final Logger LOGGER = Logger.getLogger(PaintworkResource.class.getName());

    @Inject
    PaintworkLogic paintworkLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Crea una nueva editorial con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     * @param paintwork {@link EditorialDTO} - La editorial que se desea
     * guardar.
     * @return JSON {@link EditorialDTO} - La editorial guardada con el atributo
     * id autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe la editorial.
     */
    @POST
    public PaintworkDTO createPaintWork(PaintworkDTO paintwork) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "PaintworkResource createPaintWork: input: {0}", paintwork.toString());
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        PaintworkEntity paintworkEntity = paintwork.toEntity();
        // Invoca la lógica para crear la pintura nueva
        PaintworkEntity nuevoPaintworkEntity = paintworkLogic.createPaintWork(paintworkEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        PaintworkDTO nuevoPaintworkDTO = new PaintworkDTO(paintworkEntity);
        LOGGER.log(Level.INFO, "PaintworkResource createPaintWork: input: {0}", nuevoPaintworkDTO.toString());
        return nuevoPaintworkDTO;
    }

    /**
     * Busca y devuelve todas las editoriales que existen en la aplicacion.
     *
     * @return JSONArray {@link EditorialDTO} - Las editoriales encontradas en
     * la aplicación. Si no hay ninguna retorna una lista vacía.
     */
    
    
    @GET
    public List<PaintworkDTO> getPaintworks() {
        LOGGER.info("PaintworkResource getPaintworks: input: void");
        List<PaintworkDTO> listaPaintworks = listEntity2DetailDTO(paintworkLogic.getPaintworks());
        LOGGER.log(Level.INFO, "EditorialResource getEditorials: output: {0}", listaPaintworks.toString());
        return listaPaintworks;
    }

    /**
     * Busca la obra con el id asociado recibido en la URL y la devuelve.
     *
     * @param paintworkId Identificador de la editorial que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link EditorialDTO} - La editorial buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la editorial.
     */
    @GET
    @Path("{paintworksId: \\d+}")
    public PaintworkDTO getPaintwork(@PathParam("paintworksId") Long paintworkId) throws WebApplicationException {
        LOGGER.log(Level.INFO, "PaintworkResource getPaintwork: input: {0}", paintworkId);
        PaintworkEntity paintworkEntity = paintworkLogic.getPaintWork(paintworkId);
        if (paintworkEntity == null) {
            throw new WebApplicationException("El recurso /editorials/" + paintworkId + " no existe.", 404);
        }
        PaintworkDTO detailDTO = new PaintworkDTO(paintworkEntity);
        LOGGER.log(Level.INFO, "PaintworkResource getPaintwork: output: {0}", detailDTO.toString());
        return detailDTO;
    }

    /**
     * Actualiza la editorial con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param paintworkId Identificador de la editorial que se desea
     * actualizar. Este debe ser una cadena de dígitos.
     * @param paintwork1 {@link EditorialDTO} La editorial que se desea guardar.
     * @return JSON {@link EditorialDTO} - La editorial guardada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la editorial a
     * actualizar.
     */
    @PUT
    @Path("{painworksId: \\d+}")
    public PaintworkDTO updatePaintwork(@PathParam("painworksId") Long paintworkId, PaintworkDTO paintwork1) throws WebApplicationException {
        LOGGER.log(Level.INFO, "PaintworkResource updatePaintwork: input: id:{0} , paintwork: {1}", new Object[]{paintworkId, paintwork1.toString()});
        paintwork1.setIdPaintwork(paintworkId);
        if (paintworkLogic.getPaintWork(paintworkId) == null) {
            throw new WebApplicationException("El recurso /paintworks/" + paintworkId + " no existe.", 404);
        }
        PaintworkDTO detailDTO = new PaintworkDTO(paintworkLogic.updatePaintWork(paintworkId, paintwork1.toEntity()));
        LOGGER.log(Level.INFO, "PaintworkResource updatePaintwork: output: {0}", detailDTO.toString());
        return detailDTO;
    }

    /**
     * Borra la editorial con el id asociado recibido en la URL.
     *
     * @param paintworkId Identificador de la editorial que se desea borrar.
     * Este debe ser una cadena de dígitos.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar la editorial.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la editorial.
     */
    @DELETE
    @Path("{painworksId: \\d+}")
    public void deletePaintwork(@PathParam("painworksId") Long paintworkId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "PaintworkResource updatePaintwork: input: {0}", paintworkId);
        if (paintworkLogic.getPaintWork(paintworkId) == null) {
            throw new WebApplicationException("El recurso /editorials/" + paintworkId + " no existe.", 404);
        }
        paintworkLogic.deletePaintWork(paintworkId);
        LOGGER.info("PaintworkResource updatePaintwork: output: void");
    }

    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos EditorialEntity a una lista de
     * objetos EditorialDTO (json)
     *
     * @param entityList corresponde a la lista de editoriales de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de editoriales en forma DTO (json)
     */
    private List<PaintworkDTO> listEntity2DetailDTO(List<PaintworkEntity> entityList) {
        List<PaintworkDTO> list = new ArrayList<>();
        for (PaintworkEntity entity : entityList) {
            list.add(new PaintworkDTO(entity));
        }
        return list;
    }
    
    
}

/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package co.edu.uniandes.csw.galeriaarte.resources;

import co.edu.uniandes.csw.galeriaarte.dtos.MedioPagoDTO;
import co.edu.uniandes.csw.galeriaarte.ejb.MedioPagoLogic;
import co.edu.uniandes.csw.galeriaarte.entities.MedioPagoEntity;
import co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.logging.Logger;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import java.util.logging.Level;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;

/**
 * Clase que implementa el recurso "MedioPago".
 * @author ja.penat
 * @version 1.0
 */

@Path("medioPagos")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class MedioPagoResource
{
    
    private static final Logger LOGGER = Logger.getLogger(MedioPagoResource.class.getName());
    
    
    @Inject
            MedioPagoLogic medioPagoLogic;
    
    /**
     * Crea un nuevo medio de pago con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     * @param medioPago {@link MedioPagoDTO} - El medio de pago  que se desea
     * guardar.
     * @return JSON {@link MedioPagoDTO} - El medio de pago  guardado con el atributo
     * id autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe el metodo de pago con el mismo id.
     */
    @POST
    public MedioPagoDTO createMedioPago(MedioPagoDTO medioPago) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "MedioPagoResource createMedioPago: input: {0}", medioPago.toString());
        MedioPagoEntity MedioPagoEntity = medioPago.toEntity();
        MedioPagoEntity nuevoMedioPagoEntity = medioPagoLogic.createMedioPago(MedioPagoEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        MedioPagoDTO nuevoMedioPagoDTO = new MedioPagoDTO(nuevoMedioPagoEntity);
        LOGGER.log(Level.INFO, "MedioPagoResource createMedioPago: output: {0}", nuevoMedioPagoDTO.toString());
        return nuevoMedioPagoDTO;
    }
    
    /**
     * Busca y devuelve todas los medios de pago que existen en la aplicacion.
     *
     * @return JSONArray {@link MedioPagoDTO} - Los medios de pago encontradas en
     * la aplicación. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<MedioPagoDTO> getMediosPago() {
        LOGGER.info("MedioPagoResource MedioPago: input: void");
        List<MedioPagoDTO> listaMedioPago = listEntity2DTO(medioPagoLogic.getMediosPago());
        LOGGER.log(Level.INFO, "MedioPagoResource getMediosPago: output: {0}", listaMedioPago.toString());
        return listaMedioPago;
    }
    
    /**
     * Busca la medio pago con el id asociado recibido en la URL y la devuelve.
     *
     * @param medioPagoId Identificador de medios de pago que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link MedioPagoDTO} - ¿El medio de pago buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el medio de pago.
     */
    @GET
    @Path("{MedioPagoId: \\d+}")
    public MedioPagoDTO getMedioPago(@PathParam("medioPagoId") Long medioPagoId) throws WebApplicationException
    {
        LOGGER.log(Level.INFO, "MedioPagoResource getMedioPago: input: {0}", medioPagoId);
        MedioPagoEntity medioPagoEntity = medioPagoLogic.getMedioPago(medioPagoId);
        if (medioPagoEntity == null)
        {
            throw new WebApplicationException("El recurso /MedioPagos/" + medioPagoId + " no existe.", 404);
        }
        MedioPagoDTO detailDTO = new MedioPagoDTO(medioPagoEntity);
        LOGGER.log(Level.INFO, "MedioPagoResource getMedioPago: output: {0}", detailDTO.toString());
        return detailDTO;
    }
    
    /**
     * Actualiza el medio de pago  con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param medioPagoId Identificador de la  que se desea
     * actualizar. Este debe ser una cadena de dígitos.
     * @param medioPago {@link MedioPagoDTO} el medio de pago que se desea guardar.
     * @return JSON {@link MedioPagoDTO} - El medio de pago guardado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el medio de pago  a
     * actualizar.
     */
    @PUT
    @Path("{medioPagoId: \\d+}")
    public MedioPagoDTO updateMedioPago(@PathParam("medioPagoId") Long medioPagoId, MedioPagoDTO medioPago) throws WebApplicationException, BusinessLogicException
    {
        LOGGER.log(Level.INFO, "MedioPagoResource updateMedioPago: input: id:{0} , MedioPago {1}", new Object[]{medioPagoId, medioPago.toString()});
        medioPago.setId(medioPagoId);
        if (medioPagoLogic.getMedioPago(medioPagoId) == null)
        {
            throw new WebApplicationException("El recurso /medioPagos/" + medioPagoId + " no existe.", 404);
        }
        MedioPagoDTO detailDTO = new MedioPagoDTO(medioPagoLogic.updateMedioPago(medioPagoId, medioPago.toEntity()));
        LOGGER.log(Level.INFO, "MedioPagoResource updateMedioPago: output: {0}", detailDTO.toString());
        return detailDTO;
    }
    
    
    /**
     * Borra el medio de pago con el id asociado recibido en la URL.
     * @param medioPagoId Identificador del medio de pago que se desea borrar.
     * Este debe ser una cadena de dígitos.
     */
    @DELETE
    @Path("{medioPagoId: \\d+}")
    public void deleteMedioPago(@PathParam("medioPagoId") Long medioPagoId)
    {
        LOGGER.log(Level.INFO, "MedioPagoResource deleteMedioPago: input: {0}", medioPagoId);
        if (medioPagoLogic.getMedioPago(medioPagoId) == null)
        {
            throw new WebApplicationException("El recurso /medioPagos/" + medioPagoId + " no existe.", 404);
        }
        medioPagoLogic.deleteMedioPago(medioPagoId);
        LOGGER.info("MedioPagoResource deleteMedioPago: output: void");
    }
    
    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos MedioPagoEntity a una lista de
     * objetos MedioPagoDTO (json)
     *
     * @param entityList corresponde a la lista medioPago de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de extraServices en forma DTO (json)
     */
    private List<MedioPagoDTO> listEntity2DTO(List<MedioPagoEntity> entityList) 
    {
        List<MedioPagoDTO> list = new ArrayList<MedioPagoDTO>();
        for (MedioPagoEntity entity : entityList) 
        {
            list.add(new MedioPagoDTO(entity));
        }
        return list;
    }
}

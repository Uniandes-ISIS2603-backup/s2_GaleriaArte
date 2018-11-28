/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package co.edu.uniandes.csw.galeriaarte.resources;



import co.edu.uniandes.csw.galeriaarte.dtos.ExtraServiceDTO;
import co.edu.uniandes.csw.galeriaarte.ejb.ExtraServiceLogic;
import co.edu.uniandes.csw.galeriaarte.entities.ExtraServiceEntity;
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
 * Clase que implementa el recurso "ExtraService".
 * @author ja.penat
 * @version 1.0
 */
@Path("extraServices")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ExtraServiceResource
{
    
    private static final Logger LOGGER = Logger.getLogger(ExtraServiceResource.class.getName());
    
    
    @Inject
    ExtraServiceLogic extraServiceLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.
    
    /**
     * Crea un nuevo servicio extra con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     * @param extraService {@link MedioPagoDTO} - El servicio extra   que se desea
     * guardar.
     * @return JSON {@link ExtraServiceDTO} - El servicio extra  guardado con el atributo
     * id autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe el metodo de pago con el mismo id.
     */
    @POST
    public ExtraServiceDTO createExtraService(ExtraServiceDTO extraService) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "ExtraServiceResource createExtraService: input: {0}", extraService);
        ExtraServiceEntity extraServiceEntity = extraService.toEntity();
        ExtraServiceEntity nuevoExtraServiceEntity = extraServiceLogic.createExtraService(extraServiceEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        ExtraServiceDTO nuevoExtraServiceDTO = new ExtraServiceDTO(nuevoExtraServiceEntity);
        LOGGER.log(Level.INFO, "ExtraServiceResource createExtraService: output: {0}", nuevoExtraServiceDTO);
        return nuevoExtraServiceDTO;
    }
    
     /**
     * Busca y devuelve todas los servicios extra que existen en la aplicacion.
     *
     * @return JSONArray {@link ExtraServiceDTO} - Los servicios extras encontradas en
     * la aplicación. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<ExtraServiceDTO> getExtraServices() {
        LOGGER.info("ExtraServiceResource getExtraService: input: void");
        List<ExtraServiceDTO> listaExtraService = listEntity2DTO(extraServiceLogic.getExtraServices());
        LOGGER.log(Level.INFO, "ExtraServiceResource getExtraService: output: {0}", listaExtraService);
        return listaExtraService;
    }

    /**
     * Busca la extraService con el id asociado recibido en la URL y la devuelve.
     *
     * @param extraServiceId Identificador de servicio extra que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link ExtraServiceDTO} - ¿El sevicio extra buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el servicio extra.
     */
    @GET
    @Path("{extraServiceId: \\d+}")
    public ExtraServiceDTO getExtraService(@PathParam("extraServiceId") Long extraServiceId) 
    {
        LOGGER.log(Level.INFO, "ExtraServiceResource getExtraService: input: {0}", extraServiceId);
        ExtraServiceEntity extraServiceEntity = extraServiceLogic.getExtraService(extraServiceId);
        if (extraServiceEntity == null)
        {
            throw new WebApplicationException("El recurso /extraServices/" + extraServiceId + " no esta.", 404);
        }
        ExtraServiceDTO detailDTO = new ExtraServiceDTO(extraServiceEntity);
        LOGGER.log(Level.INFO, "ExtraServiceResource getExtraService: output: {0}", detailDTO);
        return detailDTO;
    }


    /**
     * Borra el servicio extra  con el id asociado recibido en la URL.
     * @param extraServiceId Identificador del servicio extra que se desea borrar.
     * Este debe ser una cadena de dígitos.
     */
    @DELETE
    @Path("{extraServiceId: \\d+}")
    public void deleteExtraService(@PathParam("extraServiceId") Long extraServiceId) {
        LOGGER.log(Level.INFO, "ExtraServiceResource deleteExtraService: input: {0}", extraServiceId);
        if (extraServiceLogic.getExtraService(extraServiceId) == null) 
        {
            throw new WebApplicationException("El recurso /editorials/" + extraServiceId + " no existe.", 404);
        }
        extraServiceLogic.deleteExtraService(extraServiceId);
        LOGGER.info("ExtraServiceResource deleteExtraService: output: void");
    }
    
    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos ExtraServiceEntity a una lista de
     * objetos ExtraServiceDTO (json)
     *
     * @param entityList corresponde a la lista de extraService de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de extraServices en forma DTO (json)
     */
    private List<ExtraServiceDTO> listEntity2DTO(List<ExtraServiceEntity> entityList) 
    {
        List<ExtraServiceDTO> list = new ArrayList<>();
        for (ExtraServiceEntity entity : entityList) 
        {
            list.add(new ExtraServiceDTO(entity));
        }
        return list;
    }

    @PUT
    @Path(value = "{extraServiceId: \\d+}")
    private ExtraServiceDTO updateExtraService(@PathParam("extraServiceId") Long extraServiceId, ExtraServiceDTO extraService) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "ExtraServiceResource updateExtraService: input: id:{0} , xtraService: {1}", new Object[]{extraServiceId, extraService});
        extraService.setId(extraServiceId);
        if (extraServiceLogic.getExtraService(extraServiceId) == null)
        {
            throw new WebApplicationException("El recurso /extraServices/" + extraServiceId + " no se encuentra.", 404);
        }
        ExtraServiceDTO detailDTO = new ExtraServiceDTO(extraServiceLogic.updateExtraService(extraServiceId, extraService.toEntity()));
        LOGGER.log(Level.INFO, "ExtraServiceResource updateExtraService: output: {0}", detailDTO);
        return detailDTO;
    }
}
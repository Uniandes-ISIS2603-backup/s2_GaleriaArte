/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package co.edu.uniandes.csw.galeriaarte.resources;



import co.edu.uniandes.csw.galeriaarte.dtos.ExtraServiceDTO;
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
    // ExtraServiceLogic extraServiceLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.
    
    /**
     * Crea un nuevo medio de pago con la informacion que se recibe en el cuerpo de
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
        LOGGER.log(Level.INFO, "MedioPagoResource createMedioPago: input: {0}", extraService.toString());
        return extraService;
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
        LOGGER.info("ExtraServiceResource deleteExtraService: output: void");
    }
}

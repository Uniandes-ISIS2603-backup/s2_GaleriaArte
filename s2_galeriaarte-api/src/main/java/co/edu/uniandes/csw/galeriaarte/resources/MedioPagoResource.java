/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package co.edu.uniandes.csw.galeriaarte.resources;

import co.edu.uniandes.csw.galeriaarte.dtos.MedioPagoDTO;
import co.edu.uniandes.csw.galeriaarte.ejb.MedioPagoLogic;
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
        medioPagoLogic.createMedioPago(medioPago.toEntity());
        return medioPago;
    }
    
    /**
     * Borra el medio de pago con el id asociado recibido en la URL.
     * @param medioPagoId Identificador del medio de pago que se desea borrar.
     * Este debe ser una cadena de dígitos.
     */
    @DELETE
    @Path("{medioPagoId: \\d+}")
    public void deleteMedioPago(@PathParam("medioPagoId") Long medioPagoId) {
        LOGGER.log(Level.INFO, "MedioPagoResource deleteMedioPago: input: {0}", medioPagoId);
        LOGGER.info("MedioPagoResource deleteMedioPago: output: void");
    }
}

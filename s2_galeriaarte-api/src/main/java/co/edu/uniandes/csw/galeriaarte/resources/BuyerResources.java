/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.resources;
import co.edu.uniandes.csw.galeriaarte.dtos.BuyerDTO;
import co.edu.uniandes.csw.galeriaarte.entities.BuyerEntity;
import co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.galeriaarte.ejb.BuyerLogic;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author Sara Acosta 
 */
@Path("buyers")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class BuyerResources {
    @Inject
    BuyerLogic buyerLogic;
    
    // COMPLETAR
    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(BuyerResources.class.getName());
    
    @POST
    public BuyerDTO createBuyer(BuyerDTO buyer) throws BusinessLogicException{
        //Convierte el DTO (json) es un objeto Entity para ser manejado por la lógica.
    
        LOGGER.info("BuyerResource createBuyer: input:"+buyer.toString());
        BuyerEntity buyerEntity = buyer.toEntity( );
        // Invoca la lógica para crear el comprador nuevo
        BuyerEntity nuevoBuyerEntity = buyerLogic.createBuyer(buyerEntity);
        // Como debe retornar un DTO(jso) se invoca el constructor del DTO con el argumento el entity nuevo
        BuyerDTO nuevoBuyerDTO = new BuyerDTO(nuevoBuyerEntity);
        LOGGER.info("BuyerResource createBuyer: output:"+nuevoBuyerDTO.toString());
        
        return nuevoBuyerDTO;
    }
    

    
    
   
    
   
    
}


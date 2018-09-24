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
import java.util.logging.Level;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.Path;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author Sara Acosta s.acostav
 */
@Path("buyers")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class BuyerResources {
    @Inject
    BuyerLogic buyerLogic;
    
    
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
    
    @GET 
    @Path("buyers://d+")
    public BuyerDTO getBuyer(@PathParam ("/buyers")Long idBuyer) throws WebApplicationException{
      
        LOGGER.log(Level.INFO, "BuyerResource getBuyer: input: {0}", idBuyer);
        BuyerEntity buyerEntity = buyerLogic.getBuyer(idBuyer);
        if(buyerEntity==null){
            throw new WebApplicationException("El recurso /buyers/"+idBuyer+"no existe", 404);
        }
        
        BuyerDTO buyerDTO = new BuyerDTO(buyerEntity);
        LOGGER.log(Level.INFO,"BuyerResource getBuyer : output : {0}", buyerDTO.toString());
        
        return buyerDTO;
    }
    
    
    @DELETE
    @Path ("{buyerId: \\d+}")
    
    public void deleteBuyer(@PathParam ("buyerID") Long buyerID, BuyerDTO buyer){ 
            LOGGER.log(Level.INFO, "BuyerResource deleteBuyer: input: buyerID: {0}, buyer: {1}", new Object[]{ buyerID, buyer.toString()});
            if(buyerLogic.getBuyer(buyerID)==null){
                throw new WebApplicationException("El recurso /buyer/"+buyerID+"no existe", 404);
            }
            
            buyerLogic.deleteBuyer(buyerID);
            LOGGER.info("BuyerResource deleteBuyer: output: void");
    }
   
    @PUT 
    @Path("{buyerID: \\d+}")
    public BuyerDTO updateBuyer(@PathParam("buyerID") Long buyerID, BuyerDTO buyer){
        
        LOGGER.log(Level.INFO, "BuyerResource updateBuyer: input: buyerID: {0}, buyer: {1}", new Object[]{buyerID, buyer.toString()});
        buyer.setIdUser(buyerID);
        if(buyerLogic.getBuyer(buyerID)==null){
            throw new WebApplicationException("El recurso /buyer/"+buyerID+"no existe", 404);
        }
        
        BuyerDTO buyerDTO = new BuyerDTO(buyerLogic.updateBuyer(buyerID, buyer.toEntity()));
        
        LOGGER.log(Level.INFO, "BuyerResource updateBuyer: output: {0}", buyerDTO.toString());
        
        return buyerDTO;
    }
    
    private List<BuyerDTO> listEntiy(List<BuyerEntity> entityList){
        List<BuyerDTO> lista = new ArrayList<>();
        for(BuyerEntity entity: entityList){
            lista.add(new BuyerDTO(entity));
        }
        return lista;
    }
 
    
}

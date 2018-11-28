/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.resources;
import co.edu.uniandes.csw.galeriaarte.dtos.BuyerDTO;
import co.edu.uniandes.csw.galeriaarte.entities.BuyerEntity;
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
    public BuyerDTO createBuyer(BuyerDTO buyer) 
    {
        //Convierte el DTO (json) es un objeto Entity para ser manejado por la lógica.
    
        LOGGER.log(Level.INFO, "BuyerResource createBuyer: input:{0}", buyer);
        BuyerEntity buyerEntity = buyer.toEntity( );
        // Invoca la lógica para crear el comprador nuevo
        BuyerEntity nuevoBuyerEntity = buyerLogic.createBuyer(buyerEntity);
        // Como debe retornar un DTO(jso) se invoca el constructor del DTO con el argumento el entity nuevo
        BuyerDTO nuevoBuyerDTO = new BuyerDTO(nuevoBuyerEntity);
        LOGGER.log(Level.INFO, "BuyerResource createBuyer: output:{0}", nuevoBuyerDTO);
        
        return nuevoBuyerDTO;
    }
    
    @GET
    @Path("{buyerId: \\d+}")
    public BuyerDTO  getBuyer(@PathParam("buyerId") Long idBuyer)
    {
        LOGGER.log(Level.INFO, "BuyerResource getBuyer: input: {0}", idBuyer);
        BuyerEntity buyerEntity = buyerLogic.getBuyer(idBuyer);
        if(buyerEntity==null)
        {
            throw new WebApplicationException("El recurso /buyers/"+idBuyer+"no se encuentra", 404);
        }
        
        BuyerDTO buyerDTO = new BuyerDTO(buyerEntity);
        LOGGER.log(Level.INFO,"BuyerResource getBuyer : output : {0}", buyerDTO);
        
        return buyerDTO;
    }
    
    /**
     * Busca y devuelve todas las buyer que existen en la aplicacion.
     *
     * @return JSONArray {@link BuyerDTO} - Las categorias encontradas en
     * la aplicación. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<BuyerDTO> getBuyers() 
    {
        LOGGER.info("BuyerResource getBuyers: input: void");
        List<BuyerDTO> listaBuyers = listEntity2DetailDTO(buyerLogic.getBuyers());
        LOGGER.log(Level.INFO, "BuyerResource getBuyers: output: {0}", listaBuyers);
        return listaBuyers;
    }
    
    
    @DELETE
    @Path ("{buyerId: \\d+}")
    public void deleteBuyer(@PathParam ("buyerId") Long buyerID)
    { 
             LOGGER.log(Level.INFO, "BuyerResource deleteBuyer input: {0}", buyerID);
            if(buyerLogic.getBuyer(buyerID)==null){
                throw new WebApplicationException("El recurso /buyer/"+buyerID+"no esta", 404);
            }
            
            buyerLogic.deleteBuyer(buyerID);
            LOGGER.info("BuyerResource deleteBuyer: output: void");
    }
   
    @PUT 
    @Path("{buyerID: \\d+}")
    public BuyerDTO updateBuyer(@PathParam("buyerID") Long buyerID, BuyerDTO buyer)
    {    
        LOGGER.log(Level.INFO, "BuyerResource updateBuyer: input: buyerID: {0}, buyer: {1}", new Object[]{buyerID, buyer});
        buyer.setId(buyerID);
        if(buyerLogic.getBuyer(buyerID)==null)
        {
            throw new WebApplicationException("El recurso /buyer/"+buyerID+"no existe", 404);
        }
        
        BuyerDTO buyerDTO = new BuyerDTO(buyerLogic.updateBuyer(buyerID, buyer.toEntity()));
        
        LOGGER.log(Level.INFO, "BuyerResource updateBuyer: output: {0}", buyerDTO);
        
        return buyerDTO;
    }
    
    @Path("{buyersId: \\d+}/sales")
    public Class<BuyerSalesResource> getBuyerSalesResource(@PathParam("buyersId") Long buyersId) {
        if (buyerLogic.getBuyer(buyersId) == null) {
            throw new WebApplicationException("El recurso /buyers/" + buyersId + " no existe.", 404);
        }
        return BuyerSalesResource.class;
    }
    
    @Path("{buyersId: \\d+}/paintworks")
    public Class<BuyerPaintworksResources> getBuyerPaintworksResource(@PathParam("buyersId") Long buyersId) {
        if (buyerLogic.getBuyer(buyersId) == null) {
            throw new WebApplicationException("El recurso /buyers/" + buyersId + " no existe.", 404);
        }
        return BuyerPaintworksResources.class;
    }
  
    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos BuyerEntity a una lista de
     * objetos BuyerDTO (json)
     *
     * @param entityList corresponde a la lista de Buyers de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de buyers en forma DTO (json)
     */
    private List<BuyerDTO> listEntity2DetailDTO(List<BuyerEntity> entityList) {
        List<BuyerDTO> list = new ArrayList<>();
        for (BuyerEntity entity : entityList) {
            list.add(new BuyerDTO(entity));
        }
        return list;
    }
    
    
    
}
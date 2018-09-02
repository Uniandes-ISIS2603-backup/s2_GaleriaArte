/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.resources;
import co.edu.uniandes.csw.galeriaarte.dtos.BuyerDTO;
import co.edu.uniandes.csw.galeriaarte.dtos.CVDTO;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author Sara Acosta 
 */
public class BuyerResources {
    
    @POST
    
    public BuyerDTO createBuyer(BuyerDTO buyer){
        return null;
    }
    
    @GET
    @Path("buyers://d+")
    public BuyerDTO getBuyerByidUser(@PathParam("/buyers") Long idPaintwork) throws WebApplicationException {
        return null;
    }
    
    
}


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.resources;

import co.edu.uniandes.csw.galeriaarte.dtos.PaintworkDTO;
import co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author jf.copete
 */

@Path("paintwork")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class PaintworkResource {
    
    
    @POST
    public PaintworkDTO createPaintwork(PaintworkDTO paintwork) throws BusinessLogicException
    {
        return paintwork;
    }
    
}

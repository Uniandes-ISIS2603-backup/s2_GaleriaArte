/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.resources;

import co.edu.uniandes.csw.galeriaarte.dtos.CVDTO;
import co.edu.uniandes.csw.galeriaarte.dtos.KindDTO;
import co.edu.uniandes.csw.galeriaarte.ejb.CVLogic;
import co.edu.uniandes.csw.galeriaarte.ejb.KindLogic;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author LauraManrique
 */
@Path("kinds")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped

public class KindResources {
    @Inject 
KindLogic kindLogic;
     /**
     * Crea un tipo
     *
     * @param KindDTO
     * @return
     */
    @POST
    public KindDTO createKind(KindDTO kind) {
        return kind;
    }

    @GET
    @Path("kinds://d+")
    public KindDTO getKindByIdType(@PathParam("/kinds") Long idType) throws WebApplicationException {
        return null;
    }
}

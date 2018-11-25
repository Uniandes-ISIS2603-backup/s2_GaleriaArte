package co.edu.uniandes.csw.galeriaarte.resources;

import co.edu.uniandes.csw.galeriaarte.dtos.CVDTO;
import co.edu.uniandes.csw.galeriaarte.ejb.CVLogic;
import co.edu.uniandes.csw.galeriaarte.entities.CVEntity;
import co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

@Path("CVs")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped

public class CVResources
{
    
    
}



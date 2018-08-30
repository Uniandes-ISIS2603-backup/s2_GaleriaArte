package co.edu.uniandes.csw.galeriaarte.resources;

import javax.enterprise.context.RequestScoped;
import co.edu.uniandes.csw.galeriaarte.dtos.SaleDTO;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

@Path("sales")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class SaleResources
{
        @POST
	public SaleDTO createSaleDTO(SaleDTO pSaleDTO)
	{
		return pSaleDTO;
	}
               
}

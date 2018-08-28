package co.edu.uniandes.csw.galeriaarte.resources;

import javax.enterprise.context.RequestScoped;
import co.edu.uniandes.csw.galeriaarte.dtos.SaleDTO;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("extraServices")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class SaleResources
{
	public SaleDTO createSaleDTO(SaleDTO pSaleDTO)
	{
		return pSaleDTO;
	}
}

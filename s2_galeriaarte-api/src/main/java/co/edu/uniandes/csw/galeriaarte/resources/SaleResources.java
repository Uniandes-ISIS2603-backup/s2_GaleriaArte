package co.edu.uniandes.csw.galeriaarte.resources;

import javax.enterprise.context.RequestScoped;
import co.edu.uniandes.csw.galeriaarte.dtos.SaleDTO;
import co.edu.uniandes.csw.galeriaarte.ejb.SaleLogic;
import co.edu.uniandes.csw.galeriaarte.entities.SaleEntity;
import co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @author s.restrepos1
 */
@Path("sales")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class SaleResources
{
    private static final Logger LOGGER= Logger.getLogger(SaleResources.class.getName());
    @Inject 
    SaleLogic saleLogic;
        @POST
	public SaleDTO createSaleDTO(SaleDTO pSaleDTO) throws BusinessLogicException
	{
		LOGGER.log(Level.INFO, "SaleResource createSale: input: {0}",pSaleDTO.toString());
                SaleEntity saleEntity= pSaleDTO.toEntity();
                SaleEntity nuevoSaleEntity= saleLogic.createSale(saleEntity);
                
                SaleDTO nuevoSaleDTO = new SaleDTO(saleEntity);
                LOGGER.log(Level.INFO, "SaleResource createSale: input: {0}", nuevoSaleDTO.toString());
                return nuevoSaleDTO;
        }
        
               
}

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
    
    /**
     * Crea una nueva compra con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     * @param pSaleDTO {@link SaleDTO} - La compra que se desea
     * guardar.
     * @return JSON {@link SaleDTO} - La compra  guardada con el atributo
     * id autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe compra con el mismo id.
     */
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

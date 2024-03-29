package co.edu.uniandes.csw.galeriaarte.resources;

import javax.enterprise.context.RequestScoped;
import co.edu.uniandes.csw.galeriaarte.dtos.SaleDTO;
import co.edu.uniandes.csw.galeriaarte.ejb.SaleLogic;
import co.edu.uniandes.csw.galeriaarte.entities.SaleEntity;
import co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
/**
 *
 * @author ja.penat
 */
@Path("sales")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class SaleResources
{
    
    private static final Logger LOGGER = Logger.getLogger(SaleResources.class.getName());
    
    
    @Inject
            SaleLogic saleLogic;
    
    /**
     * Crea un nuevo la venta con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     * @param Sale {@link SaleDTO} - El la venta  que se desea
     * guardar.
     * @return JSON {@link SaleDTO} - El la venta  guardado con el atributo
     * id autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe el metodo de pago con el mismo id.
     */
    @POST
    public void createSale(SaleDTO sale) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "SaleResource createSale: input: {0}", sale);
          sale.toEntity();
        
    }
    
    /**
     * Busca y devuelve todas los medios de pago que existen en la aplicacion.
     *
     * @return JSONArray {@link SaleDTO} - Los medios de pago encontradas en
     * la aplicación. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<SaleDTO> getSales() {
        LOGGER.info("SaleResource Sale: input: void");
        List<SaleDTO> listaSale = listEntity2DTO(saleLogic.getSales());
        LOGGER.log(Level.INFO, "SaleResource getSales: output: {0}", listaSale);
        return listaSale;
    }
    
    /**
     * Busca la medio pago con el id asociado recibido en la URL y la devuelve.
     *
     * @param saleId Identificador de medios de pago que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link SaleDTO} - ¿El la venta buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el la venta.
     */
    @GET
    @Path("{SaleId: \\d+}")
    public SaleDTO getSale(@PathParam("SaleId") Long saleId) 
    {
        LOGGER.log(Level.INFO, "SaleResource getSale: input: {0}", saleId);
        SaleEntity saleEntity = saleLogic.getSale(saleId);
        if (saleEntity == null)
        {
            throw new WebApplicationException("El recurso /Sales/" + saleId + " no esta.", 404);
        }
        SaleDTO detailDTO = new SaleDTO(saleEntity);
        LOGGER.log(Level.INFO, "SaleResource getSale: output: {0}", detailDTO);
        return detailDTO;
    }
    
    /**
     * Actualiza el la venta  con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param saleId
     * @param sale
     * @return JSON {@link SaleDTO} - El la venta guardado.
     * @throws co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el la venta  a
     * actualizar.
     */
    @PUT
    @Path("{SaleId: \\d+}")
    public SaleDTO updateSale(@PathParam("SaleId") Long saleId, SaleDTO sale) 
    {
        LOGGER.log(Level.INFO, "SaleResource updateSale: input: id:{0} , Sale {1}");
        sale.setId(saleId);
        if (saleLogic.getSale(saleId) == null)
        {
            throw new WebApplicationException("El recurso no esta", 404);
        }
        SaleDTO detailDTO = new SaleDTO(saleLogic.updateSale(saleId, sale.toEntity()));
        LOGGER.log(Level.INFO, "SaleResource updateSale: output: {0}", detailDTO);
        return detailDTO;
    }
    
    
    /**
     * Borra el la venta con el id asociado recibido en la URL.
     * @param saleId
     */
    @DELETE
    @Path("{SaleId: \\d+}")
    public void deleteSale(@PathParam("SaleId") Long saleId)
    {
        LOGGER.log(Level.INFO, "SaleResource deleteSale: input: {0}", saleId);
        if (saleLogic.getSale(saleId) == null)
        {
            throw new WebApplicationException("El recurso /Sales/" + saleId + " no existe.", 404);
        }
        saleLogic.deleteSale(saleId);
        LOGGER.info("SaleResource deleteSale: output: void");
    }
    
     @Path("{salesId: \\d+}/extraServices")
    public Class<SaleExtraServicesResources> getSaleExtraServicesResource(@PathParam("salesId") Long salesId) {
        if (saleLogic.getSale(salesId) == null) {
            throw new WebApplicationException("El recurso /sales/" + salesId + " no existe.", 404);
        }
        return SaleExtraServicesResources.class;
    }
    /**
     * Convierte una lista de entidades a DTO.
     *        return list;

     * Este método convierte una lista de objetos SaleEntity a una lista de
     * objetos SaleDTO (json)
     *
     * @param entityList corresponde a la lista Sale de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de extraServices en forma DTO (json)
     */
    private List<SaleDTO> listEntity2DTO(List<SaleEntity> entityList) 
    {
        List<SaleDTO> list = new ArrayList<>();
        for (SaleEntity entity : entityList) 
        {
            list.add(new SaleDTO(entity));
        }
        return list;
    }  
}

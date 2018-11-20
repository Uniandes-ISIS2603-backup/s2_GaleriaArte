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
            SaleLogic SaleLogic;
    
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
    public SaleDTO createSale(SaleDTO Sale) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "SaleResource createSale: input: {0}", Sale.toString());
        SaleEntity SaleEntity = Sale.toEntity();
        SaleEntity nuevoSaleEntity = SaleLogic.createSale(SaleEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        SaleDTO nuevoSaleDTO = new SaleDTO(nuevoSaleEntity);
        LOGGER.log(Level.INFO, "SaleResource createSale: output: {0}", nuevoSaleDTO.toString());
        return nuevoSaleDTO;
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
        List<SaleDTO> listaSale = listEntity2DTO(SaleLogic.getSales());
        LOGGER.log(Level.INFO, "SaleResource getSales: output: {0}", listaSale.toString());
        return listaSale;
    }
    
    /**
     * Busca la medio pago con el id asociado recibido en la URL y la devuelve.
     *
     * @param SaleId Identificador de medios de pago que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link SaleDTO} - ¿El la venta buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el la venta.
     */
    @GET
    @Path("{SaleId: \\d+}")
    public SaleDTO getSale(@PathParam("SaleId") Long SaleId) throws WebApplicationException
    {
        LOGGER.log(Level.INFO, "SaleResource getSale: input: {0}", SaleId);
        SaleEntity SaleEntity = SaleLogic.getSale(SaleId);
        if (SaleEntity == null)
        {
            throw new WebApplicationException("El recurso /Sales/" + SaleId + " no existe.", 404);
        }
        SaleDTO detailDTO = new SaleDTO(SaleEntity);
        LOGGER.log(Level.INFO, "SaleResource getSale: output: {0}", detailDTO.toString());
        return detailDTO;
    }
    
    /**
     * Actualiza el la venta  con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param SaleId Identificador de la  que se desea
     * actualizar. Este debe ser una cadena de dígitos.
     * @param Sale {@link SaleDTO} el la venta que se desea guardar.
     * @return JSON {@link SaleDTO} - El la venta guardado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el la venta  a
     * actualizar.
     */
    @PUT
    @Path("{SaleId: \\d+}")
    public SaleDTO updateSale(@PathParam("SaleId") Long saleId, SaleDTO sale) throws WebApplicationException, BusinessLogicException
    {
        LOGGER.log(Level.INFO, "SaleResource updateSale: input: id:{0} , Sale {1}");
        sale.setId(saleId);
        if (SaleLogic.getSale(saleId) == null)
        {
            throw new WebApplicationException("El recurso /Sales/" + saleId + " no existe.", 404);
        }
        SaleDTO detailDTO = new SaleDTO(SaleLogic.updateSale(saleId, sale.toEntity()));
        LOGGER.log(Level.INFO, "SaleResource updateSale: output: {0}", detailDTO.toString());
        return detailDTO;
    }
    
    
    /**
     * Borra el la venta con el id asociado recibido en la URL.
     * @param saleId
     * @param SaleId Identificador del la venta que se desea borrar.
     * Este debe ser una cadena de dígitos.
     */
    @DELETE
    @Path("{SaleId: \\d+}")
    public void deleteSale(@PathParam("SaleId") Long saleId)
    {
        LOGGER.log(Level.INFO, "SaleResource deleteSale: input: {0}", saleId);
        if (SaleLogic.getSale(saleId) == null)
        {
            throw new WebApplicationException("El recurso /Sales/" + saleId + " no existe.", 404);
        }
        SaleLogic.deleteSale(saleId);
        LOGGER.info("SaleResource deleteSale: output: void");
    }
    
    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos SaleEntity a una lista de
     * objetos SaleDTO (json)
     *
     * @param entityList corresponde a la lista Sale de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de extraServices en forma DTO (json)
     */
    private List<SaleDTO> listEntity2DTO(List<SaleEntity> entityList) 
    {
        List<SaleDTO> list = new ArrayList<SaleDTO>();
        for (SaleEntity entity : entityList) 
        {
            list.add(new SaleDTO(entity));
        }
        return list;
    }  
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.resources;

import co.edu.uniandes.csw.galeriaarte.dtos.PaintworkDTO;
import co.edu.uniandes.csw.galeriaarte.dtos.PaintworkDetailDTO;
import co.edu.uniandes.csw.galeriaarte.dtos.SaleDTO;
import co.edu.uniandes.csw.galeriaarte.dtos.SaleDetailDTO;
import co.edu.uniandes.csw.galeriaarte.ejb.BuyerPaintworksLogic;
import co.edu.uniandes.csw.galeriaarte.ejb.PaintworkLogic;
import co.edu.uniandes.csw.galeriaarte.ejb.BuyerSalesLogic;
import co.edu.uniandes.csw.galeriaarte.ejb.SaleLogic;
import co.edu.uniandes.csw.galeriaarte.entities.PaintworkEntity;
import co.edu.uniandes.csw.galeriaarte.entities.SaleEntity;
import co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author s.acostav
 */

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BuyerSalesResource {
   private static final Logger LOGGER = Logger.getLogger(BuyerSalesResource.class.getName());

    @Inject
    private BuyerSalesLogic buyerSalesLogic;

    @Inject
    private SaleLogic saleLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias
    
    @POST
    @Path("{salesId: \\d+}")
    public SaleDTO addSale(@PathParam("buyersId") Long buyersId, @PathParam("salesId") Long salesId) {
        LOGGER.log(Level.INFO, "BuyerSalesResource addSale: input: buyersID: {0} , salesId: {1}", new Object[]{buyersId, salesId});
        if (saleLogic.getSale(salesId) == null) {
            throw new WebApplicationException("El recurso /sales/" + salesId + " no esta.", 404);
        }
        
        SaleDTO saleDTO = new SaleDTO(buyerSalesLogic.addSale(buyersId, salesId));
        LOGGER.log(Level.INFO, "BuyerSalesResource addBook: output: {0}", saleDTO);
        return saleDTO;
    }
    
    @GET
    public List<SaleDetailDTO> getSales(@PathParam("buyersId") Long buyersId) {
        LOGGER.log(Level.INFO, "BuyerSalesResource getSales: input: {0}", buyersId);
        List<SaleDetailDTO> listaDetailDTOs = salesListEntity2DTO(buyerSalesLogic.getSales(buyersId));
        LOGGER.log(Level.INFO, "BuyerSalesResource getSales: output: {0}", listaDetailDTOs);
        return listaDetailDTOs;
    }
    
    @GET
    @Path("{salesId: \\d+}")
    public SaleDetailDTO getSale(@PathParam("buyersId") Long buyersId, @PathParam("salesId") Long salesId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "BuyerSalesResource getSale: input: buyersID: {0} , salesId: {1}", new Object[]{buyersId, salesId});
        if (saleLogic.getSale(salesId) == null) {
            throw new WebApplicationException("El recurso /buyers/" + buyersId + "/sales/" + salesId + " no se encontro", 404);
        }
        SaleDetailDTO saleDetailDTO = new SaleDetailDTO(buyerSalesLogic.getSale(buyersId, salesId));
        LOGGER.log(Level.INFO, "BuyerSalesResource getSale: output: {0}", saleDetailDTO);
        return saleDetailDTO;
    }
    
     @PUT
    public List<SaleDetailDTO> replaceSales(@PathParam("buyersId") Long buyersId, List<SaleDetailDTO> sales) {
        LOGGER.log(Level.INFO, "BuyerSalesResource replaceSales: input: buyersId: {0} , sales: {1}", new Object[]{buyersId, sales});
        for (SaleDetailDTO sale : sales) {
            if (saleLogic.getSale(sale.getId()) == null) {
                throw new WebApplicationException("El recurso /sales/" + sale.getId() + " no existe.", 404);
            }
        }
        List<SaleDetailDTO> listaDetailDTOs = salesListEntity2DTO(buyerSalesLogic.replaceSales(buyersId, salesListDTO2Entity(sales)));
        LOGGER.log(Level.INFO, "BuyerSalesResource replaceSales: output: {0}", listaDetailDTOs);
        return listaDetailDTOs;
    }
    
    private List<SaleDetailDTO> salesListEntity2DTO(List<SaleEntity> entityList) {
        List<SaleDetailDTO> list = new ArrayList();
        for (SaleEntity entity : entityList) {
            list.add(new SaleDetailDTO(entity));
        }
        return list;
    }
    
    private List<SaleEntity> salesListDTO2Entity(List<SaleDetailDTO> dtos) {
        List<SaleEntity> list = new ArrayList<>();
        for (SaleDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
    
    
}
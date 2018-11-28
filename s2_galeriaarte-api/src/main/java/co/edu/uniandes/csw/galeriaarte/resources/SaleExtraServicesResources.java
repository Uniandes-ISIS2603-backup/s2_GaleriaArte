/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.resources;

import co.edu.uniandes.csw.galeriaarte.dtos.ExtraServiceDTO;
import co.edu.uniandes.csw.galeriaarte.dtos.ExtraServiceDetailDTO;
import co.edu.uniandes.csw.galeriaarte.entities.ExtraServiceEntity;
import co.edu.uniandes.csw.galeriaarte.ejb.SaleExtraServicesLogic;
import co.edu.uniandes.csw.galeriaarte.ejb.ExtraServiceLogic;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.WebApplicationException;
import co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException;
/**
 *
 * @author s.acostav
 */

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SaleExtraServicesResources {
    
    
    private static final Logger LOGGER = Logger.getLogger(SaleExtraServicesResources.class.getName());

    @Inject
    private SaleExtraServicesLogic saleExtraServicesLogic;

    @Inject
    private ExtraServiceLogic extraServiceLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias
    
    @POST
    @Path("{extraServicesId: \\d+}")
    public ExtraServiceDTO addExtraService(@PathParam("salesId") Long salesId, @PathParam("extraServicesId") Long extraServicesId) {
        LOGGER.log(Level.INFO, "SaleExtraServicesResource addExtraService: input: salesID: {0} , extraServicesId: {1}", new Object[]{salesId, extraServicesId});
        if (extraServiceLogic.getExtraService(extraServicesId) == null) {
            throw new WebApplicationException("El recurso /extraServices/" + extraServicesId + " no esta disponible.", 404);
        }
        
        ExtraServiceDTO extraServiceDTO = new ExtraServiceDTO(saleExtraServicesLogic.addExtraService(salesId, extraServicesId));
        LOGGER.log(Level.INFO, "SaleExtraServicesResource addBook: output: {0}", extraServiceDTO);
        return extraServiceDTO;
    }
    
    @GET
    public List<ExtraServiceDetailDTO> getExtraServices(@PathParam("salesId") Long salesId) {
        LOGGER.log(Level.INFO, "SaleExtraServicesResource getExtraServices: input: {0}", salesId);
        List<ExtraServiceDetailDTO> listaDetailDTOs = extraServicesListEntity2DTO(saleExtraServicesLogic.getExtraServices(salesId));
        LOGGER.log(Level.INFO, "SaleExtraServicesResource getExtraServices: output: {0}", listaDetailDTOs);
        return listaDetailDTOs;
    }
    
    @GET
    @Path("{extraServicesId: \\d+}")
    public ExtraServiceDetailDTO getExtraService(@PathParam("salesId") Long salesId, @PathParam("extraServicesId") Long extraServicesId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "SaleExtraServicesResource getExtraService: input: salesID: {0} , extraServicesId: {1}", new Object[]{salesId, extraServicesId});
        if (extraServiceLogic.getExtraService(extraServicesId) == null) {
            throw new WebApplicationException("El recurso /sales/" + salesId + "/extraServices/" + extraServicesId + " no existe.", 404);
        }
        ExtraServiceDetailDTO extraServiceDetailDTO = new ExtraServiceDetailDTO(saleExtraServicesLogic.getExtraService(salesId, extraServicesId));
        LOGGER.log(Level.INFO, "SaleExtraServicesResource getExtraService: output: {0}", extraServiceDetailDTO);
        return extraServiceDetailDTO;
    }
    
     @PUT
    public List<ExtraServiceDetailDTO> replaceExtraServices(@PathParam("salesId") Long salesId, List<ExtraServiceDetailDTO> extraServices) {
        LOGGER.log(Level.INFO, "SaleExtraServicesResource replaceExtraServices: input: salesId: {0} , extraServices: {1}", new Object[]{salesId, extraServices});
        for (ExtraServiceDetailDTO extraService : extraServices) {
            if (extraServiceLogic.getExtraService(extraService.getId()) == null) {
                throw new WebApplicationException("El recurso /extraServices/" + extraService.getId() + " no se encuentra.", 404);
            }
        }
        List<ExtraServiceDetailDTO> listaDetailDTOs = extraServicesListEntity2DTO(saleExtraServicesLogic.replaceExtraServices(salesId, extraServicesListDTO2Entity(extraServices)));
        LOGGER.log(Level.INFO, "SaleExtraServicesResource replaceExtraServices: output: {0}", listaDetailDTOs);
        return listaDetailDTOs;
    }
    
    private List<ExtraServiceDetailDTO> extraServicesListEntity2DTO(List<ExtraServiceEntity> entityList) {
        List<ExtraServiceDetailDTO> list = new ArrayList();
        for (ExtraServiceEntity entity : entityList) {
            list.add(new ExtraServiceDetailDTO(entity));
        }
        return list;
    }
    
    private List<ExtraServiceEntity> extraServicesListDTO2Entity(List<ExtraServiceDetailDTO> dtos) {
        List<ExtraServiceEntity> list = new ArrayList<>();
        for (ExtraServiceDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
}


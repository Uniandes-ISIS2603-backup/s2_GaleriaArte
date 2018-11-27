/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.resources;
import co.edu.uniandes.csw.galeriaarte.dtos.PaintworkDTO;
import co.edu.uniandes.csw.galeriaarte.dtos.PaintworkDetailDTO;
import co.edu.uniandes.csw.galeriaarte.entities.PaintworkEntity;
import co.edu.uniandes.csw.galeriaarte.ejb.BuyerPaintworksLogic;
import co.edu.uniandes.csw.galeriaarte.ejb.PaintworkLogic;
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
 * @author estudiante
 */

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BuyerPaintworksResources {
    
    
    private static final Logger LOGGER = Logger.getLogger(BuyerPaintworksResources.class.getName());

    @Inject
    private BuyerPaintworksLogic buyerPaintworksLogic;

    @Inject
    private PaintworkLogic paintworkLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias
    
    @POST
    @Path("{paintworksId: \\d+}")
    public PaintworkDTO addPaintwork(@PathParam("buyersId") Long buyersId, @PathParam("paintworksId") Long paintworksId) {
        LOGGER.log(Level.INFO, "BuyerPaintworksResource addPaintwork: input: buyersID: {0} , paintworksId: {1}", new Object[]{buyersId, paintworksId});
        if (paintworkLogic.getPaintWork(paintworksId) == null) {
            throw new WebApplicationException("El recurso /paintworks/" + paintworksId + " no existe.", 404);
        }
        
        PaintworkDTO paintworkDTO = new PaintworkDTO(buyerPaintworksLogic.addPaintwork(buyersId, paintworksId));
        LOGGER.log(Level.INFO, "BuyerPaintworksResource addBook: output: {0}", paintworkDTO);
        return paintworkDTO;
    }
    
    @GET
    public List<PaintworkDetailDTO> getPaintworks(@PathParam("buyersId") Long buyersId) {
        LOGGER.log(Level.INFO, "BuyerPaintworksResource getPaintworks: input: {0}", buyersId);
        List<PaintworkDetailDTO> listaDetailDTOs = paintworksListEntity2DTO(buyerPaintworksLogic.getPaintworks(buyersId));
        LOGGER.log(Level.INFO, "BuyerPaintworksResource getPaintworks: output: {0}", listaDetailDTOs);
        return listaDetailDTOs;
    }
    
    @GET
    @Path("{paintworksId: \\d+}")
    public PaintworkDetailDTO getPaintwork(@PathParam("buyersId") Long buyersId, @PathParam("paintworksId") Long paintworksId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "BuyerPaintworksResource getPaintwork: input: buyersID: {0} , paintworksId: {1}", new Object[]{buyersId, paintworksId});
        if (paintworkLogic.getPaintWork(paintworksId) == null) {
            throw new WebApplicationException("El recurso /buyers/" + buyersId + "/paintworks/" + paintworksId + " no existe.", 404);
        }
        PaintworkDetailDTO paintworkDetailDTO = new PaintworkDetailDTO(buyerPaintworksLogic.getPaintwork(buyersId, paintworksId));
        LOGGER.log(Level.INFO, "BuyerPaintworksResource getPaintwork: output: {0}", paintworkDetailDTO);
        return paintworkDetailDTO;
    }
    
     @PUT
    public List<PaintworkDetailDTO> replacePaintworks(@PathParam("buyersId") Long buyersId, List<PaintworkDetailDTO> paintworks) {
        LOGGER.log(Level.INFO, "BuyerPaintworksResource replacePaintworks: input: buyersId: {0} , paintworks: {1}", new Object[]{buyersId, paintworks});
        for (PaintworkDetailDTO paintwork : paintworks) {
            if (paintworkLogic.getPaintWork(paintwork.getIdPaintwork()) == null) {
                throw new WebApplicationException("El recurso /paintworks/" + paintwork.getIdPaintwork() + " no existe.", 404);
            }
        }
        List<PaintworkDetailDTO> listaDetailDTOs = paintworksListEntity2DTO(buyerPaintworksLogic.replacePaintworks(buyersId, paintworksListDTO2Entity(paintworks)));
        LOGGER.log(Level.INFO, "BuyerPaintworksResource replacePaintworks: output: {0}", listaDetailDTOs);
        return listaDetailDTOs;
    }
    
    private List<PaintworkDetailDTO> paintworksListEntity2DTO(List<PaintworkEntity> entityList) {
        List<PaintworkDetailDTO> list = new ArrayList();
        for (PaintworkEntity entity : entityList) {
            list.add(new PaintworkDetailDTO(entity));
        }
        return list;
    }
    
    private List<PaintworkEntity> paintworksListDTO2Entity(List<PaintworkDetailDTO> dtos) {
        List<PaintworkEntity> list = new ArrayList<>();
        for (PaintworkDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.resources;
import co.edu.uniandes.csw.galeriaarte.dtos.FeedBackDTO;
import co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.logging.Logger;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import java.util.logging.Level;
import javax.ws.rs.PathParam;
/**
 *
 * @author s.restrepos1
 */
import co.edu.uniandes.csw.galeriaarte.dtos.FeedBackDTO;
import co.edu.uniandes.csw.galeriaarte.ejb.FeedBackLogic;
import co.edu.uniandes.csw.galeriaarte.entities.FeedBackEntity;
import javax.ws.rs.GET;
import javax.ws.rs.WebApplicationException;
@Path("feedbacks")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class FeedBackResources 
{
    private static final Logger LOGGER=Logger.getLogger(FeedBackResources.class.getName());
    
   @Inject
    FeedBackLogic feedBackLogic;
    
    @POST
    public FeedBackDTO createFeedBack(FeedBackDTO fdbDTO) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO,"FeedBackResource createFeedBack: input: {0}",fdbDTO.toString());
        FeedBackEntity feedEntity= fdbDTO.toEntity();
        
        FeedBackEntity nuevoFeedEntity= feedBackLogic.createFeedBack(feedEntity);
        
        FeedBackDTO nuevoFeedDTO= new FeedBackDTO(feedEntity);
        
        LOGGER.log(Level.INFO, "FeedBackResource createFeedBack: input: {0}", nuevoFeedDTO.toString());
        return nuevoFeedDTO;
    }
    @GET
    @Path("feedbacks://d+")
    public FeedBackDTO getFeedBack(@PathParam("/feedbacks") Long idFeed) throws WebApplicationException{
        
        LOGGER.log(Level.INFO, "PaintworkResource getPaintwork: input: {0}", idFeed);
        FeedBackEntity feedEntity = feedBackLogic.getFeedBack(idFeed);
        if (feedEntity == null) {
            throw new WebApplicationException("El recurso /feedbacks/" + idFeed + " no existe.", 404);
        }
        FeedBackDTO detailDTO = new FeedBackDTO(feedEntity);
        LOGGER.log(Level.INFO, "PaintworkResource getPaintwork: output: {0}", detailDTO.toString());
        return detailDTO;
    }
    
    @DELETE
    @Path("{feedbacksId: \\d+}")
    public void deleteExtraService(@PathParam("feedbacksId") Long feedId)
    {
     LOGGER.log(Level.INFO, "FeedBackResource deleteFeedBack: input: {0}", feedId);
        LOGGER.info("FeedBackResource deleteFeedBack: output: void");
    }
      
}

package co.edu.uniandes.csw.galeriaarte.resources;
import co.edu.uniandes.csw.galeriaarte.dtos.PaintworkDetailDTO;
import co.edu.uniandes.csw.galeriaarte.ejb.KindPaintworksLogic;
import co.edu.uniandes.csw.galeriaarte.ejb.PaintworkLogic;
import co.edu.uniandes.csw.galeriaarte.entities.PaintworkEntity;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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

/**
 * Clase que implementa el recurso "Kinds/{id}/Paintworks".
 *
 * @Paintwork ja.penat
 * @version 1.0
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class KindPaintworksResource 
{

    private static final Logger LOGGER = Logger.getLogger(KindPaintworksResource.class.getName());

    @Inject
    private KindPaintworksLogic kindPaintworkLogic;

    @Inject
    private PaintworkLogic paintworkLogic;

    /**
     * Asocia un obra existente con un tipo existente
     *
     * @param paintworksId El ID del obra que se va a asociar
     * @param kindsId El ID del libro al cual se le va a asociar el obra
     * @return JSON {@link PaintworkDetailDTO} - El obra asociado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el obra.
     */
    @POST
    @Path("{PaintworksId: \\d+}")
    public PaintworkDetailDTO addPaintwork(@PathParam("KindsId") Long kindsId, @PathParam("PaintworksId") Long paintworksId) {
        LOGGER.log(Level.INFO, "KindPaintworksResource addPaintwork: input: KindsId {0} , PaintworksId {1}", new Object[]{kindsId, paintworksId});
        if (paintworkLogic.getPaintWork(paintworksId) == null) {
            throw new WebApplicationException("El recurso con el path /Paintworks/" + paintworksId + " no estan", 404);
        }
        PaintworkDetailDTO detailDTO = new PaintworkDetailDTO(kindPaintworkLogic.addPaintwork(kindsId, paintworksId));
        LOGGER.log(Level.INFO, "KindPaintworksResource addPaintwork: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Busca y devuelve todos los obraes que existen en un libro.
     *
     * @param kindsId El ID del libro del cual se buscan los obraes
     * @return JSONArray {@link PaintworkDetailDTO} - Los obraes encontrados en el
     * libro. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<PaintworkDetailDTO> getPaintworks(@PathParam("KindsId") Long kindsId) {
        LOGGER.log(Level.INFO, "KindPaintworksResource getPaintworks: input: {0}", kindsId);
        List<PaintworkDetailDTO> lista = paintworksListEntity2DTO(kindPaintworkLogic.getPaintworks(kindsId));
        LOGGER.log(Level.INFO, "KindPaintworksResource getPaintworks: output: {0}", lista);
        return lista;
    }

    /**
     * Busca y devuelve el obra con el ID recibido en la URL, relativo a un
     * tipo.
     *
     * @param paintworksId El ID del obra que se busca
     * @param kindsId El ID del libro del cual se busca el obra
     * @return {@link PaintworkDetailDTO} - El obra encontrado en el libro.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra el obra.
     */
    @GET
    @Path("{PaintworksId: \\d+}")
    public PaintworkDetailDTO getPaintwork(@PathParam("KindsId") Long kindsId, @PathParam("PaintworksId") Long paintworksId) {
        LOGGER.log(Level.INFO, "KindPaintworksResource getPaintwork: input: KindsId {0} , PaintworksId {1}", new Object[]{kindsId, paintworksId});
        if (paintworkLogic.getPaintWork(paintworksId) == null) {
            throw new WebApplicationException("El recurso /Paintworks/" + paintworksId + " no existe.", 404);
        }
        PaintworkDetailDTO detailDTO = new PaintworkDetailDTO(kindPaintworkLogic.getPaintwork(kindsId, paintworksId));
        LOGGER.log(Level.INFO, "KindPaintworksResource getPaintwork: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Actualiza la lista de obraes de un tipo con la lista que se recibe en
     * el cuerpo.
     *
     * @param kindsId El ID del libro al cual se le va a asociar la lista de
     * obraes
     * @param paintworks JSONArray {@link PaintworkDetailDTO} - La lista de obraes
     * que se desea guardar.
     * @return JSONArray {@link PaintworkDetailDTO} - La lista actualizada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra el obra.
     */
    @PUT
    public List<PaintworkDetailDTO> replacePaintworks(@PathParam("KindsId") Long kindsId, List<PaintworkDetailDTO> paintworks) {
        LOGGER.log(Level.INFO, "KindPaintworksResource replacePaintworks: input: KindsId {0} , Paintworks {1}", new Object[]{kindsId, paintworks});
        for (PaintworkDetailDTO paintwork : paintworks) {
            if (paintworkLogic.getPaintWork(paintwork.getIdPaintwork()) == null) {
                throw new WebApplicationException("El recurso /Paintworks no existe.", 404);
            }
        }
        List<PaintworkDetailDTO> lista = paintworksListEntity2DTO(kindPaintworkLogic.replacePaintworks(kindsId, paintworksListDTO2Entity(paintworks)));
        LOGGER.log(Level.INFO, "KindPaintworksResource replacePaintworks: output:{0}", lista);
        return lista;
    }

    /**
     * Elimina la conexión entre el obra y el tipo recibidos en la URL.
     *
     * @param kindsId El ID del libro al cual se le va a desasociar el obra
     * @param paintworksId El ID del obra que se desasocia
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra el obra.
     */
    @DELETE
    @Path("{PaintworksId: \\d+}")
    public void removePaintwork(@PathParam("KindsId") Long kindsId, @PathParam("PaintworksId") Long paintworksId) {
        LOGGER.log(Level.INFO, "KindPaintworksResource removePaintwork: input: KindsId {0} , PaintworksId {1}", new Object[]{kindsId, paintworksId});
        if (paintworkLogic.getPaintWork(paintworksId) == null) {
            throw new WebApplicationException("El recurso /Paintworks/" + paintworksId + " no existe.", 404);
        }
        kindPaintworkLogic.removePaintwork(kindsId, paintworksId);
        LOGGER.info("KindPaintworksResource removePaintwork: output: void");
    }

    /**
     * Convierte una lista de PaintworkEntity a una lista de PaintworkDetailDTO.
     *
     * @param entityList Lista de PaintworkEntity a convertir.
     * @return Lista de PaintworkDetailDTO convertida.
     */
    private List<PaintworkDetailDTO> paintworksListEntity2DTO(List<PaintworkEntity> entityList) {
        List<PaintworkDetailDTO> list = new ArrayList<>();
        for (PaintworkEntity entity : entityList) {
            list.add(new PaintworkDetailDTO(entity));
        }
        return list;
    }

    /**
     * Convierte una lista de PaintworkDetailDTO a una lista de PaintworkEntity.
     *
     * @param dtos Lista de PaintworkDetailDTO a convertir.
     * @return Lista de PaintworkEntity convertida.
     */
    private List<PaintworkEntity> paintworksListDTO2Entity(List<PaintworkDetailDTO> dtos) {
        List<PaintworkEntity> list = new ArrayList<>();
        for (PaintworkDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
}

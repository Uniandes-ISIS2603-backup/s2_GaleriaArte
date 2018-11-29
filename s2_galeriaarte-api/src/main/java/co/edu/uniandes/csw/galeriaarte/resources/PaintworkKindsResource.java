package co.edu.uniandes.csw.galeriaarte.resources;


import co.edu.uniandes.csw.galeriaarte.dtos.KindDetailDTO;
import co.edu.uniandes.csw.galeriaarte.ejb.KindLogic;
import co.edu.uniandes.csw.galeriaarte.ejb.PaintworkKindsLogic;
import co.edu.uniandes.csw.galeriaarte.entities.KindEntity;
import co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException;
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
 * Clase que implementa el recurso "Paintworks/{id}/Kinds".
 *
 * @author ja.penat
 * @version 1.0
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PaintworkKindsResource {

    private static final Logger LOGGER = Logger.getLogger(PaintworkKindsResource.class.getName());

    @Inject
    private PaintworkKindsLogic paintworkKindLogic;

    @Inject
    private KindLogic kindLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Asocia un tipo existente con un obra existente
     *
     * @param paintworksId El ID del obra al cual se le va a asociar el tipo
     * @param kindsId El ID del tipo que se asocia
     * @return JSON {@link KindDetailDTO} - El tipo asociado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el tipo.
     */
    @POST
    @Path("{KindsId: \\d+}")
    public KindDetailDTO addKind(@PathParam("PaintworksId") Long paintworksId, @PathParam("KindsId") Long kindsId) {
        LOGGER.log(Level.INFO, "PaintworkKindsResource addKind: input: PaintworksId {0} , KindsId {1}", new Object[]{paintworksId, kindsId});
        if (kindLogic.getKindV(kindsId) == null) {
            throw new WebApplicationException("El recurso con el path /Kinds/" + kindsId + " no existe.", 404);
        }
        KindDetailDTO detailDTO = new KindDetailDTO(paintworkKindLogic.addKind(paintworksId, kindsId));
        LOGGER.log(Level.INFO, "PaintworkKindsResource addKind: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Busca y devuelve todos los tipos que existen en un obra.
     *
     * @param paintworksId El ID del obra del cual se buscan los tipos
     * @return JSONArray {@link KindDetailDTO} - Los tipos encontrados en el
     * obra. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<KindDetailDTO> getKinds(@PathParam("PaintworksId") Long paintworksId) {
        LOGGER.log(Level.INFO, "PaintworkKindsResource getKinds: input: {0}", paintworksId);
        List<KindDetailDTO> lista = KindsListEntity2DTO(paintworkKindLogic.getKinds(paintworksId));
        LOGGER.log(Level.INFO, "PaintworkKindsResource getKinds: output: {0}", lista);
        return lista;
    }

    /**
     * Busca y devuelve el tipo con el ID recibido en la URL, relativo a un
     * obra.
     *
     * @param paintworksId El ID del obra del cual se busca el tipo
     * @param kindsId El ID del tipo que se busca
     * @return {@link KindDetailDTO} - El tipo encontrado en el obra.
     * @throws co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el tipo.
     */
    @GET
    @Path("{KindsId: \\d+}")
    public KindDetailDTO getKind(@PathParam("PaintworksId") Long paintworksId, @PathParam("KindsId") Long kindsId) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "PaintworkKindsResource getKind: input: PaintworksId {0} , KindsId {1}", new Object[]{paintworksId, kindsId});
        if (kindLogic.getKindV(kindsId) == null) {
            throw new WebApplicationException("El recurso /Kinds/" + kindsId + " no esta.", 404);
        }
        KindDetailDTO detailDTO = new KindDetailDTO(paintworkKindLogic.getKind(paintworksId, kindsId));
        LOGGER.log(Level.INFO, "PaintworkKindsResource getKind: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Actualiza la lista de tipos de un obra con la lista que se recibe en el
     * cuerpo
     *
     * @param paintworksId El ID del obra al cual se le va a asociar el tipo
     * @param kinds JSONArray {@link KindDetailDTO} - La lista de tipos que se
     * desea guardar.
     * @return JSONArray {@link KindDetailDTO} - La lista actualizada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el tipo.
     */
    @PUT
    public List<KindDetailDTO> replaceKinds(@PathParam("PaintworksId") Long paintworksId, List<KindDetailDTO> kinds) {
        LOGGER.log(Level.INFO, "PaintworkKindsResource replaceKinds: input: PaintworksId {0} , Kinds {1}", new Object[]{paintworksId, kinds});
        for (KindDetailDTO kind : kinds) {
            if (kindLogic.getKindV(kind.getIdType()) == null) {
                throw new WebApplicationException("El recurso /Kinds/no existe.", 404);
            }
        }
        List<KindDetailDTO> lista = KindsListEntity2DTO(paintworkKindLogic.replaceKinds(paintworksId, KindsListDTO2Entity(kinds)));
        LOGGER.log(Level.INFO, "PaintworkKindsResource replaceKinds: output: {0}", lista);
        return lista;
    }

    /**
     * Elimina la conexión entre el tipo y e obra recibidos en la URL.
     *
     * @param paintworksId El ID del obra al cual se le va a desasociar el tipo
     * @param kindsId El ID del tipo que se desasocia
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el tipo.
     */
    @DELETE
    @Path("{KindsId: \\d+}")
    public void removeKind(@PathParam("PaintworksId") Long paintworksId, @PathParam("KindsId") Long kindsId) 
    {
        LOGGER.log(Level.INFO, "PaintworkKindsResource deleteKind: input: PaintworksId {0} , KindsId {1}", new Object[]{paintworksId, kindsId});
        if (kindLogic.getKindV(kindsId) == null) {
            throw new WebApplicationException("El recurso /Kinds/" + kindsId + " no existe.", 404);
        }
        paintworkKindLogic.removeKind(paintworksId, kindsId);
        LOGGER.info("PaintworkKindsResource deleteKind: output: void");
    }

    /**
     * Convierte una lista de KindEntity a una lista de KindDetailDTO.
     *
     * @param entityList Lista de KindEntity a convertir.
     * @return Lista de KindDetailDTO convertida.
     */
    private List<KindDetailDTO> KindsListEntity2DTO(List<KindEntity> entityList) {
        List<KindDetailDTO> list = new ArrayList<>();
        for (KindEntity entity : entityList) {
            list.add(new KindDetailDTO(entity));
        }
        return list;
    }

    /**
     * Convierte una lista de KindDetailDTO a una lista de KindEntity.
     *
     * @param dtos Lista de KindDetailDTO a convertir.
     * @return Lista de KindEntity convertida.
     */
    private List<KindEntity> KindsListDTO2Entity(List<KindDetailDTO> dtos) {
        List<KindEntity> list = new ArrayList<>();
        for (KindDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
}


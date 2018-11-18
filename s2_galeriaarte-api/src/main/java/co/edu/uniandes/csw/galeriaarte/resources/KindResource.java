package co.edu.uniandes.csw.galeriaarte.resources;


import co.edu.uniandes.csw.galeriaarte.dtos.KindDTO;
import co.edu.uniandes.csw.galeriaarte.ejb.KindLogic;
import co.edu.uniandes.csw.galeriaarte.entities.KindEntity;

import co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
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


@Path("kinds")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped

public class KindResource {
    private static final Logger LOGGER = Logger.getLogger(KindResource.class.getName());
    
@Inject 
KindLogic kindLogic;
    /**
     * Crea una hoja de vida
     *
     * @param kindDTO
     * @return
     */
    @POST
    public KindDTO createKInd(KindDTO Kind) throws BusinessLogicException {
         LOGGER.log(Level.INFO, "KindResources createKind: input: {0}", Kind.toString());
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        KindEntity kindEntity = Kind.toEntity();
        // Invoca la lógica para crear la pintura nueva
      
      KindEntity   nuevoKindEntity = kindLogic.createKind(kindEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        KindDTO nuevoKindDTO = new KindDTO(nuevoKindEntity);
        LOGGER.log(Level.INFO, "KindResoruces createKind: input: {0}", nuevoKindDTO.toString());
        return nuevoKindDTO;
    }

       /**
     * Busca y devuelve todas los kinds que existen en la aplicacion.
     *
     * @return JSONArray {@link KindDTO} - Los kinds encontradas en
     * la aplicación. Si no hay ninguno retorna una lista vacía.
     */
    
    
    @GET
    public List<KindDTO> getkinds() {
        LOGGER.info("KindResource getkinds: input: void");
        List<KindDTO> listakinds;
        listakinds = listEntity2DetailDTO(kindLogic.getKInds());
        LOGGER.log(Level.INFO, "KindResource getkinds: output: {0}", listakinds.toString());
        return listakinds;
    }

    /**
     * Busca el Kind con el id asociado recibido en la URL y la devuelve.
     *
     * @param kindId Identificador de la Kind que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link EditorialDTO} - La editorial buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la editorial.
     */
    @GET
    @Path("{kindsId: \\d+}")
    public KindDTO getKind(@PathParam("kindsId") Long kindId) throws WebApplicationException {
        LOGGER.log(Level.INFO, "KindResource getKind: input: {0}", kindId);
        KindEntity kindEntity;
        kindEntity = kindLogic.getKindV(kindId);
        if (kindEntity == null) {
            throw new WebApplicationException("El recurso /kinds/" + kindId + " no existe.", 404);
        }
        KindDTO detailDTO = new KindDTO(kindEntity);
        LOGGER.log(Level.INFO, "KindResource getKind: output: {0}", detailDTO.toString());
        return detailDTO;
    }

    /**
     * Actualiza la Kind con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param kindId Identificador de la Kind que se desea
     * actualizar. Este debe ser una cadena de dígitos.
     * @param kind1 {@link EditorialDTO} La editorial que se desea guardar.
     * @return JSON {@link EditorialDTO} - La editorial guardada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la editorial a
     * actualizar.
     */
    @PUT
    @Path("{kindsId: \\d+}")
    public KindDTO updateKind(@PathParam("kindsId") Long kindId, KindDTO kind1) throws WebApplicationException {
        LOGGER.log(Level.INFO, "KindResource updateKind: input: id:{0} , Kind: {1}", new Object[]{kindId, kind1.toString()});
        kind1.setIdType(kindId);
        if (kindLogic.getKindV(kindId) == null) {
            throw new WebApplicationException("El recurso /kinds/" + kindId + " no existe.", 404);
        }
        KindDTO detailDTO = new KindDTO(kindLogic.updateKind( kind1.toEntity()));
        LOGGER.log(Level.INFO, "KindResource updateKind: output: {0}", detailDTO.toString());
        return detailDTO;
    }

    /**
     * Borra la Kind con el id asociado recibido en la URL.
     *
     * @param kindId Identificador de la editorial que se desea borrar.
     * Este debe ser una cadena de dígitos.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar la editorial.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la editorial.
     */
    @DELETE
    @Path("{kindsId: \\d+}")
    public void deleteKind(@PathParam("kindsId") Long kindId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "KindResource updateKind: input: {0}", kindId);
        if (kindLogic.getKindV(kindId) == null) {
            throw new WebApplicationException("El recurso /kinds/" + kindId + " no existe.", 404);
        }
        kindLogic.deleteKind(kindId);
        LOGGER.info("KindResource updateKind: output: void");
    }

    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos KindEntity a una lista de
     * objetos EditorialDTO (json)
     *
     * @param entityList corresponde a la lista de editoriales de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de editoriales en forma DTO (json)
     */
    private List<KindDTO> listEntity2DetailDTO(List<KindEntity> entityList) {
        List<KindDTO> list = new ArrayList<>();
        for (KindEntity entity : entityList) {
            list.add(new KindDTO(entity));
        }
        return list;
    }
    
}



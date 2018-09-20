package co.edu.uniandes.csw.galeriaarte.resources;

import co.edu.uniandes.csw.galeriaarte.dtos.CVDTO;
import co.edu.uniandes.csw.galeriaarte.ejb.CVLogic;
import co.edu.uniandes.csw.galeriaarte.entities.CVEntity;
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
import javax.ws.rs.WebApplicationException;

@Path("cvs")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped

public class CVResources {
    private static final Logger LOGGER = Logger.getLogger(CVResources.class.getName());
    
@Inject 
CVLogic cvLogic;
    /**
     * Crea una hoja de vida
     *
     * @param CVDTO
     * @return
     */
    @POST
    public CVDTO createCV(CVDTO cv) throws BusinessLogicException {
         LOGGER.log(Level.INFO, "CVResources createCV: input: {0}", cv.toString());
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        CVEntity cvEntity = cv.toEntity();
        // Invoca la lógica para crear la pintura nueva
      
      CVEntity   nuevoCVEntity = cvLogic.createCV(cvEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        CVDTO nuevoCVDTO = new CVDTO(cvEntity);
        LOGGER.log(Level.INFO, "CVResoruces createCV: input: {0}", nuevoCVDTO.toString());
        return nuevoCVDTO;
    }

       /**
     * Busca y devuelve todas los cvs que existen en la aplicacion.
     *
     * @return JSONArray {@link CVDTO} - Los cvs encontradas en
     * la aplicación. Si no hay ninguno retorna una lista vacía.
     */
    
    
    @GET
    public List<CVDTO> getCVs() {
        LOGGER.info("CVResource getCVs: input: void");
        List<CVDTO> listaCVs= new ArrayList();
        LOGGER.log(Level.INFO, "CVResource getCVs: output: {0}", listaCVs.toString());
        return listaCVs;
    }

    /**
     * Busca el cv con el id asociado recibido en la URL y la devuelve.
     *
     * @param cvId Identificador de la cv que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link EditorialDTO} - La editorial buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la editorial.
     */
    @GET
    @Path("{cvsId: \\d+}")
    public CVDTO getCV(@PathParam("cvsId") Long cvId) throws WebApplicationException {
        LOGGER.log(Level.INFO, "CVResource getCV: input: {0}", cvId);
        CVEntity cvEntity;
        cvEntity = cvLogic.getCV(cvId);
        if (cvEntity == null) {
            throw new WebApplicationException("El recurso /cvs/" + cvId + " no existe.", 404);
        }
        CVDTO detailDTO = new CVDTO(cvEntity);
        LOGGER.log(Level.INFO, "CVResource getCV: output: {0}", detailDTO.toString());
        return detailDTO;
    }

    /**
     * Actualiza la cv con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param cvId Identificador de la cv que se desea
     * actualizar. Este debe ser una cadena de dígitos.
     * @param cv1 {@link EditorialDTO} La editorial que se desea guardar.
     * @return JSON {@link EditorialDTO} - La editorial guardada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la editorial a
     * actualizar.
     */
    @PUT
    @Path("{cvsId: \\d+}")
    public CVDTO updateCV(@PathParam("cvsId") Long cvId, CVDTO cv1) throws WebApplicationException {
        LOGGER.log(Level.INFO, "CVResource updateCV: input: id:{0} , cv: {1}", new Object[]{cvId, cv1.toString()});
        cv1.setId(cvId);
        if (cvLogic.getCV(cvId) == null) {
            throw new WebApplicationException("El recurso /cvs/" + cvId + " no existe.", 404);
        }
        CVDTO detailDTO = new CVDTO(cvLogic.updateCV(cvId, cv1.toEntity()));
        LOGGER.log(Level.INFO, "CVResource updateCV: output: {0}", detailDTO.toString());
        return detailDTO;
    }

    /**
     * Borra la cv con el id asociado recibido en la URL.
     *
     * @param cvId Identificador de la editorial que se desea borrar.
     * Este debe ser una cadena de dígitos.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar la editorial.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la editorial.
     */
    @DELETE
    @Path("{cvsId: \\d+}")
    public void deleteCV(@PathParam("cvsId") Long cvId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "CVResource updateCV: input: {0}", cvId);
        if (cvLogic.getCV(cvId) == null) {
            throw new WebApplicationException("El recurso /cvs/" + cvId + " no existe.", 404);
        }
        cvLogic.deleteCV(cvId);
        LOGGER.info("CVResource updateCV: output: void");
    }

    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos CVEntity a una lista de
     * objetos EditorialDTO (json)
     *
     * @param entityList corresponde a la lista de editoriales de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de editoriales en forma DTO (json)
     */
    private List<CVDTO> listEntity2DetailDTO(List<CVEntity> entityList) {
        List<CVDTO> list = new ArrayList<>();
        for (CVEntity entity : entityList) {
            list.add(new CVDTO(entity));
        }
        return list;
    }
    
}



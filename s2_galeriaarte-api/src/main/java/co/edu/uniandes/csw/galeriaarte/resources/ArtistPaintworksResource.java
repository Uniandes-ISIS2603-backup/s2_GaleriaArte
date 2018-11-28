package co.edu.uniandes.csw.galeriaarte.resources;


import co.edu.uniandes.csw.galeriaarte.dtos.PaintworkDTO;
import co.edu.uniandes.csw.galeriaarte.dtos.PaintworkDetailDTO;
import co.edu.uniandes.csw.galeriaarte.ejb.ArtistPaintworksLogic;
import co.edu.uniandes.csw.galeriaarte.ejb.PaintworkLogic;
import co.edu.uniandes.csw.galeriaarte.entities.PaintworkEntity;
import co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException;
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

/**
 * Clase que implementa el recurso "Artist/{id}/Paintwork".
 *
 * @author ja.penat
 * @version 1.0
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ArtistPaintworksResource 
{

    private static final Logger LOGGER = Logger.getLogger(ArtistPaintworksResource.class.getName());

    @Inject
    private ArtistPaintworksLogic artistPaintworkLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private PaintworkLogic paintworkLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Guarda un obra dentro de una Artist con la informacion que recibe el
     * la URL. Se devuelve el obra que se guarda en la Artist.
     *
     * @param artistsId Identificador de la Artist que se esta
     * actualizando. Este debe ser una cadena de dígitos.
     * @param paintworkId Identificador del obra que se desea guardar. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link paintworkDTO} - El obra guardado en la Artist.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el obra.
     */
    @POST
    @Path("{PaintworkId: \\d+}")
    public PaintworkDTO addPaintwork(@PathParam("artistsId") Long artistsId, @PathParam("PaintworkId") Long paintworkId) 
    {
        LOGGER.log(Level.INFO, "artistPaintworkResource addpaintwork: input: artistsID: {0} , PaintworkId: {1}", new Object[]{artistsId, paintworkId});
        if (paintworkLogic.getPaintWork(paintworkId) == null) {
            throw new WebApplicationException("El recurso /Paintwork/" + paintworkId + " no esta", 404);
        }
        PaintworkDTO paintworkDTO = new PaintworkDTO(artistPaintworkLogic.addPaintwork(paintworkId, artistsId));
        LOGGER.log(Level.INFO, "artistPaintworkResource addpaintwork: output: {0}", paintworkDTO);
        return paintworkDTO;
    }

    /**
     * Busca y devuelve todos los obras que existen en la Artist.
     *
     * @param artistsId Identificador de la Artist que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSONArray {@link paintworkDetailDTO} - Los obras encontrados en la
     * Artist. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<PaintworkDetailDTO> getPaintwork(@PathParam("artistsId") Long artistsId) {
        LOGGER.log(Level.INFO, "artistPaintworkResource getPaintwork: input: {0}", artistsId);
        List<PaintworkDetailDTO> listaDetailDTOs = paintworkListEntity2DTO(artistPaintworkLogic.getPaintworks(artistsId));
        LOGGER.log(Level.INFO, "artistPaintworkResource getPaintwork: output: {0}", listaDetailDTOs);
        return listaDetailDTOs;
    }

    /**
     * Busca el obra con el id asociado dentro de la Artist con id asociado.
     *
     * @param artistsId Identificador de la Artist que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @param PaintworkId Identificador del obra que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link paintworkDetailDTO} - El obra buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el obra.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el obra en la
     * Artist.
     */
    @GET
    @Path("{PaintworkId: \\d+}")
    public PaintworkDetailDTO getPaintwork(@PathParam("artistsId") Long artistsId, @PathParam("PaintworkId") Long paintworkId) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "artistPaintworkResource getpaintwork: input: artistsID: {0} , PaintworkId: {1}", new Object[]{artistsId, paintworkId});
        if (paintworkLogic.getPaintWork(paintworkId) == null) {
            throw new WebApplicationException("El recurso /artists/" + artistsId + "/Paintwork/" + paintworkId + " no existe.", 404);
        }
        PaintworkDetailDTO paintworkDetailDTO = new PaintworkDetailDTO(artistPaintworkLogic.getPaintwork(artistsId, paintworkId));
        LOGGER.log(Level.INFO, "artistPaintworkResource getpaintwork: output: {0}", paintworkDetailDTO);
        return paintworkDetailDTO;
    }

    /**
     * Remplaza las instancias de Paintwork asociadas a una instancia de Artist
     *
     * @param artistsId Identificador de la Artist que se esta
     * remplazando. Este debe ser una cadena de dígitos.
     * @param paintworks JSONArray {@link paintworkDTO} El arreglo de obras nuevo para la
     * Artist.
     * @return JSON {@link paintworkDTO} - El arreglo de obras guardado en la
     * Artist.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el obra.
     */
    @PUT
    public List<PaintworkDetailDTO> replacePaintwork(@PathParam("artistsId") Long artistsId, List<PaintworkDetailDTO> paintworks) 
    {
        LOGGER.log(Level.INFO, "artistPaintworkResource replacePaintwork: input: artistsId: {0} , Paintwork: {1}", new Object[]{artistsId, paintworks});
        for (PaintworkDetailDTO paintwork : paintworks) {
            if (paintworkLogic.getPaintWork(paintwork.getIdPaintwork()) == null) {
                throw new WebApplicationException("El recurso /Paintwork/" + paintwork.getIdPaintwork() + " no se encontro", 404);
            }
        }
        List<PaintworkDetailDTO> listaDetailDTOs = paintworkListEntity2DTO(artistPaintworkLogic.replacePaintworks(artistsId, paintworkListDTO2Entity(paintworks)));
        LOGGER.log(Level.INFO, "artistPaintworkResource replacePaintwork: output: {0}", listaDetailDTOs);
        return listaDetailDTOs;
    }

    /**
     * Convierte una lista de paintworkEntity a una lista de paintworkDetailDTO.
     *
     * @param entityList Lista de paintworkEntity a convertir.
     * @return Lista de paintworkDTO convertida.
     */
    private List<PaintworkDetailDTO> paintworkListEntity2DTO(List<PaintworkEntity> entityList) {
        List<PaintworkDetailDTO> list = new ArrayList();
        for (PaintworkEntity entity : entityList) {
            list.add(new PaintworkDetailDTO(entity));
        }
        return list;
    }

    /**
     * Convierte una lista de paintworkDetailDTO a una lista de paintworkEntity.
     *
     * @param dtos Lista de paintworkDetailDTO a convertir.
     * @return Lista de paintworkEntity convertida.
     */
    private List<PaintworkEntity> paintworkListDTO2Entity(List<PaintworkDetailDTO> dtos) {
        List<PaintworkEntity> list = new ArrayList<>();
        for (PaintworkDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
}
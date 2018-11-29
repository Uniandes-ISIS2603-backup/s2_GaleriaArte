/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.resources;

import co.edu.uniandes.csw.galeriaarte.dtos.CategoryDetailDTO;
import co.edu.uniandes.csw.galeriaarte.ejb.CategoryLogic;
import co.edu.uniandes.csw.galeriaarte.ejb.PaintworkCategoriesLogic;
import co.edu.uniandes.csw.galeriaarte.entities.CategoryEntity;
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
 * Clase que implementa el recurso "Paintworks/{id}/Categories".
 *
 * @author s.acostav
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PaintworkCategoriesResource {

    private static final Logger LOGGER = Logger.getLogger(PaintworkCategoriesResource.class.getName());

    @Inject
    private PaintworkCategoriesLogic paintworkCategoryLogic;

    @Inject
    private CategoryLogic categoryLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Asocia un tipo existente con un obra existente
     *
     * @param paintworksId El ID del obra al cual se le va a asociar el tipo
     * @param categorysId El ID del tipo que se asocia
     * @return JSON {@link CategoryDetailDTO} - El tipo asociado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el tipo.
     */
    @POST
    @Path("{CategoriesId: \\d+}")
    public CategoryDetailDTO addCategory(@PathParam("PaintworksId") Long paintworksId, @PathParam("CategoriesId") Long categorysId) {
        LOGGER.log(Level.INFO, "PaintworkCategoriesResource addCategory: input: PaintworksId {0} , CategoriesId {1}", new Object[]{paintworksId, categorysId});
        if (categoryLogic.getCategory(categorysId) == null) {
            throw new WebApplicationException("El recurso con el path /Categories/" + categorysId + " no existe.", 404);
        }
        CategoryDetailDTO detailDTO = new CategoryDetailDTO(paintworkCategoryLogic.addCategory(paintworksId, categorysId));
        LOGGER.log(Level.INFO, "PaintworkCategoriesResource addCategory: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Busca y devuelve todos los tipos que existen en un obra.
     *
     * @param paintworksId El ID del obra del cual se buscan los tipos
     * @return JSONArray {@link CategoryDetailDTO} - Los tipos encontrados en el
     * obra. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<CategoryDetailDTO> getCategories(@PathParam("PaintworksId") Long paintworksId) {
        LOGGER.log(Level.INFO, "PaintworkCategoriesResource getCategories: input: {0}", paintworksId);
        List<CategoryDetailDTO> lista = CategoriesListEntity2DTO(paintworkCategoryLogic.getCategories(paintworksId));
        LOGGER.log(Level.INFO, "PaintworkCategoriesResource getCategories: output: {0}", lista);
        return lista;
    }

    /**
     * Busca y devuelve el tipo con el ID recibido en la URL, relativo a un
     * obra.
     *
     * @param paintworksId El ID del obra del cual se busca el tipo
     * @param categorysId El ID del tipo que se busca
     * @return {@link CategoryDetailDTO} - El tipo encontrado en el obra.
     * @throws co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el tipo.
     */
    @GET
    @Path("{CategoriesId: \\d+}")
    public CategoryDetailDTO getCategory(@PathParam("PaintworksId") Long paintworksId, @PathParam("CategoriesId") Long categorysId) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "PaintworkCategoriesResource getCategory: input: PaintworksId {0} , CategoriesId {1}", new Object[]{paintworksId, categorysId});
        if (categoryLogic.getCategory(categorysId) == null) {
            throw new WebApplicationException("El recurso /Categories/" + categorysId + " no esta", 404);
        }
        CategoryDetailDTO detailDTO = new CategoryDetailDTO(paintworkCategoryLogic.getCategory(paintworksId, categorysId));
        LOGGER.log(Level.INFO, "PaintworkCategoriesResource getCategory: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Actualiza la lista de tipos de un obra con la lista que se recibe en el
     * cuerpo
     *
     * @param paintworksId El ID del obra al cual se le va a asociar el tipo
     * @param categorys JSONArray {@link CategoryDetailDTO} - La lista de tipos que se
     * desea guardar.
     * @return JSONArray {@link CategoryDetailDTO} - La lista actualizada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el tipo.
     */
    @PUT
    public List<CategoryDetailDTO> replaceCategories(@PathParam("PaintworksId") Long paintworksId, List<CategoryDetailDTO> categorys) {
        LOGGER.log(Level.INFO, "PaintworkCategoriesResource replaceCategories: input: PaintworksId {0} , Categories {1}", new Object[]{paintworksId, categorys});
        for (CategoryDetailDTO category : categorys) {
            if (categoryLogic.getCategory(category.getIdCategory()) == null) {
                throw new WebApplicationException("El recurso /Categories no existe.", 404);
            }
        }
        List<CategoryDetailDTO> lista = CategoriesListEntity2DTO(paintworkCategoryLogic.replaceCategories(paintworksId, CategoriesListDTO2Entity(categorys)));
        LOGGER.log(Level.INFO, "PaintworkCategoriesResource replaceCategories: output: {0}", lista);
        return lista;
    }

    /**
     * Elimina la conexión entre el tipo y e obra recibidos en la URL.
     *
     * @param paintworksId El ID del obra al cual se le va a desasociar el tipo
     * @param categorysId El ID del tipo que se desasocia
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el tipo.
     */
    @DELETE
    @Path("{CategoriesId: \\d+}")
    public void removeCategory(@PathParam("PaintworksId") Long paintworksId, @PathParam("CategoriesId") Long categorysId) 
    {
        LOGGER.log(Level.INFO, "PaintworkCategoriesResource deleteCategory: input: PaintworksId {0} , CategoriesId {1}", new Object[]{paintworksId, categorysId});
        if (categoryLogic.getCategory(categorysId) == null) {
            throw new WebApplicationException("El recurso /Categories/" + categorysId + " no existe.", 404);
        }
        paintworkCategoryLogic.removeCategory(paintworksId, categorysId);
        LOGGER.info("PaintworkCategoriesResource deleteCategory: output: void");
    }

    /**
     * Convierte una lista de CategoryEntity a una lista de CategoryDetailDTO.
     *
     * @param entityList Lista de CategoryEntity a convertir.
     * @return Lista de CategoryDetailDTO convertida.
     */
    private List<CategoryDetailDTO> CategoriesListEntity2DTO(List<CategoryEntity> entityList) {
        List<CategoryDetailDTO> list = new ArrayList<>();
        for (CategoryEntity entity : entityList) {
            list.add(new CategoryDetailDTO(entity));
        }
        return list;
    }

    /**
     * Convierte una lista de CategoryDetailDTO a una lista de CategoryEntity.
     *
     * @param dtos Lista de CategoryDetailDTO a convertir.
     * @return Lista de CategoryEntity convertida.
     */
    private List<CategoryEntity> CategoriesListDTO2Entity(List<CategoryDetailDTO> dtos) {
        List<CategoryEntity> list = new ArrayList<>();
        for (CategoryDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
}

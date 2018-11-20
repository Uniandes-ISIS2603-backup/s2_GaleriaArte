/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.resources;

import co.edu.uniandes.csw.galeriaarte.dtos.CategoryDTO;
import co.edu.uniandes.csw.galeriaarte.ejb.CategoryLogic;
import co.edu.uniandes.csw.galeriaarte.entities.CategoryEntity;
import co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
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
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;

/**
 *
 * @author jf.copete
 */

@Path("categories")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class CategoryResource {
    
    private static final Logger LOGGER = Logger.getLogger(CategoryResource.class.getName());

    @Inject
    CategoryLogic categoryLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Crea una nueva categoria con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     * @param category {@link EditorialDTO} - La categoria que se desea
     * guardar.
     * @return JSON {@link EditorialDTO} - La editorial guardada con el atributo
     * id autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe la categoria.
     */
    @POST
    public CategoryDTO createCategory(CategoryDTO category) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "CategoryResource createCategory: input: {0}", category.toString());
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        CategoryEntity categoryEntity = category.toEntity();
        // Invoca la lógica para crear la editorial nueva
        CategoryEntity nuevoEditorialEntity = categoryLogic.createCategory(categoryEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        CategoryDTO nuevoEditorialDTO = new CategoryDTO(nuevoEditorialEntity);
        LOGGER.log(Level.INFO, "CategoryResource createCategory: output: {0}", nuevoEditorialDTO.toString());
        return nuevoEditorialDTO;
    }

    /**
     * Busca y devuelve todas las categorias que existen en la aplicacion.
     *
     * @return JSONArray {@link EditorialDTO} - Las categorias encontradas en
     * la aplicación. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<CategoryDTO> getCategories() 
    {
        LOGGER.info("CategoryResource getCategories: input: void");
        List<CategoryDTO> listaCategories = listEntity2DetailDTO(categoryLogic.getCategories());
        LOGGER.log(Level.INFO, "CategoryResource getCategories: output: {0}", listaCategories.toString());
        return listaCategories;
    }


    /**
     * Actualiza la categoria con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param categoryId Identificador de la categoria que se desea
     * actualizar. Este debe ser una cadena de dígitos.
     * @param category {@link CategoryDTO} La categoria que se desea guardar.
     * @return JSON {@link CategoryDTO} - La categoria guardada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la categoria a
     * actualizar.
     */
    @PUT
    @Path("{categoryId: \\d+}")
    public CategoryDTO updateCategory(@PathParam("categoryId") Long categoryId, CategoryDTO category) throws WebApplicationException, BusinessLogicException {
        LOGGER.log(Level.INFO, "CategoryResource updateCategory: input: id:{0} , editorial: {1}", new Object[]{categoryId, category.toString()});
        category.setIdCategory(categoryId);
        if (categoryLogic.getCategory(categoryId) == null)
        {
            throw new WebApplicationException("El recurso /categories/" + categoryId + " no existe.", 404);
        }
        CategoryDTO detailDTO = new CategoryDTO(categoryLogic.updateCategory(categoryId, category.toEntity()));
        LOGGER.log(Level.INFO, "CategoryResource updateCategory: output: {0}", detailDTO.toString());
        return detailDTO;
    }

    /**
     * Borra la categoria con el id asociado recibido en la URL.
     *
     * @param categoryId Identificador de la categoria que se desea borrar.
     * Este debe ser una cadena de dígitos.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar la categoria.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la categoria.
     */
    @DELETE
    @Path("{categoryId: \\d+}")
    public void deleteEditorial(@PathParam("categoryId") Long categoryId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "CategoryResource deleteCategory: input: {0}", categoryId);
        if (categoryLogic.getCategory(categoryId) == null) {
            throw new WebApplicationException("El recurso /editorials/" + categoryId + " no existe.", 404);
        }
        categoryLogic.deleteCategory(categoryId);
        LOGGER.info("CategoryResource deleteCategory: output: void");
    }

    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos EditorialEntity a una lista de
     * objetos EditorialDTO (json)
     *
     * @param entityList corresponde a la lista de editoriales de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de editoriales en forma DTO (json)
     */
    private List<CategoryDTO> listEntity2DetailDTO(List<CategoryEntity> entityList) {
        List<CategoryDTO> list = new ArrayList<>();
        for (CategoryEntity entity : entityList) {
            list.add(new CategoryDTO(entity));
        }
        return list;
    }
    
    private ExecutorService executorService = java.util.concurrent.Executors.newCachedThreadPool();

    @GET
    @Path(value = "{categoryId: \\d+}")
    private CategoryDTO doGetCategory(@PathParam("categoryId") Long categoryId)
    {
        LOGGER.log(Level.INFO, "CategoryResource getCategoria: input: {0}", categoryId);
        CategoryEntity categoryEntity = categoryLogic.getCategory(categoryId);
        if (categoryEntity == null)
        {
            throw new WebApplicationException("El recurso /categories/" + categoryId + " no existe.", 404);
        }
        CategoryDTO detailDTO = new CategoryDTO(categoryEntity);
        LOGGER.log(Level.INFO, "CategoryResource getCategoria: output: {0}", detailDTO.toString());
        return detailDTO;
    }
}

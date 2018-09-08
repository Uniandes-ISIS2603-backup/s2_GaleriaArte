/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.ejb;

import co.edu.uniandes.csw.galeriaarte.entities.CategoryEntity;
import co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.galeriaarte.persistence.CategoryPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author jf.copete
 */

@Stateless
public class CategoryLogic {
    private static final Logger LOGGER = Logger.getLogger(CategoryLogic.class.getName());

    @Inject
    private CategoryPersistence persistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    /**
     * Crea una categoria en la persistencia.
     *
     * @param categoryEntity La entidad que representa la categoria a
     * persistir.
     * @return La entiddad de la categoria luego de persistirla.
     * @throws BusinessLogicException Si la categoria a persistir ya existe.
     */
    public CategoryEntity createCategory(CategoryEntity categoryEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la categoria");
        // Verifica la regla de negocio que dice que no puede haber dos categorias con el mismo nombre
        if (persistence.findByName(categoryEntity.getName()) != null) {
            throw new BusinessLogicException("Ya existe una Editorial con el nombre \"" + categoryEntity.getName() + "\"");
        }
        // Invoca la persistencia para crear la categoria
        persistence.create(categoryEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación de la categoria");
        return categoryEntity;
    }

    /**
     * Obtener todas las categorias existentes en la base de datos.
     *
     * @return una lista de categorias.
     */
    public List<CategoryEntity> getCategories() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las categorias");
        // Note que, por medio de la inyección de dependencias se llama al método "findAll()" que se encuentra en la persistencia.
        List<CategoryEntity> categories = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las categorias");
        return categories;
    }

    /**
     * Obtener una categoria por medio de su id.
     *
     * @param categoryId: id de la categoria para ser buscada.
     * @return la categoria solicitada por medio de su id.
     */
    public CategoryEntity getCategory(Long categoryId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la categoria con id = {0}", categoryId);
        // Note que, por medio de la inyección de dependencias se llama al método "find(id)" que se encuentra en la persistencia.
        CategoryEntity categoryEntity = persistence.find(categoryId);
        if (categoryEntity == null) {
            LOGGER.log(Level.SEVERE, "La categoria con el id = {0} no existe", categoryId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar la categoria con id = {0}", categoryId);
        return categoryEntity;
    }

    /**
     * Actualizar una categoria.
     *
     * @param categoryId: id de la categoria para buscarla en la base de
     * datos.
     * @param categoryEntity: categoria con los cambios para ser actualizada,
     * por ejemplo el nombre.
     * @return la editorial con los cambios actualizados en la base de datos.
     */
    public CategoryEntity updateCategory(Long categoryId, CategoryEntity categoryEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la categoria con id = {0}", categoryId);
        // Note que, por medio de la inyección de dependencias se llama al método "update(entity)" que se encuentra en la persistencia.
        CategoryEntity newEntity = persistence.update(categoryEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la categoria con id = {0}", categoryEntity.getId());
        return newEntity;
    }

    /**
     * Borrar un categoria
     *
     * @param categoryID: id de la categoria a borrar
     */
    public void deleteCategory(Long categoryID) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la categoria con id = {0}", categoryID);
        // Note que, por medio de la inyección de dependencias se llama al método "delete(id)" que se encuentra en la persistencia.
        persistence.delete(categoryID);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la categoria con id = {0}", categoryID);
    }
}

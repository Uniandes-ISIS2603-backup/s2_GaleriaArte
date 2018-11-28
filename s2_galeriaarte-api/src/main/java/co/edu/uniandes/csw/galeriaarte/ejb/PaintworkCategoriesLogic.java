/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.ejb;

import co.edu.uniandes.csw.galeriaarte.entities.CategoryEntity;
import co.edu.uniandes.csw.galeriaarte.entities.PaintworkEntity;
import co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.galeriaarte.persistence.CategoryPersistence;
import co.edu.uniandes.csw.galeriaarte.persistence.PaintworkPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la relación entre
 * la entidad de Paintwork  y Category.
 *
 * @author s.acostav
 */
@Stateless
public class PaintworkCategoriesLogic
{
    private static final Logger LOGGER = Logger.getLogger(PaintworkCategoriesLogic.class.getName());

    @Inject
    private CategoryPersistence categoryPersistence;

    @Inject
    private PaintworkPersistence paintworkPersistence;

    /**
     * Asocia un category  existente a un Paintwork
     *
     * @param paintworksId Identificador de la instancia de 
     * @param categoriesId Identificador de la instancia de category
     * @return Instancia de categoryEntity que fue asociada a paintwork
     */
    public CategoryEntity addCategory(Long paintworksId, Long categoriesId) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de asociarle un tipo al obra con id = {0}", paintworksId);
        PaintworkEntity paintworkEntity = paintworkPersistence.find(paintworksId);
        CategoryEntity categoryEntity = categoryPersistence.find(categoriesId);
        categoryEntity.getObra().add(paintworkEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociarle un tipo al obra con id = {0}", paintworksId);
        return categoryPersistence.find(categoriesId);
    }

    /**
     * Obtiene una colección de instancias de categoryEntity asociadas a una
     * instancia de paintwork
     *
     * @param paintworksId Identificador de la instancia de paintwork
     * @return Colección de instancias de categoryEntity asociadas a la instancia de
     * paintwork
     */
    public List<CategoryEntity> getCategories(Long paintworksId) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los tipos del obra con id = {0}", paintworksId);
        return paintworkPersistence.find(paintworksId).getCategory();
    }

    /**
     * Obtiene una instancia de categoryEntity asociada a una instancia de paintwork
     *
     * @param paintworksId Identificador de la instancia de paintwork
     * @param categoriesId Identificador de la instancia de category
     * @return La entidadd de tipo del obra
     * @throws BusinessLogicException Si el tipo no está asociado al obra
     */
    public CategoryEntity getCategory(Long paintworksId, Long categoriesId) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el tipo con id ={0} del obra con id = {1}", new Object[]{categoriesId, paintworksId});
        List<CategoryEntity> categories = paintworkPersistence.find(paintworksId).getCategory();
        CategoryEntity categoryEntity = categoryPersistence.find(categoriesId);
        int index = categories.indexOf(categoryEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar  el tipo con id ={0} del obra con id = {1}", new Object[]{categoriesId, paintworksId});
        if (index >= 0)
        {
            return categories.get(index);
        }
        throw new BusinessLogicException("El tipo no está asociado al obra");
    }

    /**
     * Remplaza las instancias de category asociadas a una instancia de paintwork
     *
     * @param paintworkId Identificador de la instancia de paintwork
     * @param categories Colección de instancias de categoryEntity a asociar a instancia
     * de paintwork
     * @return Nueva colección de categoryEntity asociada a la instancia de paintwork
     */
    public List<CategoryEntity> replaceCategories(Long paintworkId, List<CategoryEntity> categories) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar los tipos asocidos al paintwork con id = {0}", paintworkId);
        PaintworkEntity paintworkEntity = paintworkPersistence.find(paintworkId);
        List<CategoryEntity> categoryList = categoryPersistence.findAll();
        for (CategoryEntity category : categoryList)
        {
            if (categories.contains(category)) 
            {
                if (!category.getObra().contains(paintworkEntity)) 
                {
                    category.getObra().add(paintworkEntity);
                }
            } 
            else 
            {
                category.getObra().remove(paintworkEntity);
            }
        }
        paintworkEntity.setCategory(categories);
        LOGGER.log(Level.INFO, "Termina proceso de reemplazar los tipos asocidos al paintwork con id = {0}", paintworkId);
        return paintworkEntity.getCategory();
    }

    /**
     * Desasocia un category existente de un paintwork existente
     *
     * @param paintworksId Identificador de la instancia de paintwork
     * @param categoriesId Identificador de la instancia de category
     */
    public void removeCategory(Long paintworksId, Long categoriesId)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un tipo del paintwork con id = {0}", paintworksId);
        PaintworkEntity paintworkEntity = paintworkPersistence.find(paintworksId);
        CategoryEntity categoryEntity = categoryPersistence.find(categoriesId);
        categoryEntity.getObra().remove(paintworkEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar un tipo del paintwork con id = {0}", paintworksId);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.persistence;

import co.edu.uniandes.csw.galeriaarte.entities.CategoryEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author jf.copete
 */

@Stateless
public class CategoryPersistence {
     private static final Logger LOGGER = Logger.getLogger(CategoryPersistence.class.getName());

    @PersistenceContext(unitName = "InterArtPU")
    protected EntityManager em;

    /**
     * Método para persisitir la entidad en la base de datos.
     *
     * @param categoryEntity objeto category que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public CategoryEntity create(CategoryEntity categoryEntity)
    {
        LOGGER.log(Level.INFO, "Creando una Categoria nueva");
        /* Note que hacemos uso de un método propio de EntityManager para persistir la editorial en la base de datos.
        Es similar a "INSERT INTO table_name (column1, column2, column3, ...) VALUES (value1, value2, value3, ...);" en SQL.
         */
        em.persist(categoryEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear una Categoria nueva");
        return categoryEntity;
    }

    /**
     * Devuelve todas las categorias de la base de datos.
     *
     * @return una lista con todas las editoriales que encuentre en la base de
     * datos, "select u from EditorialEntity u" es como un "select * from
     * EditorialEntity;" - "SELECT * FROM table_name" en SQL.
     */
    public List<CategoryEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todas las categorias");
        // Se crea un query para buscar todas las editoriales en la base de datos.
        TypedQuery query = em.createQuery("select u from CategoryEntity u", CategoryEntity.class);
        // Note que en el query se hace uso del método getResultList() que obtiene una lista de editoriales.
        return query.getResultList();
    }

    /**
     * Busca si hay alguna editorial con el id que se envía de argumento
     *
     * @param categoryId: id correspondiente a la editorial buscada.
     * @return una editorial.
     */
    public CategoryEntity find(Long categoryId) {
        LOGGER.log(Level.INFO, "Consultando categoria con id={0}", categoryId);
        /* Note que se hace uso del metodo "find" propio del EntityManager, el cual recibe como argumento 
        el tipo de la clase y el objeto que nos hara el filtro en la base de datos en este caso el "id"
        Suponga que es algo similar a "select * from EditorialEntity where id=id;" - "SELECT * FROM table_name WHERE condition;" en SQL.
         */
        return em.find(CategoryEntity.class, categoryId);
    }

    /**
     * Actualiza una editorial.
     *
     * @param categoryID: la editorial que viene con los nuevos cambios.
     * Por ejemplo el nombre pudo cambiar. En ese caso, se haria uso del método
     * update.
     * @return una editorial con los cambios aplicados.
     */
    public CategoryEntity update(CategoryEntity categoryID) {
        LOGGER.log(Level.INFO, "Actualizando categoria con id = {0}", categoryID.getId());
        /* Note que hacemos uso de un método propio del EntityManager llamado merge() que recibe como argumento
        la editorial con los cambios, esto es similar a 
        "UPDATE table_name SET column1 = value1, column2 = value2, ... WHERE condition;" en SQL.
         */
        LOGGER.log(Level.INFO, "Saliendo de actualizar la editorial con id = {0}", categoryID.getId());
        return em.merge(categoryID);
    }

    /**
     * Borra una editorial de la base de datos recibiendo como argumento el id
     * de la editorial
     *
     * @param categoryId: id correspondiente a la editorial a borrar.
     */
    public void delete(Long categoryId) {
        LOGGER.log(Level.INFO, "Borrando editorial con id = {0}", categoryId);
        // Se hace uso de mismo método que esta explicado en public EditorialEntity find(Long id) para obtener la editorial a borrar.
        CategoryEntity entity = em.find(CategoryEntity.class, categoryId);
        /* Note que una vez obtenido el objeto desde la base de datos llamado "entity", volvemos hacer uso de un método propio del
         EntityManager para eliminar de la base de datos el objeto que encontramos y queremos borrar.
         Es similar a "delete from EditorialEntity where id=id;" - "DELETE FROM table_name WHERE condition;" en SQL.*/
        em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar la categoria con id = {0}", categoryId);
    }

    /**
     * Busca si hay alguna editorial con el nombre que se envía de argumento
     *
     * @param name: Nombre de la editorial que se está buscando
     * @return null si no existe ninguna editorial con el nombre del argumento.
     * Si existe alguna devuelve la primera.
     */
    public CategoryEntity findByName(String name)
    {
        LOGGER.log(Level.INFO, "Consultando categoria por nombre ={0}", name);
        // Se crea un query para buscar editoriales con el nombre que recibe el método como argumento. ":name" es un placeholder que debe ser remplazado
        TypedQuery query = em.createQuery("Select e From CategoryEntity e where e.name = :name", CategoryEntity.class);
        // Se remplaza el placeholder ":name" con el valor del argumento 
        query = query.setParameter("name", name);
        // Se invoca el query se obtiene la lista resultado
        List<CategoryEntity> sameName = query.getResultList();
        CategoryEntity result;
        if (sameName == null) {
            result = null;
        } else if (sameName.isEmpty()) {
            result = null;
        } else {
            result = sameName.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar categoria por nombre  ={0}", name);
        return result;
    }
}

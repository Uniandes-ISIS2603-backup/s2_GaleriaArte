/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.persistence;

import co.edu.uniandes.csw.galeriaarte.entities.PaintworkEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.eclipse.persistence.internal.jpa.EntityManagerImpl;

/**
 *
 * @author estudiante
 */
public class PaintworkPersistence {
    private static final Logger LOGGER = Logger.getLogger(FeedBackPersistence.class.getName());
    @PersistenceContext(unitName = "InterArtPU")
    protected EntityManager em;
    
     /**
     * Método para persisitir la entidad en la base de datos.
     *
     * @param paintworkEntity objeto editorial que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public PaintworkEntity create(PaintworkEntity paintworkEntity) {
        LOGGER.log(Level.INFO, "Creando una pintura nueva");
        /* Note que hacemos uso de un método propio de EntityManager para persistir la editorial en la base de datos.
        Es similar a "INSERT INTO table_name (column1, column2, column3, ...) VALUES (value1, value2, value3, ...);" en SQL.
         */
        em.persist(paintworkEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear una pintura nueva");
        return paintworkEntity;
    }

    /**
     * Devuelve todas las editoriales de la base de datos.
     *
     * @return una lista con todas las editoriales que encuentre en la base de
     * datos, "select u from EditorialEntity u" es como un "select * from
     * EditorialEntity;" - "SELECT * FROM table_name" en SQL.
     */
    public List<PaintworkEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todas las obras");
        // Se crea un query para buscar todas las obras en la base de datos.
        TypedQuery query = em.createQuery("select u from PaintworkEntity u", PaintworkEntity.class);
        // Note que en el query se hace uso del método getResultList() que obtiene una lista de obras.
        return query.getResultList();
    }

    /**
     * Busca si hay alguna obra con el id que se envía de argumento
     *
     * @param paintworkId: id correspondiente a la editorial buscada.
     * @return una editorial.
     */
    public PaintworkEntity find(Long paintworkId) {
        LOGGER.log(Level.INFO, "Consultando obra con id={0}", paintworkId);
        /* Note que se hace uso del metodo "find" propio del EntityManager, el cual recibe como argumento 
        el tipo de la clase y el objeto que nos hara el filtro en la base de datos en este caso el "id"
        Suponga que es algo similar a "select * from EditorialEntity where id=id;" - "SELECT * FROM table_name WHERE condition;" en SQL.
         */
        return em.find(PaintworkEntity.class, paintworkId);
    }

    /**
     * Actualiza una obra.
     *
     * @param paintworkEntity: la editorial que viene con los nuevos cambios.
     * Por ejemplo el nombre pudo cambiar. En ese caso, se haria uso del método
     * update.
     * @return una editorial con los cambios aplicados.
     */
    public PaintworkEntity update(PaintworkEntity paintworkEntity) {
        LOGGER.log(Level.INFO, "Actualizando obra con id = {0}", paintworkEntity.getId());
        /* Note que hacemos uso de un método propio del EntityManager llamado merge() que recibe como argumento
        la obra con los cambios, esto es similar a 
        "UPDATE table_name SET column1 = value1, column2 = value2, ... WHERE condition;" en SQL.
         */
        LOGGER.log(Level.INFO, "Saliendo de actualizar la obra con id = {0}", paintworkEntity.getId());
        return em.merge(paintworkEntity);
    }

    /**
     * Borra una editorial de la base de datos recibiendo como argumento el id
     * de la editorial
     *
     * @param paintworkId: id correspondiente a la editorial a borrar.
     */
    public void delete(Long paintworkId) {
        LOGGER.log(Level.INFO, "Borrando la obra con id = {0}", paintworkId);
        // Se hace uso de mismo método que esta explicado en public EditorialEntity find(Long id) para obtener la editorial a borrar.
        PaintworkEntity entity = em.find(PaintworkEntity.class, paintworkId);
        /* Note que una vez obtenido el objeto desde la base de datos llamado "entity", volvemos hacer uso de un método propio del
         EntityManager para eliminar de la base de datos el objeto que encontramos y queremos borrar.
         Es similar a "delete from EditorialEntity where id=id;" - "DELETE FROM table_name WHERE condition;" en SQL.*/
        em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar la obra con id = {0}", paintworkId);
    }

    /**
     * Busca si hay alguna editorial con el nombre que se envía de argumento
     *
     * @param name: Nombre de la editorial que se está buscando
     * @return null si no existe ninguna editorial con el nombre del argumento.
     * Si existe alguna devuelve la primera.
     */
    public PaintworkEntity findByName(String name) {
        LOGGER.log(Level.INFO, "Consultando la obra por nombre ", name);
        // Se crea un query para buscar obras con el nombre que recibe el método como argumento. ":name" es un placeholder que debe ser remplazado
        TypedQuery query = em.createQuery("Select e From EditorialEntity e where e.name = :name", PaintworkEntity.class);
        // Se remplaza el placeholder ":name" con el valor del argumento 
        query = query.setParameter("name", name);
        // Se invoca el query se obtiene la lista resultado
        List<PaintworkEntity> sameName = query.getResultList();
        PaintworkEntity result;
        if (sameName == null) {
            result = null;
        } else if (sameName.isEmpty()) {
            result = null;
        } else {
            result = sameName.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar la obra por nombre ", name);
        return result;
    }
            
    
}

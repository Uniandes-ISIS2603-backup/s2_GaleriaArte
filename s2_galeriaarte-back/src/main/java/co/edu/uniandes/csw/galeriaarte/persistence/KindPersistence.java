/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.persistence;


import co.edu.uniandes.csw.galeriaarte.ejb.KindLogic;


import co.edu.uniandes.csw.galeriaarte.entities.KindEntity;
import java.util.List;
import java.util.logging.Level;

 
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author estudiante
 */
@Stateless
public class KindPersistence {
        @PersistenceContext(unitName = "InterArtPU")
    protected EntityManager em;
    
        private static final Logger LOGGER = Logger.getLogger(KindLogic.class.getName());
    
  /**
     * Método para persisitir la entidad en la base de datos.
     *
     * @param kindEntity objeto kind que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public KindEntity create(KindEntity kindEntity) {
        LOGGER.log(Level.INFO, "Creando un kind nuevo");
      
        em.persist(kindEntity);
  LOGGER.log(Level.INFO, "Saliendo de crear un kind nuevo");
        return kindEntity;
    }
    
        /**
     * Devuelve todos los kinds de la base de datos.
     *
     * @return una lista con todos los kinds que encuentre en la base de datos
     */
    public List<KindEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todos los kinds");
        // Se crea un query para buscar todos los kinds en la base de datos.
        TypedQuery query = em.createQuery("select u from KindEntity u", KindEntity.class);
        // Note que en el query se hace uso del método getResultList() que obtiene una lista de cvs.
        return query.getResultList();
    }
        /**
     * Busca si hay algun kind con el id que se envía de argumento
     * @param kindId: id correspondiente al kind buscado.
     * @return un kind.
     */
    public KindEntity find(Long kindId) {
        LOGGER.log(Level.INFO, "Consultando cv con id={0}", kindId);
     
        return em.find(KindEntity.class, kindId);
    }
    /**
     * Actualiza un kind
     * @param kindEntity: el kind que viene con los nuevos cambios.
     * @return un kind con los cambios aplicados.
     */
    public KindEntity update(KindEntity kindEntity) {
        LOGGER.log(Level.INFO, "Actualizando kind con id = {0}", kindEntity.getId());
        
        LOGGER.log(Level.INFO, "Saliendo de actualizar el kind con id = {0}", kindEntity.getId());
        return em.merge(kindEntity);
    }
        /**
     * Borra un kind de la base de datos recibiendo como argumento el id
     * del kind
     *
     * @param kindID: id correspondiente a la editorial a borrar.
     */
    public void delete(Long kindID) {
        LOGGER.log(Level.INFO, "Borrando el kin con id = {0}", kindID);
    
        KindEntity entity = em.find(KindEntity.class, kindID);
     
        em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar el kind con id = {0}", kindID);
    }
     /**
     * Busca si hay algun kind con el nombre que se envía de argumento
     *
     * @param name: Nombre del kind que se está buscando
     * @return null si no existe ningun kind con el nombre del argumento.
     * Si existe alguno devuelve el primero.
     */
    public KindEntity findByName(String name) 
    {
        LOGGER.log(Level.INFO, "Consultando el kind por nombre ", name);
        // Se crea un query para buscar cvs con el nombre que recibe el método como argumento. ":name" es un placeholder que debe ser remplazado
        TypedQuery query = em.createQuery("Select e From KindEntity e where e.name = :name", KindEntity.class);
        // Se remplaza el placeholder ":name" con el valor del argumento 
        query = query.setParameter("name", name);
        // Se invoca el query se obtiene la lista resultado
        List<KindEntity> sameName = query.getResultList();
        KindEntity result;
        if (sameName == null) 
        {
            result = null;
        }
        else if (sameName.isEmpty()) 
        {
            result = null;
        }
        else
        {
            result = sameName.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar el kind por nombre ", name);
        return result;
    }
    
}

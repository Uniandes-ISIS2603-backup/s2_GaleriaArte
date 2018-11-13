/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.persistence;

import co.edu.uniandes.csw.galeriaarte.entities.CVEntity;

import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author LauraManrique
 */
@Stateless
public class CVPersistence {
    
    private static final Logger LOGGER = Logger.getLogger(CVPersistence.class.getName());

    @PersistenceContext(unitName = "InterArtPU")
    protected EntityManager em;
    
    
     /**
     * Método para persisitir la entidad en la base de datos.
     *
     * @param cvEntity objeto editorial que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public CVEntity create(CVEntity cvEntity) {
       LOGGER.log(Level.INFO, "Creando un cv nuevo");
      
        em.persist(cvEntity);
         LOGGER.log(Level.INFO, "Saliendo de crear un cv nuevo");
        return cvEntity;
    }
    
        /**
     * Devuelve todos los cvs de la base de datos.
     *
     * @return una lista con todos los cvs que encuentre en la base de
     */
    public List<CVEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todos los cvs");
        // Se crea un query para buscar todos los cvs en la base de datos.
        TypedQuery query = em.createQuery("select u from CVEntity u", CVEntity.class);
        // Note que en el query se hace uso del método getResultList() que obtiene una lista de cvs.
        return query.getResultList();
    }
        /**
     * Busca si hay algun cv con el id que se envía de argumento
     *
     * @param cvId: id correspondiente a la editorial buscada.
     * @return una editorial.
     */
    public CVEntity find(Long cvId) {
        LOGGER.log(Level.INFO, "Consultando cv con id={0}", cvId);
     
        return em.find(CVEntity.class, cvId);
    }
    /**
     * Actualiza un cv
     *
     * @param cvEntity: el cv que viene con los nuevos cambios.
     * @return un cv con los cambios aplicados.
     */
    public CVEntity update(CVEntity cvEntity) {
        LOGGER.log(Level.INFO, "Actualizando cv con id = {0}", cvEntity.getId());
        
        LOGGER.log(Level.INFO, "Saliendo de actualizar el cv con id = {0}", cvEntity.getId());
        return em.merge(cvEntity);
    }
        /**
     * Borra un cv de la base de datos recibiendo como argumento el id
     * del cv
     *
     * @param cvId: id correspondiente a la editorial a borrar.
     */
    public void delete(Long cvId) {
        LOGGER.log(Level.INFO, "Borrando el cv con id = {0}", cvId);
    
        CVEntity entity = em.find(CVEntity.class, cvId);
     
        em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar el cv con id = {0}", cvId);
    }
     /**
     * Busca si hay algun cv con el nombre que se envía de argumento
     *
     * @param name: Nombre del cv que se está buscando
     * @return null si no existe ningun cv con el nombre del argumento.
     * Si existe alguno devuelve el primero.
     */
    public CVEntity findByName(String name) {
        LOGGER.log(Level.INFO, "Consultando el cv por nombre ", name);
        // Se crea un query para buscar cvs con el nombre que recibe el método como argumento. ":name" es un placeholder que debe ser remplazado
        TypedQuery query = em.createQuery("Select e From CVlEntity e where e.name = :name", CVEntity.class);
        // Se remplaza el placeholder ":name" con el valor del argumento 
        query = query.setParameter("name", name);
        // Se invoca el query se obtiene la lista resultado
        List<CVEntity> sameName = query.getResultList();
        CVEntity result;
        if (sameName == null) {
            result = null;
        } else if (sameName.isEmpty()) {
            result = null;
        } else {
            result = sameName.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar el cv por nombre ", name);
        return result;
    }
            
    
}

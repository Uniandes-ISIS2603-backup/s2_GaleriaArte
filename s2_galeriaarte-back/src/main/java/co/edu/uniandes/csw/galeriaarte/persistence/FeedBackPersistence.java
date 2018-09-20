/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.persistence;

import co.edu.uniandes.csw.galeriaarte.entities.FeedBackEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author s.restrepos1
 */
@Stateless
public class FeedBackPersistence {

    private static final Logger LOGGER = Logger.getLogger(FeedBackPersistence.class.getName());
    @PersistenceContext(unitName = "InterArtPU")
    protected EntityManager em;
     /**
     * Método para persisitir la entidad en la base de datos.
     * @param feedEntity objeto ExtraService que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public FeedBackEntity create(FeedBackEntity feedEntity)
    {
        LOGGER.log(Level.INFO, "Creando un nuevo feedback");
        
        em.persist(feedEntity);
        LOGGER.log(Level.INFO, "FeedBack creado");
        return feedEntity;
    }
    
     /**
     * Devuelve todos los feedbacks de la base de datos.
     * @return una lista con todos los feedbacks extras que encuentre en la base de datos
     */
    public List<FeedBackEntity> findAll()
    {
        LOGGER.log(Level.INFO, "Consultando todos las calificaciones");
        TypedQuery query = em.createQuery("select u from FeedBackEntity u", FeedBackEntity.class);
        return query.getResultList();
    }
     /**
     * Devuelve el feedback con id dado de la base de datos.
     * @param feedBackId: id correspondiente al fedback buscado.
     * @return un feedback que encuentre en la base de datos con el id
     */
     public FeedBackEntity find(Long feedBackId) 
     {
        LOGGER.log(Level.INFO, "Consultando la calificacion con id={0}", feedBackId);
     
        return em.find(FeedBackEntity.class, feedBackId);
    }
      /**
     * Actualiza una calificacion.
     *
     * @param feedEntity:
     * @return feedback con los cambios aplicados.
     */
     public FeedBackEntity update(FeedBackEntity feedEntity) 
     {
        LOGGER.log(Level.INFO, "Actualizando el feedback con id={0}", feedEntity.getId());
        
        return em.merge(feedEntity);
    }
     
   /**
     * Borra una calificacion de la base de datos recibiendo como argumento el id
     * 
     *
     * @param feedId: id correspondiente a la calificacion  borrar.
     */
      public void delete(Long feedId) {

        LOGGER.log(Level.INFO, "Borrando el feedback con id={0}", feedId);
        FeedBackEntity feedEntity = em.find(FeedBackEntity.class, feedId);
        
        em.remove(feedEntity);
    }
    
    
}

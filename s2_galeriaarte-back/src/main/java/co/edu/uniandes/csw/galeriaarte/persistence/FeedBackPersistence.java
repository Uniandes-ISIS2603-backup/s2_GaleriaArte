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
    
    public FeedBackEntity create(FeedBackEntity feedEntity)
    {
        LOGGER.log(Level.INFO, "Creando un nuevo feedback");
        
        em.persist(feedEntity);
        LOGGER.log(Level.INFO, "FeedBack creado");
        return feedEntity;
    }
    public List<FeedBackEntity> findAll()
    {
        LOGGER.log(Level.INFO, "Consultando todos las calificaciones");
        TypedQuery query = em.createQuery("select u from FeedBackEntity u", FeedBackEntity.class);
        return query.getResultList();
    }
     public FeedBackEntity find(Long authorsId) 
     {
        LOGGER.log(Level.INFO, "Consultando la calificacion con id={0}", authorsId);
     
        return em.find(FeedBackEntity.class, authorsId);
    }
     public FeedBackEntity update(FeedBackEntity feedEntity) 
     {
        LOGGER.log(Level.INFO, "Actualizando el feedback con id={0}", feedEntity.getId());
        
        return em.merge(feedEntity);
    }

      public void delete(Long feedId) {

        LOGGER.log(Level.INFO, "Borrando el feedback con id={0}", feedId);
        FeedBackEntity authorEntity = em.find(FeedBackEntity.class, feedId);
        
        em.remove(authorEntity);
    }
    
    
}

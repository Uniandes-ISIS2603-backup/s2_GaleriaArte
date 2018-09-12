/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.persistence;

import co.edu.uniandes.csw.galeriaarte.entities.CVEntity;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author estudiante
 */
@Stateless
public class CVPersistence {
    
    
            @PersistenceContext(unitName = "InterArtPU")
    private static final Logger LOGGER = Logger.getLogger(FeedBackPersistence.class.getName());
    @PersistenceContext(unitName = "InterartPU")
    protected EntityManager em;
    
    

    public CVEntity create(CVEntity cvEntity) {
  
        em.persist(cvEntity);
        return cvEntity;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.persistence;

import co.edu.uniandes.csw.galeriaarte.entities.CVEntity;
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
    protected EntityManager em;
    
    

    public CVEntity create(CVEntity cvEntity) {
  
        em.persist(cvEntity);
        return cvEntity;
    }
}

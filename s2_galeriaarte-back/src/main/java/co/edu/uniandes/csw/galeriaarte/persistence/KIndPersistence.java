/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.persistence;


import co.edu.uniandes.csw.galeriaarte.ejb.KindLogic;


import co.edu.uniandes.csw.galeriaarte.entities.KindEntity;

 
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author estudiante
 */
@Stateless
public class KIndPersistence {
        @PersistenceContext(unitName = "InterArtPU")
    protected EntityManager em;
    
        private static final Logger LOGGER = Logger.getLogger(KindLogic.class.getName());
    

    public KindEntity create(KindEntity kindEntity) {
       
        em.persist(kindEntity);

        return kindEntity;
    }
}

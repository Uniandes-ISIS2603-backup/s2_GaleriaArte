/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.persistence;

import co.edu.uniandes.csw.galeriaarte.entities.CVEntity;
import co.edu.uniandes.csw.galeriaarte.entities.KindEntity;
import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;
import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author estudiante
 */
@Stateless
public class KIndPersistence {
        @PersistenceContext(unitName = "Interart")
    protected EntityManager em;
    
    

    public KindEntity create(KindEntity kindEntity) {
        LOGGER.log(Level.INFO, "Creando un kind");
  
        em.persist(kindEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear  un kind");
        return kindEntity;
    }
}

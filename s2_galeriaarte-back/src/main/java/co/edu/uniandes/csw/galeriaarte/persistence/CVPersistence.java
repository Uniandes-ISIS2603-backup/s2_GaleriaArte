/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.persistence;

import co.edu.uniandes.csw.galeriaarte.entities.CVEntity;
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
public class CVPersistence {
    
    
    @PersistenceContext(unitName = "Interart")
    protected EntityManager em;
    
    

    public CVEntity create(CVEntity cvEntity) {
        LOGGER.log(Level.INFO, "Creando un cv");
  
        em.persist(cvEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear  un cv");
        return cvEntity;
    }
}

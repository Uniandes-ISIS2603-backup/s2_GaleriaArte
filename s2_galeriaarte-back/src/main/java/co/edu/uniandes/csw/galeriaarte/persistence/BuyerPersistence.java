/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.persistence;
import co.edu.uniandes.csw.galeriaarte.entities.BuyerEntity;
import javax.ejb.Stateless;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author sara acosta
 */

@Stateless

public class BuyerPersistence {
    
    private static final Logger LOGGER = Logger.getLogger(BuyerPersistence.class.getName());
    
    @PersistenceContext(unitName = "InterArtPU")
    protected EntityManager em;
    
    public BuyerEntity create(BuyerEntity buyerEntity){
        LOGGER.log(Level.INFO, "Creando un comprador nuevo");
        
        em.persist(buyerEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear un comprador");
        return buyerEntity;
    }
    
}

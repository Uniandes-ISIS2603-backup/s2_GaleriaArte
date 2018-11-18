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
import java.util.List;
import javax.persistence.TypedQuery;
import javax.persistence.PersistenceContext;

/**
 *
 * @author sara acosta s.acostav
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
    
    public List<BuyerEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todos los compradores");
        // Se crea un query para buscar todas los buyeras en la base de datos.
        TypedQuery query = em.createQuery("select u from BuyerEntity u", BuyerEntity.class);
        // Note que en el query se hace uso del método getResultList() que obtiene una lista de buyeras.
        return query.getResultList();
    }
     public BuyerEntity update(BuyerEntity buyerEntity) {
        LOGGER.log(Level.INFO, "Actualizando el comprador con id={0}", buyerEntity.getId());

        return em.merge(buyerEntity);
    }

     public BuyerEntity find(Long buyerId) {
        LOGGER.log(Level.INFO, "Consultando el comprador con id={0}", buyerId);

        return em.find(BuyerEntity.class, buyerId);
    }
    public void delete(Long buyerId) {
        LOGGER.log(Level.INFO, "Borrando el comprador con id={0}", buyerId);
        BuyerEntity buyerEntity = em.find(BuyerEntity.class, buyerId);

        em.remove(buyerEntity);
    }
}

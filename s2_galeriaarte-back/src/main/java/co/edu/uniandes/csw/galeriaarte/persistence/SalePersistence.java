/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.persistence;

import co.edu.uniandes.csw.galeriaarte.entities.SaleEntity;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author s.restrepos1
 */
@Stateless
public class SalePersistence
{
    private static final Logger LOGGER = Logger.getLogger(FeedBackPersistence.class.getName());
            @PersistenceContext(unitName = "InterArtPU")

    protected EntityManager em;
    
    public SaleEntity create(SaleEntity saleEntity)
    {
        LOGGER.log(Level.INFO, "Creando una nueva venta");
        
        em.persist(saleEntity);
        LOGGER.log(Level.INFO, "venta creada");
        return saleEntity;
    }
    
    public SaleEntity find(Long Id) {
        LOGGER.log(Level.INFO, "Consultando el comprador con id={0}", Id);

        return em.find(SaleEntity.class, Id);
    }
    
    public SaleEntity update(SaleEntity saleEntity) {
        LOGGER.log(Level.INFO, "Actualizando el comprador con id={0}", saleEntity.getId());

        return em.merge(saleEntity);
    }
    
    public void delete(Long buyerId) {
        LOGGER.log(Level.INFO, "Borrando el comprador con id={0}", buyerId);
        SaleEntity saleEntity = em.find(SaleEntity.class, buyerId);
        em.remove(saleEntity);
    }
}

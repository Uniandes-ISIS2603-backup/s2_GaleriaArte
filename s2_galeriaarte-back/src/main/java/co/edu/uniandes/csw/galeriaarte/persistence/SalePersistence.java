/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.persistence;

import co.edu.uniandes.csw.galeriaarte.entities.SaleEntity;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;

/**
 *
 * @author s.restrepos1
 */
public class SalePersistence
{
    private static final Logger LOGGER = Logger.getLogger(FeedBackPersistence.class.getName());
    
    protected EntityManager em;
    
    public SaleEntity create(SaleEntity saleEntity)
    {
        LOGGER.log(Level.INFO, "Creando una nueva venta");
        
        em.persist(saleEntity);
        LOGGER.log(Level.INFO, "venta creada");
        return saleEntity;
    }
}

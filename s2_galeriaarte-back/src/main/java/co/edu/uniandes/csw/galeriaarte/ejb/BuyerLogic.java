/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.ejb;
import co.edu.uniandes.csw.galeriaarte.entities.BuyerEntity;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import co.edu.uniandes.csw.galeriaarte.persistence.BuyerPersistence;

/**
 *
 * @author estudiante
 */

@Stateless

public class BuyerLogic {
    
    
    private final static Logger LOGGER = Logger.getLogger(BuyerLogic.class.getName());
    
    @Inject
    
    private BuyerPersistence persistence;
  
    public BuyerEntity createBuyer (BuyerEntity buyerEntity){
        
        LOGGER.log(Level.INFO, "Inicia el proceso de creacion de la editorial");
        // Verifica la regla de negocio que dice que no pueden existir dos compradores con el mismo login
        //if( ) 
        
        //Invoca la persistencia para crear el comprador 
        persistence.create(buyerEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación de comprador");
        
        return buyerEntity;
    }
}

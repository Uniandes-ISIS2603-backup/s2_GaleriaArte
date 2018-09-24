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
import java.util.List;
import co.edu.uniandes.csw.galeriaarte.persistence.BuyerPersistence;

/**
 *
 * @author s.acostav
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
    
    public List<BuyerEntity> getBuyers(){
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los compradores");
        List<BuyerEntity> lista = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los autores");
        return lista;
    }
    
    public BuyerEntity getBuyer(Long buyerID){
        
        LOGGER.log(Level.INFO, "Termina el proceso de consultar el artista con id = {0}", buyerID);
        BuyerEntity buyerEntity = persistence.find(buyerID);
        if(buyerEntity==null){
            LOGGER.log(Level.SEVERE, "no existe la entidad con id = {0} no existe", buyerID);
        }
        
        LOGGER.log(Level.INFO, "Termina el proceso de consultar el artista con id = {0}", buyerID);
        return buyerEntity;
    }
    
     public BuyerEntity updateBuyer(Long buyerId, BuyerEntity buyerEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el artista con id = {0}", buyerId);
        BuyerEntity newBuyerEntity = persistence.update(buyerEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el artista con id = {0}", buyerId);
        return newBuyerEntity;
    }
    
     
      
       public void deleteBuyer(Long buyerId){
        LOGGER.log(Level.INFO, "Inicia proceso de borrar al comprador con id = {0}", buyerId);
        persistence.delete(buyerId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar al compador con id = {0}", buyerId);
    }
}

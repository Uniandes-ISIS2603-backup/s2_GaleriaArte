/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.ejb;


import co.edu.uniandes.csw.galeriaarte.entities.SaleEntity;
import co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException;

import co.edu.uniandes.csw.galeriaarte.persistence.SalePersistence;
import co.edu.uniandes.csw.galeriaarte.persistence.BuyerPersistence;
import co.edu.uniandes.csw.galeriaarte.persistence.PaintworkPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author s.restrepos1
 */
@Stateless
public class SaleLogic 
{
    private static final Logger LOGGER= Logger.getLogger(SaleLogic.class.getName());
    @Inject 
    private SalePersistence persistence;
    
    
    
    @Inject
    private BuyerPersistence buyerPersistence ;
    
    @Inject
    private PaintworkPersistence paintworkPersistence ;
/**
 *
 * crea una compra siguiendo las reglas de negocio, una compra debe tener un artista, un comprador y una obra asociada, el precio de venta debe ser mayor 
 * a cero
     * @param saleEntity
     * @param buyerId
     * @return 
     * @throws co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException 
 */
    public SaleEntity createSale(SaleEntity saleEntity,  long buyerId, long paintworkId) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del cv");
       saleEntity.setBuyer(buyerPersistence.find(buyerId));
       saleEntity.setObra(paintworkPersistence.find(paintworkId));
       
        if ( saleEntity.getPrice() > 0 && saleEntity.getBuyer() != null && saleEntity.getObra()!= null)
        {
            persistence.create(saleEntity);
            LOGGER.log(Level.INFO, "Termina proceso de creación del cv");
            return saleEntity;
        }
        else
        {
            LOGGER.log(Level.INFO, "No se termino la creacion porque los datos no eran validos");
            throw new BusinessLogicException("No pueden haber campos nulos\"" );
    }
    }
    
    
    public SaleEntity getSale(Long saleID){
        
        LOGGER.log(Level.INFO, "Termina el proceso de consultar el artista con id = {0}", saleID);
        SaleEntity saleEntity = persistence.find(saleID);
        if(saleEntity==null){
            LOGGER.log(Level.SEVERE, "no existe la entidad con id = {0} no existe", saleID);
        }
        
        LOGGER.log(Level.INFO, "Termina el proceso de consultar el artista con id = {0}", saleID);
        return saleEntity;
    }
   
 
     public SaleEntity updateSale(Long Id, SaleEntity saleEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el artista con id = {0}", Id);
        SaleEntity newBuyerEntity = persistence.update(saleEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el artista con id = {0}", Id);
        return newBuyerEntity;
    }
     
     public void deleteSale(Long saleId){
        LOGGER.log(Level.INFO, "Inicia proceso de borrar al comprador con id = {0}", saleId);
        persistence.delete(saleId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar al compador con id = {0}", saleId);
    }

    public List<SaleEntity> getSales()
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las sales");
        List<SaleEntity> sales = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los sales");
        return sales;
}
}
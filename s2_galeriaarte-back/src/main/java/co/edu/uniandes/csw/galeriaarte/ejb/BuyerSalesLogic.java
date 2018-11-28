/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.ejb;

import co.edu.uniandes.csw.galeriaarte.entities.BuyerEntity;
import co.edu.uniandes.csw.galeriaarte.entities.SaleEntity;
import co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.galeriaarte.persistence.BuyerPersistence;
import co.edu.uniandes.csw.galeriaarte.persistence.SalePersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;

/**
 *
 * @author estudiante
 */
public class BuyerSalesLogic {
    
    private static final Logger LOGGER = Logger.getLogger(BuyerSalesLogic.class.getName());

    @Inject
    private SalePersistence salePersistence;

    @Inject
    private BuyerPersistence buyerPersistence;

    /**
     * Agregar un  sale a elbuyer
     *
     * @param salesId El id sale a guardar
     * @param buyersId El id de el comprador en elcual se va a guardar el
     * sale.
     * @return elobracreado.
     */
    public SaleEntity addSale(Long salesId, Long buyersId) {
        LOGGER.log(Level.INFO, "Inicia proceso de agregarle un  sale a el comprador con id = {0}", buyersId);
        BuyerEntity buyerEntity = buyerPersistence.find(buyersId);
        SaleEntity saleEntity = salePersistence.find(salesId);
        saleEntity.setBuyer(buyerEntity);
        LOGGER.log(Level.INFO, "Termina proceso de agregarle un  sale a el comprador con id = {0}", buyersId);
        return saleEntity;
    }

    /**
     * Retorna todos los sales asociados a un a buyer
     *
     * @param buyersId El ID de el comprador buscada
     * @return ellista de sales de elbuyer
     */
    public List<SaleEntity> getSales(Long buyersId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los sales asociados a el comprador con id = {0}", buyersId);
        return buyerPersistence.find(buyersId).getSales();
    }

    /**
     * Retorna un  sale asociado a un a buyer
     *
     * @param buyersId El id de el comprador a buscar.
     * @param salesId El id delobraa buscar
     * @return elobraencontrado dentro de elbuyer.
     * @throws BusinessLogicException Si elobrano se encuentra en la
     * buyer
     */
    public SaleEntity getSale(Long buyersId, Long salesId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar elobracon id = {0} de el comprador con id = " + buyersId, salesId);
        List<SaleEntity> sales = buyerPersistence.find(buyersId).getSales();
        SaleEntity saleEntity = salePersistence.find(salesId);
        int index = sales.indexOf(saleEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar elobracon id = {0} de el comprador con id = " + buyersId, salesId);
        if (index >= 0) {
            return sales.get(index);
        }
        throw new BusinessLogicException("elobrano está asociado a elbuyer");
    }

    /**
     * Remplazar sales de un a buyer
     *
     * @param sales Lista de sales que serán los de elbuyer.
     * @param buyersId El id de el comprador que se quiere actualizar.
     * @return ellista de sales actualizada.
     */
    public List<SaleEntity> replaceSales(Long buyersId, List<SaleEntity> sales) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el comprador con id = {0}", buyersId);
        BuyerEntity buyerEntity = buyerPersistence.find(buyersId);
        List<SaleEntity> saleList = salePersistence.findAll();
        for (SaleEntity sale : saleList) {
            if (sales.contains(sale)) {
                sale.setBuyer(buyerEntity);
            } else if (sale.getBuyer() != null && sale.getBuyer().equals(buyerEntity)) {
                sale.setBuyer(null);
            }
        }
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el comprador con id = {0}", buyersId);
        return sales;
    }

    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.ejb;
import java.util.List;
import co.edu.uniandes.csw.galeriaarte.persistence.BuyerPersistence;
import co.edu.uniandes.csw.galeriaarte.persistence.PaintworkPersistence;
import co.edu.uniandes.csw.galeriaarte.entities.BuyerEntity;
import co.edu.uniandes.csw.galeriaarte.entities.PaintworkEntity;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException;
/**
 *
 * @author estudiante
 */
public class BuyerPaintworksLogic
{
    
     private static final Logger LOGGER = Logger.getLogger(BuyerPaintworksLogic.class.getName());

    @Inject
    private PaintworkPersistence paintworkPersistence;

    @Inject
    private BuyerPersistence buyerPersistence;

    /**
     * Agregar un  paintwork a elbuyer
     *
     * @param paintworksId El id paintwork a guardar
     * @param buyersId El id de el comprador en elcual se va a guardar el
     * paintwork.
     * @return elobracreado.
     */
    public PaintworkEntity addPaintwork(Long paintworksId, Long buyersId) {
        LOGGER.log(Level.INFO, "Inicia proceso de agregarle un  paintwork a el comprador con id = {0}", buyersId);
        BuyerEntity buyerEntity = buyerPersistence.find(buyersId);
        PaintworkEntity paintworkEntity = paintworkPersistence.find(paintworksId);
        paintworkEntity.setBuyer(buyerEntity);
        LOGGER.log(Level.INFO, "Termina proceso de agregarle un  paintwork a el comprador con id = {0}", buyersId);
        return paintworkEntity;
    }

    /**
     * Retorna todos los paintworks asociados a un a buyer
     *
     * @param buyersId El ID de el comprador buscada
     * @return ellista de paintworks de elbuyer
     */
    public List<PaintworkEntity> getPaintworks(Long buyersId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los paintworks asociados a el comprador con id = {0}", buyersId);
        return buyerPersistence.find(buyersId).getPaintworks();
    }

    /**
     * Retorna un  paintwork asociado a un a buyer
     *
     * @param buyersId El id de el comprador a buscar.
     * @param paintworksId El id delobraa buscar
     * @return elobraencontrado dentro de elbuyer.
     * @throws BusinessLogicException Si elobrano se encuentra en la
     * buyer
     */
    public PaintworkEntity getPaintwork(Long buyersId, Long paintworksId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar elobracon id = {0} de el comprador con id = " + buyersId, paintworksId);
        List<PaintworkEntity> paintworks = buyerPersistence.find(buyersId).getPaintworks();
        PaintworkEntity paintworkEntity = paintworkPersistence.find(paintworksId);
        int index = paintworks.indexOf(paintworkEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar elobracon id = {0} de el comprador con id = " + buyersId, paintworksId);
        if (index >= 0) {
            return paintworks.get(index);
        }
        throw new BusinessLogicException("elobrano está asociado a elbuyer");
    }

    /**
     * Remplazar paintworks de un a buyer
     *
     * @param paintworks Lista de paintworks que serán los de elbuyer.
     * @param buyersId El id de el comprador que se quiere actualizar.
     * @return ellista de paintworks actualizada.
     */
    public List<PaintworkEntity> replacePaintworks(Long buyersId, List<PaintworkEntity> paintworks) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el comprador con id = {0}", buyersId);
        BuyerEntity buyerEntity = buyerPersistence.find(buyersId);
        List<PaintworkEntity> paintworkList = paintworkPersistence.findAll();
        for (PaintworkEntity paintwork : paintworkList) {
            if (paintworks.contains(paintwork)) {
                paintwork.setBuyer(buyerEntity);
            } else if (paintwork.getBuyer() != null && paintwork.getBuyer().equals(buyerEntity)) {
                paintwork.setBuyer(null);
            }
        }
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el comprador con id = {0}", buyersId);
        return paintworks;
    }
}

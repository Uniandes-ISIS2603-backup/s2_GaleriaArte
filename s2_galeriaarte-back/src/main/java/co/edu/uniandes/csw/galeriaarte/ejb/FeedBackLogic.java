/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package co.edu.uniandes.csw.galeriaarte.ejb;

import co.edu.uniandes.csw.galeriaarte.entities.FeedBackEntity;
import co.edu.uniandes.csw.galeriaarte.entities.PaintworkEntity;
import co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.galeriaarte.persistence.FeedBackPersistence;
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
public class FeedBackLogic
{
    
    
    private static final Logger LOGGER = Logger.getLogger(FeedBackLogic.class.getName());
    
    @Inject
    private FeedBackPersistence persistence;
    
    @Inject
    private PaintworkPersistence paintworkPersistence;
    
    /**
     * Se encarga de crear un feedBack en la base de datos.
     *
     * @param feedBackEntity Objeto de feedBackEntity con los datos nuevos
     * @param paintworksId id del paintwork el cual sera padre del nuevo feedBack.
     * @return Objeto de feedBackEntity con los datos nuevos y su ID.
     * @throws BusinessLogicException si paintworksId no es el mismo que tiene el
     * entity.
     *
     */
    public FeedBackEntity createFeedBack(Long paintworksId, FeedBackEntity feedBackEntity) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia proceso de crear feedBack");
        PaintworkEntity paintwork = paintworkPersistence.find(paintworksId);
        feedBackEntity.setObra(paintwork);
        LOGGER.log(Level.INFO, "Termina proceso de creación del feedBack");
        return persistence.create(feedBackEntity);
    }
    
    /**
     * Obtiene la lista de los registros de feedBack que pertenecen a un paintwork.
     *
     * @param paintworksId id del paintwork el cual es padre de los feedBacks.
     * @return Colección de objetos de feedBackEntity.
     */
    public List<FeedBackEntity> getFeedBacks(Long paintworksId)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los feedBacks asociados al paintwork con id = {0}", paintworksId);
        PaintworkEntity paintworkEntity = paintworkPersistence.find(paintworksId);
        LOGGER.log(Level.INFO, "Termina proceso de consultar los feedBacks asociados al paintwork con id = {0}", paintworksId);
        return paintworkEntity.getFeedback();
    }
    
    /**
     * Obtiene los datos de una instancia de feedBack a partir de su ID. La
     * existencia del elemento padre paintwork se debe garantizar.
     *
     * @param paintworksId El id del paintwork buscado
     * @param feedBacksId Identificador de la Reseña a consultar
     * @return Instancia de feedBackEntity con los datos del feedBack consultado.
     *
     */
    public FeedBackEntity getFeedBack(Long paintworksId, Long feedBacksId)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el feedBack con id {0} del paintwork con id  {1}", new Object[]{feedBacksId, paintworksId});
        return persistence.find(paintworksId, feedBacksId);
    }
    
    /**
     * Actualiza la información de una instancia de feedBack.
     *
     * @param feedBackEntity Instancia de feedBackEntity con los nuevos datos.
     * @param paintworksId id del paintwork el cual sera padre del feedBack actualizado.
     * @return Instancia de feedBackEntity con los datos actualizados.
     *
     */
    public FeedBackEntity updateFeedBack(Long paintworksId, FeedBackEntity feedBackEntity)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el feedBack con id {0} del paintwork con id  {1}", new Object[]{feedBackEntity.getId(), paintworksId});
        PaintworkEntity paintworkEntity = paintworkPersistence.find(paintworksId);
        feedBackEntity.setObra(paintworkEntity);
        persistence.update(feedBackEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el el feedBack con id {0} del paintwork con id  {1}", new Object[]{feedBackEntity.getId(), paintworksId});
        return feedBackEntity;
    }
    
    /**
     * Elimina una instancia de feedBack de la base de datos.
     *
     * @param feedBacksId Identificador de la instancia a eliminar.
     * @param paintworksId id del paintwork el cual es padre del feedBack.
     * @throws BusinessLogicException Si la reseña no esta asociada al paintwork.
     *
     */
    public void deleteFeedBack(Long paintworksId, Long feedBacksId) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el  el feedBack con id {0} del paintwork con id  {1}", new Object[]{feedBacksId, paintworksId});
        FeedBackEntity old = getFeedBack(paintworksId, feedBacksId);
        if (old == null) {
            throw new BusinessLogicException("El feedBack con id = " + feedBacksId + " no esta asociado a el paintwork con id = " + paintworksId);
        }
        persistence.delete(old.getId());
        LOGGER.log(Level.INFO, "Termina proceso de borrar el feedBack con id {0} del paintwork con id  {1}", new Object[]{feedBacksId, paintworksId});
    }
}

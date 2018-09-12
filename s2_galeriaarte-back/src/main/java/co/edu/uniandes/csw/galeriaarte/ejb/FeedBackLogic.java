/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.ejb;

import co.edu.uniandes.csw.galeriaarte.entities.FeedBackEntity;
import co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.galeriaarte.persistence.FeedBackPersistence;
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

    /**
     * Se encarga de crear un FeedBack en la base de datos.
     *
     * @param feedEntity Objeto de FeedBackEntity con los datos nuevos
     * @return Objeto de FeedBackEntity con los datos nuevos y su ID.
     */
    public FeedBackEntity createFeedBack(FeedBackEntity feedEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la calificacion");
        FeedBackEntity newFeedEntity = persistence.create(feedEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación de la calificacion");
        return newFeedEntity;
    }
    public List<FeedBackEntity> getFeedBacks() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos las calificaciones");
        List<FeedBackEntity> lista = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos las calificaciones");
        return lista;
    }
    
    public FeedBackEntity updateFeedBack(Long fdId, FeedBackEntity feedEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la calificacion con id = {0}", fdId);
        FeedBackEntity newAuthorEntity = persistence.update(feedEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la calificacion con id = {0}", fdId);
        return newAuthorEntity;
    }
      /**
     * Borrar una calificacion
     *
     * @param feedBackId: id de la obra a borrar
     */
    public void deleteFeedBack(Long feedBackId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la calificacion con id = {0}", feedBackId);
        // Note que, por medio de la inyección de dependencias se llama al método "delete(id)" que se encuentra en la persistencia.
        persistence.delete(feedBackId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la calificacion con id = {0}", feedBackId);
    }
    
   
}

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
     * @throws co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException
     */
    public FeedBackEntity createFeedBack(FeedBackEntity feedEntity) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la calificacion");
        if (feedEntity.getComentario().length()>200)
        {
            throw new BusinessLogicException("El numero de caracteres del comentario execede 200  \"" + feedEntity.getComentario() + "\"");
        }
        FeedBackEntity newFeedEntity = persistence.create(feedEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación de la calificacion");
        return newFeedEntity;
    }
    
    public List<FeedBackEntity> getFeedBacks() 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos las calificaciones");
        List<FeedBackEntity> lista = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos las calificaciones");
        return lista;
    }
    public FeedBackEntity getFeedBack(Long feedId)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la calificacion con id = {0}", feedId);
        // Note que, por medio de la inyección de dependencias se llama al método "find(id)" que se encuentra en la persistencia.
        FeedBackEntity feedEntity = persistence.find(feedId);
        if (feedEntity == null)
        {
            LOGGER.log(Level.SEVERE, "La calificacion con el id = {0} no existe", feedId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar la calificacion con id = {0}", feedId);
        return feedEntity;
    }
    
    public FeedBackEntity updateFeedBack(Long fdId, FeedBackEntity feedEntity) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la calificacion con id = {0}", fdId);
        if(feedEntity.getComentario().length()>200)
        {
            throw new BusinessLogicException("El numero de caracteres del comentario excede 200  \"" + feedEntity.getComentario() + "\"");
        }
        FeedBackEntity newAuthorEntity = persistence.update(feedEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la calificacion con id = {0}", fdId);
        return newAuthorEntity;
    }
      /**
     * Borrar una calificacion
     *
     * @param feedBackId: id de la obra a borrar
     */
    public void deleteFeedBack(Long feedBackId) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la calificacion con id = {0}", feedBackId);
        // Note que, por medio de la inyección de dependencias se llama al método "delete(id)" que se encuentra en la persistencia.
        if(persistence.find(feedBackId) != null )
        {
        persistence.delete(feedBackId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de borrar la calificacion con id = {0}", feedBackId);
    }
    
   
}

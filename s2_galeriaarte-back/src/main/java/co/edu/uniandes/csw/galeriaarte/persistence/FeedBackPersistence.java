/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package co.edu.uniandes.csw.galeriaarte.persistence;

import co.edu.uniandes.csw.galeriaarte.entities.FeedBackEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author ja.penat
 */
@Stateless
public class FeedBackPersistence
{
    
    private static final Logger LOGGER = Logger.getLogger(FeedBackPersistence.class.getName());
    
    @PersistenceContext(unitName = "InterArtPU")
    protected EntityManager em;
    
    /**
     * Crear un comentario
     *
     * Crea un nuevo comentario con la información recibida en la entidad.
     *
     * @param feedBackEntity La entidad que representa el nuevo comentario
     * @return La entidad creada
     */
    public FeedBackEntity create(FeedBackEntity feedBackEntity)
    {
        LOGGER.log(Level.INFO, "Creando un feedback nuevo");
        em.persist(feedBackEntity);
        LOGGER.log(Level.INFO, "feedback creado");
        return feedBackEntity;
    }
    
    /**
     * Actualizar un comentario
     *
     * Actualiza la entidad que recibe en la base de datos
     *
     * @param feedBackEntity La entidad actualizada que se desea guardar
     * @return La entidad resultante luego de la acutalización
     */
    public FeedBackEntity update(FeedBackEntity feedBackEntity)
    {
        LOGGER.log(Level.INFO, "Actualizando feedback con id = {0}", feedBackEntity.getId());
        return em.merge(feedBackEntity);
    }
    
    /**
     * Eliminar un comentario
     *
     * Elimina el comentario asociado al ID que recibe
     *
     * @param feedBackId El ID del comentario que se desea borrar
     */
    public void delete(Long feedBackId)
    {
        LOGGER.log(Level.INFO, "Borrando feedback con id = {0}", feedBackId);
        FeedBackEntity feedBackEntity = em.find(FeedBackEntity.class, feedBackId);
        em.remove(feedBackEntity);
        LOGGER.log(Level.INFO, "Saliendo de borrar El feedback con id = {0}", feedBackId);
    }
    
    /**
     * Buscar un comentario
     *
     * Busca si hay alguna comentario asociado a una obra y con un ID específico
     *
     * @param obraId El ID de la obra con respecto al cual se busca
     * @param feedbackId El ID del comentario buscado
     * @return El comentario encontrado o null. Nota: Si existe uno o más comentarios
     * devuelve siempre la primera que encuentra
     */
    public FeedBackEntity find(Long obraId, Long feedbackId)
    {
        LOGGER.log(Level.INFO, "Consultando el comentario con id {0} de la obra con id = {1}", new Object[]{feedbackId, obraId});
        TypedQuery<FeedBackEntity> q = em.createQuery("select p from FeedBackEntity p where (p.obra.id = :obraId) and (p.id = :feedbackId)", FeedBackEntity.class);
        q.setParameter("obraId", obraId);
        q.setParameter("feedbackId", feedbackId);
        List<FeedBackEntity> results = q.getResultList();
        FeedBackEntity comentario = null;
        if (results == null || results.isEmpty())
        {
          return comentario;  
        }
        else if (results.size() >= 1)
        {
            comentario = results.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo del comentario con id {0} de la obra con id = {1}", new Object[]{feedbackId, obraId});
        return comentario;
    }
    
}

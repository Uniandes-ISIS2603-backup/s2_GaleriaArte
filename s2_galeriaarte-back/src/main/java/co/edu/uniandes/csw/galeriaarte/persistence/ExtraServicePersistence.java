/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package co.edu.uniandes.csw.galeriaarte.persistence;


import co.edu.uniandes.csw.galeriaarte.entities.ExtraServiceEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Clase que maneja la persistencia para ExtraService. Se conecta a través Entity
 * Manager de javax.persistance con la base de datos SQL.
 *
 * @author estudiante
 */
@Stateless
public class ExtraServicePersistence
{
    
    private static final Logger LOGGER = Logger.getLogger(ExtraServicePersistence.class.getName());
    
    @PersistenceContext(unitName = "InterArtPU")
    protected EntityManager em;
    
    /**
     * Método para persisitir la entidad en la base de datos.
     * @param extraServiceEntity objeto ExtraService que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public ExtraServiceEntity create(ExtraServiceEntity extraServiceEntity)
    {
        LOGGER.log(Level.INFO, "Creando un nuevo servicio extra");
        em.persist(extraServiceEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear un nuevo servicio extra");
        return extraServiceEntity;
    }
    
    /**
     * Devuelve todos los servicios extras  de la base de datos.
     * @return una lista con todos los servicios extras que encuentre en la base de datos
     */
    public List<ExtraServiceEntity> findAll()
    {
        LOGGER.log(Level.INFO, "Consultando todos los servicios extra");
        TypedQuery query = em.createQuery("select u from ExtraServiceEntity u", ExtraServiceEntity.class);
        return query.getResultList();
    }
    
    /**
     * Busca si hay algun servicio extra  con el id que se envía de argumento
     * @param extraServiceId: id correspondiente al servicio de pago  buscado.
     * @return un servicio extra.
     */
    public ExtraServiceEntity find(Long extraServiceId)
    {
        LOGGER.log(Level.INFO, "Consultando el servicio extra  con id={0}", extraServiceId);
        return em.find(ExtraServiceEntity.class, extraServiceId);
    }
    
    /**
     * Actualiza un servicio extra.
     *
     * @param extraServiceEntity: el servicio extra   que viene con los nuevos cambios.
     * Por ejemplo el nombre pudo cambiar. En ese caso, se haria uso del método
     * update.
     * @return el servicio extra   con los cambios aplicados.
     */
    public ExtraServiceEntity update(ExtraServiceEntity extraServiceEntity)
    {
        LOGGER.log(Level.INFO, "Actualizando el servicio extra con id = {0}", extraServiceEntity.getId());
        LOGGER.log(Level.INFO, "Saliendo de actualizar el servicio extral con id = {0}", extraServiceEntity.getId());
        return em.merge(extraServiceEntity);
    }
    
    /**
     * Borra un servicio extra   de la base de datos recibiendo como argumento el id
     * del servicio extra.
     *
     * @param extraServiceId: id correspondiente al servicio extra  a  borrar.
     */
    public void delete(Long extraServiceId)
    {
        LOGGER.log(Level.INFO, "Borrando el servicio extra  con id = {0}", extraServiceId);
        ExtraServiceEntity entity = em.find(ExtraServiceEntity.class, extraServiceId);
        em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar el servicio extra con id = {0}", extraServiceId);
    }
    
    /**
     * Busca si hay alguna servicio extra con el nombre que se envía de argumento
     *
     * @param name: Nombre del servicio extra que se está buscando
     * @return null si no existe ningun servicio extra con el nombre del argumento.
     * Si existe algun servicio extra   devuelve el primera.
     */
    public ExtraServiceEntity findByName(String name)
    {
        LOGGER.log(Level.INFO, "Consultando el servicio extra por nombre ", name);
        TypedQuery query = em.createQuery("Select e From ExtraServiceEntity e where e.name = :name", ExtraServiceEntity.class);
        query = query.setParameter("name", name);
        List<ExtraServiceEntity> sameName = query.getResultList();
        ExtraServiceEntity result;
        if (sameName == null)
        {
            result = null;
        }
        else if (sameName.isEmpty())
        {
            result = null;
        } else
        {
            result = sameName.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar el servicio extra por nombre ", name);
        return result;
    }
}

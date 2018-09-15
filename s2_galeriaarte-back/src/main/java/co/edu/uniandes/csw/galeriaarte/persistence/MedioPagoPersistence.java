/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.persistence;

import co.edu.uniandes.csw.galeriaarte.entities.MedioPagoEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Clase que maneja la persistencia para MedioPago. Se conecta a través Entity
 * Manager de javax.persistance con la base de datos SQL.
 *
 * @author ja.penat
 */
@Stateless
public class MedioPagoPersistence 
{
    
    private static final Logger LOGGER = Logger.getLogger(MedioPagoPersistence.class.getName());

    @PersistenceContext(unitName = "InterArtPU")
    protected EntityManager em;

    /**
     * Método para persisitir la entidad en la base de datos.
     * @param medioPagoEntity objeto medioPago que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public MedioPagoEntity create(MedioPagoEntity medioPagoEntity)
    {
        LOGGER.log(Level.INFO, "Creando un nuevo medio de pago nueva");
        em.persist(medioPagoEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear un medio de pago nueva");
        return medioPagoEntity;
    }

    /**
     * Devuelve todas los medios de pago  de la base de datos.
     * @return una lista con todas los medios de pago que encuentre en la base de
     */
    public List<MedioPagoEntity> findAll() 
    {
        LOGGER.log(Level.INFO, "Consultando todos los medios de pago");
        TypedQuery query = em.createQuery("select u from MedioPagoEntity u", MedioPagoEntity.class);
        return query.getResultList();
    }

    /**
     * Busca si hay algun medio de pago  con el id que se envía de argumento
     * @param medioPagoId: id correspondiente al medio de pago  buscado.
     * @return un medio de pago.
     */
    public MedioPagoEntity find(Long medioPagoId) 
    {
        LOGGER.log(Level.INFO, "Consultando medio de pago  con id={0}", medioPagoId);
        return em.find(MedioPagoEntity.class, medioPagoId);
    }

    /**
     * Actualiza un medio de pago.
     *
     * @param medioPagoEntity: el medio de pago  que viene con los nuevos cambios.
     * Por ejemplo el numero  pudo cambiar. En ese caso, se haria uso del método
     * update.
     * @return el medio de pago  con los cambios aplicados.
     */
    public MedioPagoEntity update(MedioPagoEntity medioPagoEntity)
    {
        LOGGER.log(Level.INFO, "Actualizando medio de pago con id = {0}", medioPagoEntity.getId());
        LOGGER.log(Level.INFO, "Saliendo de actualizar el medio de pago con id = {0}", medioPagoEntity.getId());
        return em.merge(medioPagoEntity);
    }

    /**
     * Borra un medio de pago  de la base de datos recibiendo como argumento el id
     * del medio de pago.
     *
     * @param medioPagoId: id correspondiente al medio de pago a  borrar.
     */
    public void delete(Long medioPagoId) 
    {
        LOGGER.log(Level.INFO, "Borrando el medio de pago  con id = {0}", medioPagoId);
        MedioPagoEntity entity = em.find(MedioPagoEntity.class, medioPagoId);
        em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar el medio de pago con id = {0}", medioPagoId);
    }

    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.ejb;


import co.edu.uniandes.csw.galeriaarte.entities.KindEntity;
import co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException;

import co.edu.uniandes.csw.galeriaarte.persistence.KindPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author LauraMAnrique
 */
@Stateless
public class KindLogic {
       private static final Logger LOGGER = Logger.getLogger(KindLogic.class.getName());
 
      @Inject
    private KindPersistence persistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

      /**
     * Crea un kind en la persistencia.
     *
     * @param kindEntity El kind que representa el kind a
     * persistir.
     * @return La entiddad del kind luego de persistirlo.
     * @throws BusinessLogicException Si el kind a persistir ya existe.
     */
    public KindEntity createKind(KindEntity kindEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del kind");
        // Verifica la regla de negocio que dice que no puede haber dos kinds con el mismo nombre
        if (persistence.findByName(kindEntity.getName()) != null) {
            throw new BusinessLogicException("Ya existe un kind con el nombre \"" + kindEntity.getName() + "\"");
        }
        // Invoca la persistencia para crear el kind
        persistence.create(kindEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación del kind");
        return kindEntity;
    }

     /**
     * Obtener todos los kinds existentes en la base de datos.
     *
     * @return una lista de obras.
     */
    public List<KindEntity> getKInds() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los kinds");
        // Note que, por medio de la inyección de dependencias se llama al método "findAll()" que se encuentra en la persistencia.
        List<KindEntity> kinds = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los kinds");
        return kinds;
    }
     /**
     * Obtener un kind por medio de su id.
     *
     * @param kindID: id del kind para ser buscado.
     * @return kind solicitado por medio de su id.
     */
    public KindEntity getKindV(Long kindID) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el kind con id = {0}", kindID);
        // Note que, por medio de la inyección de dependencias se llama al método "find(id)" que se encuentra en la persistencia.
        KindEntity kindEntity = persistence.find(kindID);
        if (kindEntity == null) {
            LOGGER.log(Level.SEVERE, "La obra con el id = {0} no existe", kindID);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar la obra con id = {0}", kindID);
        return kindEntity;
    }

    /**
     * Actualizar un kind.
     *
     * @param kindId: id del kind para buscarlo en la base de
     * datos.
     * @param KindEntity: kind con los cambios para ser actualizado,
     * por ejemplo el nombre.
     * @return kind con los cambios actualizados en la base de datos.
     */
    public KindEntity updateKind(Long kindId, KindEntity kindEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el kind con id = {0}", kindId);
        // Note que, por medio de la inyección de dependencias se llama al método "update(entity)" que se encuentra en la persistencia.
        KindEntity newEntity = persistence.update(kindEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el kind con id = {0}", kindEntity.getId());
        return newEntity;
    }

    /**
     * Borrar un kind
     *
     * @param kindId: id del kind a borrar
     */
    public void deletePaintWork(Long kindId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar  el kind con id = {0}", kindId);
        // Note que, por medio de la inyección de dependencias se llama al método "delete(id)" que se encuentra en la persistencia.
        persistence.delete(kindId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el kind con id = {0}", kindId);
    }
}

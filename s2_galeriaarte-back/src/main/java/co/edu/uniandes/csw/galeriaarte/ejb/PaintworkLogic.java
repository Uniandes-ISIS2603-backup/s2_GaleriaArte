/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.ejb;

import co.edu.uniandes.csw.galeriaarte.entities.PaintworkEntity;
import co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.galeriaarte.persistence.PaintworkPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author jf.copete
 */


@Stateless
public class PaintworkLogic {
    private static final Logger LOGGER = Logger.getLogger(PaintworkLogic.class.getName());

    @Inject
    private PaintworkPersistence persistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    /**
     * Crea una obra en la persistencia.
     *
     * @param paintworkEntity La obra que representa la editorial a
     * persistir.
     * @return La entiddad de la obra luego de persistirla.
     * @throws BusinessLogicException Si la obra a persistir ya existe.
     */
    public PaintworkEntity createPaintWork(PaintworkEntity paintworkEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la obra");
        // Verifica la regla de negocio que dice que no puede haber dos obras con el mismo nombre
        if (persistence.findByName(paintworkEntity.getName()) != null) {
            throw new BusinessLogicException("Ya existe una Editorial con el nombre \"" + paintworkEntity.getName() + "\"");
        }
        // Invoca la persistencia para crear la editorial
        persistence.create(paintworkEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación de la obra");
        return paintworkEntity;
    }

    /**
     * Obtener todas las obras existentes en la base de datos.
     *
     * @return una lista de obras.
     */
    public List<PaintworkEntity> getPaintworks() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las obras");
        // Note que, por medio de la inyección de dependencias se llama al método "findAll()" que se encuentra en la persistencia.
        List<PaintworkEntity> paintworks = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las obras");
        return paintworks;
    }

    /**
     * Obtener una editorial por medio de su id.
     *
     * @param paintworkId: id de la obra para ser buscada.
     * @return la editorial solicitada por medio de su id.
     */
    public PaintworkEntity getPaintWork(Long paintworkId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la obra con id = {0}", paintworkId);
        // Note que, por medio de la inyección de dependencias se llama al método "find(id)" que se encuentra en la persistencia.
        PaintworkEntity paintworkEntity = persistence.find(paintworkId);
        if (paintworkEntity == null) {
            LOGGER.log(Level.SEVERE, "La obra con el id = {0} no existe", paintworkId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar la obra con id = {0}", paintworkId);
        return paintworkEntity;
    }

    /**
     * Actualizar una obra.
     *
     * @param paintworkId: id de la obra para buscarla en la base de
     * datos.
     * @param paintworkEntity: obra con los cambios para ser actualizada,
     * por ejemplo el nombre.
     * @return la obra con los cambios actualizados en la base de datos.
     */
    public PaintworkEntity updatePaintWork(Long paintworkId, PaintworkEntity paintworkEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la obra con id = {0}", paintworkId);
        // Note que, por medio de la inyección de dependencias se llama al método "update(entity)" que se encuentra en la persistencia.
        PaintworkEntity newEntity = persistence.update(paintworkEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la obra con id = {0}", paintworkEntity.getId());
        return newEntity;
    }

    /**
     * Borrar un obra
     *
     * @param paintworkId: id de la obra a borrar
     */
    public void deletePaintWork(Long paintworkId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la obra con id = {0}", paintworkId);
        // Note que, por medio de la inyección de dependencias se llama al método "delete(id)" que se encuentra en la persistencia.
        persistence.delete(paintworkId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la obra con id = {0}", paintworkId);
    }
}

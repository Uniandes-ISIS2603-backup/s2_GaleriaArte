/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package co.edu.uniandes.csw.galeriaarte.ejb;

import co.edu.uniandes.csw.galeriaarte.entities.CVEntity;
import co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.galeriaarte.persistence.CVPersistence;
import java.util.List;
import java.util.logging.Level;

import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author LauraManrique y Ja.penat
 */
@Stateless
public class CVLogic
{
    private static final Logger LOGGER = Logger.getLogger(CVLogic.class.getName());
    
    @Inject
    private CVPersistence persistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.
    
    /**
     * Crea un cv en la persistencia.
     *
     * @param cvEntity El cv que representa el cv a
     * persistir.
     * @return La entiddad del cv luego de persistirlo.
     * @throws BusinessLogicException Si el cv a persistir ya existe.
     */
    public CVEntity createCV(CVEntity cvEntity) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del cv");
        
        if ( cvEntity.getEducation() != null && cvEntity.getFechaNacimiento() != null && cvEntity.getInformacionAdicional() != null   )
        {
            persistence.create(cvEntity);
            LOGGER.log(Level.INFO, "Termina proceso de creación del cv");
            return cvEntity;
        }
        else
        {
            LOGGER.log(Level.INFO, "No se termino la creacion porque los datos no eran validos");
            throw new BusinessLogicException("No pueden haber campos nulos\"" );
        }
    }
    
    /**
     * Obtener todos los cvs existentes en la base de datos.
     *
     * @return una lista de cvs.
     */
    public List<CVEntity> getCVs() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los cvs");
        // Note que, por medio de la inyección de dependencias se llama al método "findAll()" que se encuentra en la persistencia.
        List<CVEntity> cvs = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los cvs");
        return cvs;
    }
    /**
     * Obtener un cv por medio de su id.
     *
     * @param cvID: id del cv para ser buscado.
     * @return cv solicitado por medio de su id.
     */
    public CVEntity getCV(Long cvID) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el cv con id = {0}", cvID);
        // Note que, por medio de la inyección de dependencias se llama al método "find(id)" que se encuentra en la persistencia.
        CVEntity cvEntity = persistence.find(cvID);
        if (cvEntity == null) {
            LOGGER.log(Level.SEVERE, "La obra con el id = {0} no existe", cvID);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar la obra con id = {0}", cvID);
        return cvEntity;
    }
    
    /**
     * Actualizar un cv.
     *
     * @param cvId: id del cv para buscarlo en la base de
     * datos.
     * @param cvEntity: cv con los cambios para ser actualizado,
     * por ejemplo el nombre.
     * @return cv con los cambios actualizados en la base de datos.
     */
    public CVEntity updateCV(Long cvId, CVEntity cvEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el cv con id = {0}", cvId);
        // Note que, por medio de la inyección de dependencias se llama al método "update(entity)" que se encuentra en la persistencia.
        CVEntity newEntity = persistence.update(cvEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el cv con id = {0}", cvEntity.getId());
        return newEntity;
    }
    
    /**
     * Borrar un cv
     *
     * @param cvId: id del cv a borrar
     */
    public void deleteCV(Long cvId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar  el cv con id = {0}", cvId);
        // Note que, por medio de la inyección de dependencias se llama al método "delete(id)" que se encuentra en la persistencia.
        persistence.delete(cvId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el cv con id = {0}", cvId);
    }
}

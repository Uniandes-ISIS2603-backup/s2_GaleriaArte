/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package co.edu.uniandes.csw.galeriaarte.ejb;

import co.edu.uniandes.csw.galeriaarte.entities.ArtistEntity;
import co.edu.uniandes.csw.galeriaarte.entities.CVEntity;
import co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.galeriaarte.persistence.ArtistPersistence;
import co.edu.uniandes.csw.galeriaarte.persistence.CVPersistence;
import java.util.List;

import java.util.logging.Level;

import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para  
 * la entidad de CV.
 * @author LauraManrique y Ja.penat
 */
@Stateless
public class CVLogic
{
    private static final Logger LOGGER = Logger.getLogger(CVLogic.class.getName());
    
    @Inject
    private CVPersistence persistence;
    
    @Inject
    private ArtistPersistence artistPersistence ;
    
    /**
     * Crea un cv en la persistencia.
     *
     * @param artistId id del artista asociado a la hoja de vida
     * @param cvEntity El cv que representa el cv a
     * persistir.
     * @return La entiddad del cv luego de persistirlo.
     * @throws BusinessLogicException Si el cv a persistir ya existe.
     */
    public CVEntity createCV(Long artistId, CVEntity cvEntity) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del cv");
        cvEntity.setArtist(artistPersistence.find(artistId));
        if ( cvEntity.getEducation() != null &&  cvEntity.getInformacionAdicional() != null   )
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
     * @return una lista de cvs.
     */
    public List<CVEntity> getCVs() 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los cvs");
        // Note que, por medio de la inyección de dependencias se llama al método "findAll()" que se encuentra en la persistencia.
        List<CVEntity> cvs = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los cvs");
        return cvs;
    }
    /**
     * Obtener un cv por medio de su id.
     * @param artistId id del artista asociado a la hoja de vida
     * @param cvID: id del cv para ser buscado.
     * @return cv solicitado por medio del id de su artista.
     */
    public CVEntity getCV(Long artistId, Long cvID) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el cv con id = {0}", cvID);
        // Note que, por medio de la inyección de dependencias se llama al método "find(id)" que se encuentra en la persistencia.
        ArtistEntity artista = artistPersistence.find(artistId);
        LOGGER.log(Level.INFO, "Termina proceso de consultar la obra con id = {0}", cvID);
        return artista.getCV();
    }
    
    /**
     * Actualizar un cv.
     *
     * @param artistId: id del artista asociado a la hoja de vida.
     * @param cvEntity: cv con los cambios para ser actualizado,
     * por ejemplo el nombre.
     * @return cv con los cambios actualizados en la base de datos.
     */
    public CVEntity updateCV(Long artistId, CVEntity cvEntity)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el cv con id = {0}", cvEntity.getId());
        // Note que, por medio de la inyección de dependencias se llama al método "update(entity)" que se encuentra en la persistencia.
        ArtistEntity artista = artistPersistence.find(artistId);
        cvEntity.setArtist(artista);
        persistence.update(cvEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el cv con id = {0}", cvEntity.getId());
        return cvEntity;
    }
    
 /**
     * Elimina una instancia de CV de la base de datos.
     *
     * @param cvId Identificador de la instancia a eliminar.
     * @param artistId id del artista el cual es padre del CV.
     * @throws BusinessLogicException Si la reseña no esta asociada al libro.
     *
     */
    public void deleteCV(Long artistId, Long cvId) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el CV con id = {0} del artista con id = " + cvId, artistId);
        CVEntity old = getCV(artistId, cvId);
        if (old == null) 
        {
            throw new BusinessLogicException("La hojo de vida  con id = " + cvId + " no esta asociado al artista con id = " + artistId);
        }
        persistence.delete(old.getId());
        LOGGER.log(Level.INFO, "Termina proceso de borrar la hoja de vida con id = {0} del artista con id = " + cvId, artistId);
    }
}

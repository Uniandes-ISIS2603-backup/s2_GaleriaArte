/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package co.edu.uniandes.csw.galeriaarte.ejb;

import co.edu.uniandes.csw.galeriaarte.entities.ArtistEntity;
import co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.galeriaarte.persistence.ArtistPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author a.barragan Anderson Barragan
 */
@Stateless
public class ArtistLogic
{
    
    private static final Logger LOGGER = Logger.getLogger(ArtistLogic.class.getName());
    
    @Inject
    private ArtistPersistence persistence;
    
    public ArtistEntity createArtist(ArtistEntity artistEntity) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del artista");
        if(artistEntity.getName() == null  || "".equals(artistEntity.getName()))
        {
            throw new BusinessLogicException("El nombre del artista no es valido \"" + artistEntity.getName() + "\"");
        }
        ArtistEntity newArtistEntity = persistence.create(artistEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación del artista");
        return newArtistEntity;
    }
    
    /**
     * Obtiene la lista de los registros de Artist.
     *
     * @return Colección de objetos de ArtistEntity.
     */
    public List<ArtistEntity> getArtists() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los artistas");
        List<ArtistEntity> artistas = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los artistas");
        return artistas;
    }
    
    /**
     * Obtiene los datos de una instancia de Artist a partir de su ID.
     *
     * @param artistsId Identificador de la instancia a consultar
     * @return Instancia de ArtistEntity con los datos del Artist consultado.
     */
    public ArtistEntity getArtist(Long artistsId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el artista con id = {0}", artistsId);
        ArtistEntity artistEntity = persistence.find(artistsId);
        if (artistEntity == null) {
            LOGGER.log(Level.SEVERE, "no existe la entidad con id = {0} no existe", artistsId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el artista con id = {0}", artistsId);
        return artistEntity;
    }
    
    /**
     * Actualiza la información de una instancia de Artist.
     * @param artistEntity Instancia de ArtistEntity con los nuevos datos.
     * @return Instancia de rtistEntity con los datos actualizados.
     * @throws co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException, si se cambia el nombre del artista 
     */
    public ArtistEntity updateArtist(ArtistEntity artistEntity) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el artista con id = {0}", artistEntity.getId());
        if(!(artistEntity.getName().equals(persistence.find(artistEntity.getId()).getName())))
        {
            throw new BusinessLogicException("No se puede cambiar el nombre del artista");
        }
        ArtistEntity newArtistEntity = persistence.update(artistEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el artista con id = {0}", artistEntity.getId());
        return newArtistEntity;
    }
    
    /**
     * Borrar un Artista
     *
     * @param artistId: id del artista a borrar
     */
    public void deleteArtist(Long artistId) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar al artista con id = {0}", artistId);
        // Note que, por medio de la inyección de dependencias se llama al método "delete(id)" que se encuentra en la persistencia.
        persistence.delete(artistId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar al artista con id = {0}", artistId);
    }
}


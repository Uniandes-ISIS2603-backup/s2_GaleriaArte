/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.ejb;

import co.edu.uniandes.csw.galeriaarte.entities.ArtistEntity;
import co.edu.uniandes.csw.galeriaarte.persistence.ArtistPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;

/**
 *
 * @author Anderson Barragan a.barragan
 */
public class ArtistLogic {
    
    private static final Logger LOGGER = Logger.getLogger(ArtistLogic.class.getName());

    @Inject
    private ArtistPersistence persistence;
      
    public ArtistEntity createArtist(ArtistEntity artistEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del artista");
        ArtistEntity newArtistEntity = persistence.create(artistEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación del artista");
        return newArtistEntity;
    }

    /**
     * Obtiene la lista de los registros de Artist.
     *
     * @return Colección de objetos de AuthorEntity.
     */
    public List<ArtistEntity> getArtists() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los autores");
        List<ArtistEntity> lista = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los autores");
        return lista;
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
            LOGGER.log(Level.SEVERE, "La editorial con el id = {0} no existe", artistsId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el artista con id = {0}", artistsId);
        return artistEntity;
    }

    /**
     * Actualiza la información de una instancia de Artist.
     *
     * @param artistId Identificador de la instancia a actualizar
     * @param artistEntity Instancia de ArtistEntity con los nuevos datos.
     * @return Instancia de rtistEntity con los datos actualizados.
     */
    public ArtistEntity updateAuthor(Long artistId, ArtistEntity artistEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el artista con id = {0}", artistId);
        ArtistEntity newArtistEntity = persistence.update(artistEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el artista con id = {0}", artistId);
        return newArtistEntity;
    }
}


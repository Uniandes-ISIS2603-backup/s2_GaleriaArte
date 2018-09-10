/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.ejb;

import co.edu.uniandes.csw.galeriaarte.entities.ArtistEntity;
import co.edu.uniandes.csw.galeriaarte.persistence.ArtistPersistence;
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
    
    public ArtistEntity createAuthor(ArtistEntity authorEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del artista");
        ArtistEntity newArtistEntity = persistence.create(authorEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación del artista");
        return newArtistEntity;
    }
    
    public ArtistEntity getArtist(Long id){
        return null;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.persistence;

import co.edu.uniandes.csw.galeriaarte.entities.ArtistEntity;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Anderson Barragan a.barragan
 */
public class ArtistPersistence {
    
    private static final Logger LOGGER = Logger.getLogger(ArtistPersistence.class.getName());

    @PersistenceContext(unitName = "InterArtPU")
    protected EntityManager em;
    
    public ArtistEntity create(ArtistEntity authorEntity) {
        LOGGER.log(Level.INFO, "Creando un artista nuevo");
        /* Note que hacemos uso de un método propio de EntityManager para persistir la author en la base de datos.
        Es similar a "INSERT INTO table_name (column1, column2, column3, ...) VALUES (value1, value2, value3, ...);" en SQL.
         */
        em.persist(authorEntity);
        LOGGER.log(Level.INFO, "Artista creado");
        return authorEntity;
    }
}

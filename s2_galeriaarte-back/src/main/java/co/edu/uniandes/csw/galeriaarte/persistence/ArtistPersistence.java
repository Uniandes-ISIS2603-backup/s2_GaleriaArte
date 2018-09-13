/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.persistence;

import co.edu.uniandes.csw.galeriaarte.entities.ArtistEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**


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

    public List<ArtistEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todos los artistas");
        // Se crea un query para buscar todas los artistas en la base de datos.
        TypedQuery query = em.createQuery("select u from ArtistEntity u", ArtistEntity.class);
        // Note que en el query se hace uso del método getResultList() que obtiene una lista de artistas.
        return query.getResultList();
    }
    public ArtistEntity find(Long artistsId) {
        LOGGER.log(Level.INFO, "Consultando el artista con id={0}", artistsId);
        /* Note que se hace uso del metodo "find" propio del EntityManager, el cual recibe como argumento 
        el tipo de la clase y el objeto que nos hara el filtro en la base de datos en este caso el "id"
        Suponga que es algo similar a "select * from ArtistEntity where id=id;" - "SELECT * FROM table_name WHERE condition;" en SQL.
         */
        return em.find(ArtistEntity.class, artistsId);
    }
    
    /**
     * Actualiza una artista.
     *
     * @param artistEntity: la author que viene con los nuevos cambios. Por
     * ejemplo el nombre pudo cambiar. En ese caso, se haria uso del método
     * update.
     * @return una author con los cambios aplicados.
     */
    public ArtistEntity update(ArtistEntity artistEntity) {
        LOGGER.log(Level.INFO, "Actualizando el artista con id={0}", artistEntity.getId());
        /* Note que hacemos uso de un método propio del EntityManager llamado merge() que recibe como argumento
        el artista con los cambios, esto es similar a 
        "UPDATE table_name SET column1 = value1, column2 = value2, ... WHERE condition;" en SQL.
         */
        return em.merge(artistEntity);
    }
}

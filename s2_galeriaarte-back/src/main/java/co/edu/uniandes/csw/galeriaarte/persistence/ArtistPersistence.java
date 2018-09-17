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
 *
 *
 * /**
 *
 * @author a.barragan Anderson Barragan
 */
public class ArtistPersistence {

    private static final Logger LOGGER = Logger.getLogger(ArtistPersistence.class.getName());

    @PersistenceContext(unitName = "InterArtPU")
    protected EntityManager em;

    public ArtistEntity create(ArtistEntity authorEntity) {
        LOGGER.log(Level.INFO, "Creando un artista nuevo");

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

        return em.find(ArtistEntity.class, artistsId);
    }

    /**
     * Actualiza una artista.
     *
     * @param artistEntity: el artista que viene con los nuevos cambios. Por
     * ejemplo el nombre pudo cambiar. En ese caso, se haria uso del método
     * update.
     * @return un artista con los cambios aplicados.
     */
    public ArtistEntity update(ArtistEntity artistEntity) {
        LOGGER.log(Level.INFO, "Actualizando el artista con id={0}", artistEntity.getId());

        return em.merge(artistEntity);
    }

    public void delete(Long artistId) {
        LOGGER.log(Level.INFO, "Borrando el artista con id={0}", artistId);
        ArtistEntity artistEntity = em.find(ArtistEntity.class, artistId);

        em.remove(artistEntity);
    }
}

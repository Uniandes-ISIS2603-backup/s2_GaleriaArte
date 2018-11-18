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
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Clase que maneja la persistencia para Artist. Se conecta a través del Entity
 * Manager de javax.persistance con la base de datos SQL.
 * @author a.barragan Anderson Barragan
 */
@Stateless
public class ArtistPersistence {

    private static final Logger LOGGER = Logger.getLogger(ArtistPersistence.class.getName());

    @PersistenceContext(unitName = "InterArtPU")
    protected EntityManager em;

    /**
     * Crea un artista en la base de datos
     *
     * @param artistEntity objeto artist que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public ArtistEntity create(ArtistEntity artistEntity) {
        LOGGER.log(Level.INFO, "Creando un artista nuevo");
        /* Note que hacemos uso de un método propio de EntityManager para persistir la artist en la base de datos.
        Es similar a "INSERT INTO table_name (column1, column2, column3, ...) VALUES (value1, value2, value3, ...);" en SQL.
         */
        em.persist(artistEntity);
        LOGGER.log(Level.INFO, "Artista creado");
        return artistEntity;
    }

    /**
     * Devuelve todas las artistos de la base de datos.
     *
     * @return una lista con todas los artistas que encuentre en la base de
     * datos, "select u from ArtistEntity u" es como un "select * from
     * ArtistEntity;" - "SELECT * FROM table_name" en SQL.
     */
    public List<ArtistEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todos los artistas");
        // Se crea un query para buscar todas las artistas en la base de datos.
        TypedQuery query = em.createQuery("select u from ArtistEntity u", ArtistEntity.class);
        // Note que en el query se hace uso del método getResultList() que obtiene una lista de artistas.
        return query.getResultList();
    }

    /**
     * Busca si hay alguna artist con el id que se envía de argumento
     *
     * @param artistsId: id correspondiente a la artist buscada.
     * @return un artist.
     */
    public ArtistEntity find(Long artistsId) {
        LOGGER.log(Level.INFO, "Consultando el artista con id={0}", artistsId);
        /* Note que se hace uso del metodo "find" propio del EntityManager, el cual recibe como argumento 
        el tipo de la clase y el objeto que nos hara el filtro en la base de datos en este caso el "id"
        Suponga que es algo similar a "select * from ArtistEntity where id=id;" - "SELECT * FROM table_name WHERE condition;" en SQL.
         */
        return em.find(ArtistEntity.class, artistsId);
    }

    /**
     * Actualiza una artist.
     *
     * @param artistEntity: la artist que viene con los nuevos cambios. Por
     * ejemplo el nombre pudo cambiar. En ese caso, se haria uso del método
     * update.
     * @return una artist con los cambios aplicados.
     */
    public ArtistEntity update(ArtistEntity artistEntity) {
        LOGGER.log(Level.INFO, "Actualizando el artist con id={0}", artistEntity.getId());
        /* Note que hacemos uso de un método propio del EntityManager llamado merge() que recibe como argumento
        la artist con los cambios, esto es similar a 
        "UPDATE table_name SET column1 = value1, column2 = value2, ... WHERE condition;" en SQL.
         */
        return em.merge(artistEntity);
    }

    /**
     * Borra una artist de la base de datos recibiendo como argumento el id de
     * la artist
     *
     * @param artistsId: id correspondiente a la artist a borrar.
     */
    public void delete(Long artistsId) {

        LOGGER.log(Level.INFO, "Borrando el artist con id={0}", artistsId);
        // Se hace uso de mismo método que esta explicado en public ArtistEntity find(Long id) para obtener la artist a borrar.
        ArtistEntity artistEntity = em.find(ArtistEntity.class, artistsId);
        /* Note que una vez obtenido el objeto desde la base de datos llamado "entity", volvemos hacer uso de un método propio del
        EntityManager para eliminar de la base de datos el objeto que encontramos y queremos borrar.
        Es similar a "delete from ArtistEntity where id=id;" - "DELETE FROM table_name WHERE condition;" en SQL.*/
        em.remove(artistEntity);
    }
}

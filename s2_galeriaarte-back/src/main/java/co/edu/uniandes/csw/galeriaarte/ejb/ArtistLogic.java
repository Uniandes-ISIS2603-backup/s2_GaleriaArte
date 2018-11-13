package co.edu.uniandes.csw.galeriaarte.ejb;

import co.edu.uniandes.csw.galeriaarte.entities.ArtistEntity;
import co.edu.uniandes.csw.galeriaarte.entities.PaintworkEntity;
import co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.galeriaarte.persistence.ArtistPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la entidad de
 * Artist.
 * @author Anderson Barragán
 */
@Stateless
public class ArtistLogic {

    private static final Logger LOGGER = Logger.getLogger(ArtistLogic.class.getName());

    @Inject
    private ArtistPersistence persistence;

    /**
     * Se encarga de crear un Artist en la base de datos.
     *
     * @param artistEntity Objeto de ArtistEntity con los datos nuevos
     * @return Objeto de ArtistEntity con los datos nuevos y su ID.
     */
    public ArtistEntity createArtist(ArtistEntity artistEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del artista");
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
        List<ArtistEntity> lista = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los artistas");
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
     * @param artistsId Identificador de la instancia a actualizar
     * @param artistEntity Instancia de ArtistEntity con los nuevos datos.
     * @return Instancia de ArtistEntity con los datos actualizados.
     */
    public ArtistEntity updateArtist(Long artistsId, ArtistEntity artistEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el artista con id = {0}", artistsId);
        ArtistEntity newArtistEntity = persistence.update(artistEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el artista con id = {0}", artistsId);
        return newArtistEntity;
    }

    /**
     * Elimina una instancia de Artist de la base de datos.
     *
     * @param artistsId Identificador de la instancia a eliminar.
     * @throws BusinessLogicException si el artista tiene libros asociados.
     */
    public void deleteArtist(Long artistsId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el artista con id = {0}", artistsId);
        List<PaintworkEntity> paintworks = getArtist(artistsId).getPaintworks();
        if (paintworks != null && !paintworks.isEmpty()) {
            throw new BusinessLogicException("No se puede borrar el artista con id = " + artistsId + " porque tiene obras asociados");
        }
        persistence.delete(artistsId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el artista con id = {0}", artistsId);
    }
}



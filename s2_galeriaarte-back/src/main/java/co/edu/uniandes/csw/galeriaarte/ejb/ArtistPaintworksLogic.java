package co.edu.uniandes.csw.galeriaarte.ejb;

import co.edu.uniandes.csw.galeriaarte.entities.ArtistEntity;
import co.edu.uniandes.csw.galeriaarte.entities.PaintworkEntity;
import co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.galeriaarte.persistence.ArtistPersistence;
import co.edu.uniandes.csw.galeriaarte.persistence.PaintworkPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que implementa la conexion con la persistencia para la relación entre
 * la entidad de Artist y Paintwork.
 *
 * @author Anderson Barragán
 */
@Stateless
public class ArtistPaintworksLogic {

    private static final Logger LOGGER = Logger.getLogger(ArtistPaintworksLogic.class.getName());

    @Inject
    private PaintworkPersistence paintworkPersistence;

    @Inject
    private ArtistPersistence artistPersistence;

    /**
     * Asocia un Paintwork existente a un Artist
     *
     * @param artistsId Identificador de la instancia de Artist
     * @param paintworksId Identificador de la instancia de Paintwork
     * @return Instancia de PaintworkEntity que fue asociada a Artist
     */
    public PaintworkEntity addPaintwork(Long artistsId, Long paintworksId) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociarle una obra al artista con id = {0}", artistsId);
        ArtistEntity artistEntity = artistPersistence.find(artistsId);
        PaintworkEntity paintworkEntity = paintworkPersistence.find(paintworksId);
       // paintworkEntity.getArtists().add(artistEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociarle una obra al artista con id = {0}", artistsId);
        return paintworkPersistence.find(paintworksId);
    }

    /**
     * Obtiene una colección de instancias de PaintworkEntity asociadas a una
     * instancia de Artist
     *
     * @param artistsId Identificador de la instancia de Artist
     * @return Colección de instancias de PaintworkEntity asociadas a la instancia de
     * Artist
     */
    public List<PaintworkEntity> getPaintworks(Long artistsId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las obras del artista con id = {0}", artistsId);
        return artistPersistence.find(artistsId).getPaintworks();
    }

    /**
     * Obtiene una instancia de PaintworkEntity asociada a una instancia de Artist
     *
     * @param artistsId Identificador de la instancia de Artist
     * @param paintworksId Identificador de la instancia de Paintwork
     * @return La entidadd de Obra del artista
     * @throws BusinessLogicException Si la obra no está asociado al artista
     */
    public PaintworkEntity getPaintwork(Long artistsId, Long paintworksId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la obra con id = {0} del artista con id = " + artistsId, paintworksId);
        List<PaintworkEntity> paintworks = artistPersistence.find(artistsId).getPaintworks();
        PaintworkEntity paintworkEntity = paintworkPersistence.find(paintworksId);
        int index = paintworks.indexOf(paintworkEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar la obra con id = {0} del artista con id = " + artistsId, paintworksId);
        if (index >= 0) {
            return paintworks.get(index);
        }
        throw new BusinessLogicException("El libro no está asociado al artista");
    }

    /**
     * Remplaza las instancias de Paintwork asociadas a una instancia de Artist
     *
     * @param artistId Identificador de la instancia de Artist
     * @param paintworks Colección de instancias de PaintworkEntity a asociar a instancia
     * de Artist
     * @return Nueva colección de PaintworkEntity asociada a la instancia de Artist
     */
    public List<PaintworkEntity> replacePaintworks(Long artistId, List<PaintworkEntity> paintworks) {
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar los libros asocidos al artist con id = {0}", artistId);
        ArtistEntity artistEntity = artistPersistence.find(artistId);
        List<PaintworkEntity> paintworkList = paintworkPersistence.findAll();
        for (PaintworkEntity paintwork : paintworkList) {
//           if (paintworks.contains(paintwork)) {
//                if (!paintwork.getArtists().contains(artistEntity)) {
//                    paintwork.getArtists().add(artistEntity);
//                }
//            } else {
//                paintwork.getArtists().remove(artistEntity);
//            }
       }
        artistEntity.setPaintworks(paintworks);
        LOGGER.log(Level.INFO, "Termina proceso de reemplazar los libros asocidos al artist con id = {0}", artistId);
        return artistEntity.getPaintworks();
    }

    /**
     * Desasocia un Paintwork existente de un Artist existente
     *
     * @param artistsId Identificador de la instancia de Artist
     * @param paintworksId Identificador de la instancia de Paintwork
     */
    public void removePaintwork(Long artistsId, Long paintworksId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un libro del artist con id = {0}", artistsId);
        ArtistEntity artistEntity = artistPersistence.find(artistsId);
        PaintworkEntity paintworkEntity = paintworkPersistence.find(paintworksId);
        //paintworkEntity.getArtists().remove(artistEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar un libro del artist con id = {0}", artistsId);
    }
}

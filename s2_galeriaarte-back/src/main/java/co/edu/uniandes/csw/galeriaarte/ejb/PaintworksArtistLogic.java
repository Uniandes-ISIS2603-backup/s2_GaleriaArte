package co.edu.uniandes.csw.galeriaarte.ejb;

import co.edu.uniandes.csw.galeriaarte.entities.ArtistEntity;
import co.edu.uniandes.csw.galeriaarte.entities.PaintworkEntity;
import co.edu.uniandes.csw.galeriaarte.persistence.ArtistPersistence;
import co.edu.uniandes.csw.galeriaarte.persistence.PaintworkPersistence;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la relación entre
 * la entidad de paintwork e artist.
 *
 * @author ja.penat
 */
@Stateless
public class PaintworksArtistLogic
{

    private static final Logger LOGGER = Logger.getLogger(PaintworksArtistLogic.class.getName());

    @Inject
    private PaintworkPersistence paintworkPersistence;

    @Inject
    private ArtistPersistence artistPersistence;

    /**
     * Remplazar la artist de un paintwork.
     *
     * @param paintworksId id del obra que se quiere actualizar.
     * @param artistsId El id de la artist que se será del obra.
     * @return el nuevo obra.
     */
    public PaintworkEntity replaceArtist(Long paintworksId, Long artistsId) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar obra con id = {0}", paintworksId);
        ArtistEntity artistEntity = artistPersistence.find(artistsId);
        PaintworkEntity paintworkEntity = paintworkPersistence.find(paintworksId);
        paintworkEntity.setArtist(artistEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar obra con id = {0}", paintworkEntity.getId());
        return paintworkEntity;
    }

    /**
     * Borrar un paintwork de una artist. Este metodo se utiliza para borrar la
     * relacion de un obra.
     *
     * @param paintworksId El obra que se desea borrar de la artist.
     */
    public void removeArtist(Long paintworksId) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la artist del obra con id = {0}", paintworksId);
        PaintworkEntity paintworkEntity = paintworkPersistence.find(paintworksId);
        ArtistEntity artistEntity = artistPersistence.find(paintworkEntity.getArtist().getId());
        paintworkEntity.setArtist(null);
        artistEntity.getPaintworks().remove(paintworkEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la artist del obra con id = {0}", paintworkEntity.getId());
    }
}

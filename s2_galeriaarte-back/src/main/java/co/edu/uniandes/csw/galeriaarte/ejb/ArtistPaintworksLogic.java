package co.edu.uniandes.csw.galeriaarte.ejb;

import co.edu.uniandes.csw.galeriaarte.entities.ArtistEntity;
import co.edu.uniandes.csw.galeriaarte.entities.PaintworkEntity;
import co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.galeriaarte.persistence.ArtistPersistence;
import co.edu.uniandes.csw.galeriaarte.persistence.PaintworkPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la relación entre
 * la entidad de artist y paintwork.
 *
 * @author ja.penat
 */
@Stateless
public class ArtistPaintworksLogic 
{

    private static final Logger LOGGER = Logger.getLogger(ArtistPaintworksLogic.class.getName());

    @Inject
    private PaintworkPersistence paintworkPersistence;

    @Inject
    private ArtistPersistence artistPersistence;

    /**
     * Agregar un paintwork al artist
     *
     * @param paintworksId El id de la obra a guardar
     * @param artistsId El id de la artist en la cual se va a guardar la obra
     * @return La obra creada.
     */
    public PaintworkEntity addPaintwork(Long paintworksId, Long artistsId)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de agregarle una obra al artist con id = {0}", artistsId);
        ArtistEntity artistEntity =  artistPersistence.find(artistsId);
        PaintworkEntity paintworkEntity  =  paintworkPersistence.find(paintworksId);
        paintworkEntity.setArtist(artistEntity);
        LOGGER.log(Level.INFO, "Termina proceso de agregarle una obra al artist con id = {0}", artistsId);
        return paintworkEntity;
    }

    /**
     * Retorna todos los paintworks asociados a una artist
     *
     * @param artistsId El ID de la artist buscada
     * @return La lista de paintworks del artist
     */
    public List<PaintworkEntity> getPaintworks(Long artistsId) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar las obras asociados a la artist con id = {0}", artistsId);
        return artistPersistence.find(artistsId).getPaintworks();
    }

    /**
     * Retorna un paintwork asociado a una artist
     *
     * @param artistsId El id de la artist a buscar.
     * @param paintworksId El id del paintwork a buscar
     * @return El paintwork encontrado dentro de la artist.
     * @throws BusinessLogicException Si el libro no se encuentra en la
     * artist
     */
    public PaintworkEntity getPaintwork(Long artistsId, Long paintworksId) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar  la obra con id {0} de la artist con id = {1}", new Object[]{paintworksId, artistsId});
        List<PaintworkEntity> paintworks = artistPersistence.find(artistsId).getPaintworks();
        PaintworkEntity paintworkEntity = paintworkPersistence.find(paintworksId);
        int index = paintworks.indexOf(paintworkEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultarla obra con id {0} de la artist con id = {1}", new Object[]{paintworksId, artistsId});
        if (index >= 0) {
            return paintworks.get(index);
        }
        throw new BusinessLogicException("La obra no está asociado a la artist");
    }

    /**
     * Remplazar paintworks de una artist
     *
     * @param paintworks Lista de obras que serán los de la artist.
     * @param artistsId El id de la artist que se quiere actualizar.
     * @return La lista de obras actualizada.
     */
    public List<PaintworkEntity> replacePaintworks(Long artistsId, List<PaintworkEntity> paintworks) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la artist con id = {0}", artistsId);
        ArtistEntity artistEntity = artistPersistence.find(artistsId);
        List<PaintworkEntity> paintworkList = paintworkPersistence.findAll();
        for (PaintworkEntity paintwork : paintworkList) 
        {
            if (paintworks.contains(paintwork)) 
            {
                paintwork.setArtist(artistEntity);
            }
            else if (paintwork.getArtist() != null && paintwork.getArtist().equals(artistEntity)) 
            {
                paintwork.setArtist(null);
            }
        }
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la artist con id = {0}", artistsId);
        return paintworks;
    }
}

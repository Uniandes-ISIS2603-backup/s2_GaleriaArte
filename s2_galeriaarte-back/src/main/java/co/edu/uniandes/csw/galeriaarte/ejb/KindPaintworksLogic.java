/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.ejb;

import co.edu.uniandes.csw.galeriaarte.entities.KindEntity;
import co.edu.uniandes.csw.galeriaarte.entities.PaintworkEntity;
import co.edu.uniandes.csw.galeriaarte.persistence.KindPersistence;
import co.edu.uniandes.csw.galeriaarte.persistence.PaintworkPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class KindPaintworksLogic 
{

    private static final Logger LOGGER = Logger.getLogger(KindPaintworksLogic.class.getName());

    @Inject
    private KindPersistence kindPersistence;

    @Inject
    private PaintworkPersistence paintworkPersistence;

    /**
     * Asocia un Paintwork existente a un Kind
     *
     * @param kindsId Identificador de la instancia de Kind
     * @param paintworksId Identificador de la instancia de Paintwork
     * @return Instancia de PaintworkEntity que fue asociada a Kind
     */
    public PaintworkEntity addPaintwork(Long kindsId, Long paintworksId) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de asociarle una obra al tipo con id = {0}", kindsId);
        PaintworkEntity paintworkEntity = paintworkPersistence.find(paintworksId);
        KindEntity kindEntity = kindPersistence.find(kindsId);
        kindEntity.getObra().add(paintworkEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociarle un obra al tipo con id = {0}", kindsId);
        return paintworkPersistence.find(paintworksId);
    }

    /**
     * Obtiene una colección de instancias de PaintworkEntity asociadas a una
     * instancia de Kind
     *
     * @param kindsId Identificador de la instancia de Kind
     * @return Colección de instancias de PaintworkEntity asociadas a la instancia
     * de Kind
     */
    public List<PaintworkEntity> getPaintworks(Long kindsId) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los obras del tipo con id = {0}", kindsId);
        return kindPersistence.find(kindsId).getObra();
    }

    /**
     * Obtiene una instancia de PaintworkEntity asociada a una instancia de Kind
     *
     * @param kindsId Identificador de la instancia de Kind
     * @param paintworksId Identificador de la instancia de Paintwork
     * @return La entidad del obra asociada al tipo
     */
    public PaintworkEntity getPaintwork(Long kindsId, Long paintworksId) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar un obra del tipo con id = {0}", kindsId);
        List<PaintworkEntity> paintworks = kindPersistence.find(kindsId).getObra();
        PaintworkEntity paintworkEntity = paintworkPersistence.find(paintworksId);
        int index = paintworks.indexOf(paintworkEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar un obra del tipo con id = {0}", kindsId);
        if (index >= 0) 
        {
            return paintworks.get(index);
        }
        return null;
    }

    /**
     * Remplaza las instancias de Paintwork asociadas a una instancia de Kind
     *
     * @param kindsId Identificador de la instancia de Kind
     * @param list Colección de instancias de PaintworkEntity a asociar a instancia
     * de Kind
     * @return Nueva colección de PaintworkEntity asociada a la instancia de Kind
     */
    public List<PaintworkEntity> replacePaintworks(Long kindsId, List<PaintworkEntity> list)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar los obras del tipo con id = {0}", kindsId);
        KindEntity kindEntity = kindPersistence.find(kindsId);
        kindEntity.setObra(list);
        LOGGER.log(Level.INFO, "Termina proceso de reemplazar los obras del tipo con id = {0}", kindsId);
        return kindPersistence.find(kindsId).getObra();
    }

    /**
     * Desasocia un Paintwork existente de un Kind existente
     *
     * @param kindsId Identificador de la instancia de Kind
     * @param paintworksId Identificador de la instancia de Paintwork
     */
    public void removePaintwork(Long kindsId, Long paintworksId) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un obra del tipo con id = {0}", kindsId);
        PaintworkEntity paintworkEntity = paintworkPersistence.find(paintworksId);
        KindEntity kindEntity = kindPersistence.find(kindsId);
        kindEntity.getObra().remove(paintworkEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar un obra del tipo con id = {0}", kindsId);
    }
}

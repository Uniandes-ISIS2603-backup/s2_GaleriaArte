/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.ejb;

import co.edu.uniandes.csw.galeriaarte.entities.KindEntity;
import co.edu.uniandes.csw.galeriaarte.entities.PaintworkEntity;
import co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.galeriaarte.persistence.KindPersistence;
import co.edu.uniandes.csw.galeriaarte.persistence.PaintworkPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la relación entre
 * la entidad de Paintwork  y Kind.
 *
 * @author ja.penat
 */
@Stateless
public class PaintworkKindsLogic
{
    private static final Logger LOGGER = Logger.getLogger(PaintworkKindsLogic.class.getName());

    @Inject
    private KindPersistence kindPersistence;

    @Inject
    private PaintworkPersistence paintworkPersistence;

    /**
     * Asocia un kind  existente a un Paintwork
     *
     * @param paintworksId Identificador de la instancia de 
     * @param kindsId Identificador de la instancia de kind
     * @return Instancia de kindEntity que fue asociada a paintwork
     */
    public KindEntity addKind(Long paintworksId, Long kindsId) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de asociarle un tipo al obra con id = {0}", paintworksId);
        PaintworkEntity paintworkEntity = paintworkPersistence.find(paintworksId);
        KindEntity kindEntity = kindPersistence.find(kindsId);
        kindEntity.getObra().add(paintworkEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociarle un tipo al obra con id = {0}", paintworksId);
        return kindPersistence.find(kindsId);
    }

    /**
     * Obtiene una colección de instancias de kindEntity asociadas a una
     * instancia de paintwork
     *
     * @param paintworksId Identificador de la instancia de paintwork
     * @return Colección de instancias de kindEntity asociadas a la instancia de
     * paintwork
     */
    public List<KindEntity> getKinds(Long paintworksId) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los tipos del obra con id = {0}", paintworksId);
        return paintworkPersistence.find(paintworksId).getKind();
    }

    /**
     * Obtiene una instancia de kindEntity asociada a una instancia de paintwork
     *
     * @param paintworksId Identificador de la instancia de paintwork
     * @param kindsId Identificador de la instancia de kind
     * @return La entidadd de tipo del obra
     * @throws BusinessLogicException Si el tipo no está asociado al obra
     */
    public KindEntity getKind(Long paintworksId, Long kindsId) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el tipo con id ={0} del obra con id = {1}", new Object[]{kindsId, paintworksId});
        List<KindEntity> kinds = paintworkPersistence.find(paintworksId).getKind();
        KindEntity kindEntity = kindPersistence.find(kindsId);
        int index = kinds.indexOf(kindEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar  el tipo con id ={0} del obra con id = {1}", new Object[]{kindsId, paintworksId});
        if (index >= 0)
        {
            return kinds.get(index);
        }
        throw new BusinessLogicException("El tipo no está asociado al obra");
    }

    /**
     * Remplaza las instancias de kind asociadas a una instancia de paintwork
     *
     * @param paintworkId Identificador de la instancia de paintwork
     * @param kinds Colección de instancias de kindEntity a asociar a instancia
     * de paintwork
     * @return Nueva colección de kindEntity asociada a la instancia de paintwork
     */
    public List<KindEntity> replaceKinds(Long paintworkId, List<KindEntity> kinds) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar los tipos asocidos al paintwork con id = {0}", paintworkId);
        PaintworkEntity paintworkEntity = paintworkPersistence.find(paintworkId);
        List<KindEntity> kindList = kindPersistence.findAll();
        for (KindEntity kind : kindList)
        {
            if (kinds.contains(kind)) 
            {
                if (!kind.getObra().contains(paintworkEntity)) 
                {
                    kind.getObra().add(paintworkEntity);
                }
            } 
            else 
            {
                kind.getObra().remove(paintworkEntity);
            }
        }
        paintworkEntity.setKind(kinds);
        LOGGER.log(Level.INFO, "Termina proceso de reemplazar los tipos asocidos al paintwork con id = {0}", paintworkId);
        return paintworkEntity.getKind();
    }

    /**
     * Desasocia un kind existente de un paintwork existente
     *
     * @param paintworksId Identificador de la instancia de paintwork
     * @param kindsId Identificador de la instancia de kind
     */
    public void removeKind(Long paintworksId, Long kindsId)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un tipo del paintwork con id = {0}", paintworksId);
        PaintworkEntity paintworkEntity = paintworkPersistence.find(paintworksId);
        KindEntity kindEntity = kindPersistence.find(kindsId);
        kindEntity.getObra().remove(paintworkEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar un tipo del paintwork con id = {0}", paintworksId);
    }
}
    


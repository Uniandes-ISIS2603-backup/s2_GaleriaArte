/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package co.edu.uniandes.csw.galeriaarte.ejb;
import co.edu.uniandes.csw.galeriaarte.entities.ExtraServiceEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.galeriaarte.persistence.ExtraServicePersistence;
/**
 * Clase que implementa la conexion con la persistencia para la entidad de
 * ExtraService.
 * @author estudiante
 */
@Stateless
public class ExtraServiceLogic
{
    private static final Logger LOGGER = Logger.getLogger(ExtraServiceLogic.class.getName());
    
    @Inject
    private ExtraServicePersistence persistence;
    
    /**
     * Crea servicio extra  en la persistencia.
     *
     * @param extraServiceEntity La entidad que representa el servicio extra a
     * persistir.
     * @return La entiddad del servicio extra luego de persistirla.
     * @throws BusinessLogicException Si el servicio extra   a persistir ya existe.
     */
    public ExtraServiceEntity createExtraService(ExtraServiceEntity extraServiceEntity) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del servicio extra ");
        // Verifica que el nombre no exista ya en la base de datos o que no sea nulo.
        if(extraServiceEntity != null && (extraServiceEntity.getName() == null && persistence.findByName(extraServiceEntity.getName()) != null))
        {
            throw new BusinessLogicException("El nombre del servicio extra no es valido o ya esta en la base de datos\"" + extraServiceEntity.getName() + "\"");
        }
        // Verifica que el precio no sea negativo.
        else if(extraServiceEntity != null && extraServiceEntity.getPrice() < 0)
        {
            throw new BusinessLogicException("El precio  del servicio extra no es valido \"" + extraServiceEntity.getPrice() + "\"");
        }
        // Invoca la persistencia para crear el extraService
        persistence.create(extraServiceEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación del servicio extra");
        return extraServiceEntity;
    }
    
    /**
     * Obtener todos los servicios extra  existentes en la base de datos.
     *
     * @return una lista de servicios extra.
     */
    public List<ExtraServiceEntity> getExtraServices()
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas los servicios extra");
        List<ExtraServiceEntity> extraService = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los servicios extra");
        return extraService;
    }
    
    /**
     * Obtener un servicio extra por medio de su id.
     *
     * @param extraServiceId: id del servicio extra para ser buscado.
     * @return el servicoi extra solicitado por medio de su id.
     */
    public ExtraServiceEntity getExtraService(Long extraServiceId)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el servico extra con id = {0}", extraServiceId);
        ExtraServiceEntity extraServiceEntity = persistence.find(extraServiceId);
        if (extraServiceEntity == null)
        {
            LOGGER.log(Level.SEVERE, "El servicio extra con el id = {0} no existe", extraServiceId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el servicio extra  con id = {0}", extraServiceId);
        return extraServiceEntity;
    }
    
    /**
     * Actualizar un extraService.
     *
     * @param extraServiceId: id del servicio extra para buscarla en la base de
     * datos.
     * @param extraServiceEntity: servicio extra con los cambios para ser actualizada,
     * por ejemplo el nombre.
     * @return el servicio extra con los cambios actualizados en la base de datos.
     */
    public ExtraServiceEntity updateExtraService(Long extraServiceId, ExtraServiceEntity extraServiceEntity) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el servicio extra con id = {0}", extraServiceId);
        
        extraServiceEntity.setId(extraServiceId);
        //Valida que el servicio extra este en la base de datos, si no, lo crea.
        if(persistence.find(extraServiceId)== null)
        {
            ExtraServiceEntity newEntity = persistence.create(extraServiceEntity);
            return newEntity;
        }
        // Verifica que el nombre  no sea nulo.
        else if(extraServiceEntity.getName() == null )
        {
            throw new BusinessLogicException("El nombre del servicio extra no es valido o ya esta en la base de datos\"" + extraServiceEntity.getName() + "\"");
        }
        // Verifica que el precio no sea negativo.
        else if(extraServiceEntity.getPrice() < 0)
        {
            throw new BusinessLogicException("El precio  del servicio extra no es valido \"" + extraServiceEntity.getPrice() + "\"");
        }
        ExtraServiceEntity newEntity = persistence.update(extraServiceEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el servicio extra con id = {0}", extraServiceEntity.getId());
        return newEntity;
    }
    
    /**
     * Borrar un servicio extra
     *
     * @param extraServiceId: id del servicio extra a borrar
     */
    public void deleteExtraService(Long extraServiceId)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el servicio extra con id = {0}", extraServiceId);
        if(persistence.find(extraServiceId) != null )
        {
            persistence.delete(extraServiceId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de borrar el servicio extra  con id = {0}", extraServiceId);
    }
    
    
}

/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package co.edu.uniandes.csw.galeriaarte.ejb;

import co.edu.uniandes.csw.galeriaarte.entities.MedioPagoEntity;
import co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.galeriaarte.persistence.MedioPagoPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la entidad de
 * MedioPago.
 * @author ja.penat
 */
@Stateless
public class MedioPagoLogic
{
    private static final Logger LOGGER = Logger.getLogger(MedioPagoLogic.class.getName());
    
    @Inject
    private MedioPagoPersistence persistence;
    
    /**
     * Crea un medio de pago en la persistencia.
     *
     * @param medioPagoEntity La entidad que representa el medio pago a
     * persistir.
     * @return La entiddad del medio de pago luego de persistirla.
     * @throws BusinessLogicException Si el medio de pago  a persistir ya existe.
     */
    public MedioPagoEntity createMedioPago(MedioPagoEntity medioPagoEntity) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del medio de pago");
       
         if( (medioPagoEntity.getBank() == null || medioPagoEntity.getBank().equals("")))
        {
            throw new BusinessLogicException("El nombre del banco  no es valido  \"" + medioPagoEntity.getBank() + "\"");
        }
        // Invoca la persistencia para crear el medio de pago
        persistence.create(medioPagoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación del medio de pago");
        return medioPagoEntity;
    }
    
    /**
     * Obtener todos los medios de pago  existentes en la base de datos.
     *
     * @return una lista de medios de pago.
     */
    public List<MedioPagoEntity> getMediosPago()
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas los medios de pago");
        List<MedioPagoEntity> mediosPago = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los medios de pago");
        return mediosPago;
    }
    
    /**
     * Obtener un medio de pago  por medio de su id.
     *
     * @param medioPagoId: id del medio de pago para ser buscada.
     * @return el medio de pago solicitado por medio de su id.
     */
    public MedioPagoEntity getMedioPago(Long medioPagoId)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el medio de pago con id = {0}", medioPagoId);
        // Note que, por medio de la inyección de dependencias se llama al método "find(id)" que se encuentra en la persistencia.
        return persistence.find(medioPagoId);
    }
    
    /**
     * Actualizar un medio de pago.
     *
     * @param medioPagoId: id del medio de pago para buscarla en la base de
     * datos.
     * @param medioPagoEntity: medioPago con los cambios para ser actualizada,
     * por ejemplo el numero.
     * @return el medio de pago con los cambios actualizados en la base de datos.
     */
    public MedioPagoEntity updateMedioPago(Long medioPagoId, MedioPagoEntity medioPagoEntity) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el medio de pago con id = {0}", medioPagoId);
        // Verifica que el medio de pago tenga numero, que no tenga menos de 16 digitos y que este no sea negativo.
        
        medioPagoEntity.setId(medioPagoId);
        
        if(persistence.find(medioPagoId)== null)
        {
            MedioPagoEntity newEntity = persistence.create(medioPagoEntity);
            return newEntity;
        }
        else if (medioPagoEntity.getNumber() == null && medioPagoEntity.getNumber().toString().length() != 15 )
        {
            throw new BusinessLogicException("El numero del medio de pago no es valido  \"" + medioPagoEntity.getNumber() + "\"");
        }
        // Verifica que el banco no sea nulo
        else if(medioPagoEntity.getBank() == null && medioPagoEntity.getBank().equals(""))
        {
            throw new BusinessLogicException("El nombre del banco  no es valido  \"" + medioPagoEntity.getBank() + "\"");
        }
        
        MedioPagoEntity newEntity = persistence.update(medioPagoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el medio de pago con id = {0}", medioPagoEntity.getId());
        return newEntity;
        
    }
    
    /**
     * Borrar un medio de pago
     *
     * @param medioPagoId: id del medio de pago  a borrar
     */
    public void deleteMedioPago(Long medioPagoId)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el medio de pago con id = {0}", medioPagoId);
        if(persistence.find(medioPagoId) != null )
        {
            persistence.delete(medioPagoId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de borrar el medio de pago  con id = {0}", medioPagoId);
    }
    
}

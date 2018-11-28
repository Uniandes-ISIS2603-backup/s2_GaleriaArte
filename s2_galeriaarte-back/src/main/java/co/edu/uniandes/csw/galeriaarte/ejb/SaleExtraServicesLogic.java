/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.ejb;
import java.util.List;
import co.edu.uniandes.csw.galeriaarte.persistence.SalePersistence;
import co.edu.uniandes.csw.galeriaarte.persistence.ExtraServicePersistence;
import co.edu.uniandes.csw.galeriaarte.entities.SaleEntity;
import co.edu.uniandes.csw.galeriaarte.entities.ExtraServiceEntity;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException;
/**
 *
 * @author s.acostav
 */
@Stateless
public class SaleExtraServicesLogic
{
    
     private static final Logger LOGGER = Logger.getLogger(SaleExtraServicesLogic.class.getName());

    @Inject
    private ExtraServicePersistence extraServicePersistence;

    @Inject
    private SalePersistence salePersistence;

    /**
     * Agregar un  extraService a elsale
     *
     * @param extraServicesId El id extraService a guardar
     * @param salesId El id de el comprador en elcual se va a guardar el
     * extraService.
     * @return elobracreado.
     */
    public ExtraServiceEntity addExtraService(Long extraServicesId, Long salesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de agregarle un  extraService a el comprador con id = {0}", salesId);
        SaleEntity saleEntity = salePersistence.find(salesId);
        ExtraServiceEntity extraServiceEntity = extraServicePersistence.find(extraServicesId);
        List<SaleEntity> sales = extraServiceEntity.getSale();
        sales.add(saleEntity);
        extraServiceEntity.setSale(sales);
        LOGGER.log(Level.INFO, "Termina proceso de agregarle un  extraService a el comprador con id = {0}", salesId);
        return extraServiceEntity;
    }

    /**
     * Retorna todos los extraServices asociados a un a sale
     *
     * @param salesId El ID de el comprador buscada
     * @return ellista de extraServices de elsale
     */
    public List<ExtraServiceEntity> getExtraServices(Long salesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los extraServices asociados a el comprador con id = {0}", salesId);
        return salePersistence.find(salesId).getServices();
    }

    /**
     * Retorna un  extraService asociado a un a sale
     *
     * @param salesId El id de el comprador a buscar.
     * @param extraServicesId El id delobraa buscar
     * @return elobraencontrado dentro de elsale.
     * @throws BusinessLogicException Si elobrano se encuentra en la
     * sale
     */
    public ExtraServiceEntity getExtraService(Long salesId, Long extraServicesId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar elobracon id {0} de el comprador con id = {1}", new Object[]{extraServicesId, salesId});
        List<ExtraServiceEntity> extraServices = salePersistence.find(salesId).getServices();
        ExtraServiceEntity extraServiceEntity = extraServicePersistence.find(extraServicesId);
        int index = extraServices.indexOf(extraServiceEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar elobracon id {0} de el comprador con id = {1}", new Object[]{extraServicesId, salesId});
        if (index >= 0) {
            return extraServices.get(index);
        }
        throw new BusinessLogicException("elobrano está asociado a elsale");
    }

    /**
     * Remplazar extraServices de un a sale
     *
     * @param extraServices Lista de extraServices que serán los de elsale.
     * @param salesId El id de el comprador que se quiere actualizar.
     * @return ellista de extraServices actualizada.
     */
    public List<ExtraServiceEntity> replaceExtraServices(Long salesId, List<ExtraServiceEntity> extraServices) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el comprador con id = {0}", salesId);
        SaleEntity saleEntity = salePersistence.find(salesId);
        List<ExtraServiceEntity> extraServiceList = extraServicePersistence.findAll();
        for (ExtraServiceEntity extraService : extraServiceList) {
            if (extraServices.contains(extraService)) {
                List<SaleEntity> sales = extraService.getSale();
                sales.add(saleEntity);
                extraService.setSale(sales);
            } else if (extraService.getSale() != null && extraService.getSale().equals(saleEntity)) {
                extraService.setSale(null);
            }
        }
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el comprador con id = {0}", salesId);
        return extraServices;
    }
}

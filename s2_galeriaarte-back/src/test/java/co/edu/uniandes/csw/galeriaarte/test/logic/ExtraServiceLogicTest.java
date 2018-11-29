/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package co.edu.uniandes.csw.galeriaarte.test.logic;
import co.edu.uniandes.csw.galeriaarte.ejb.ExtraServiceLogic;
import co.edu.uniandes.csw.galeriaarte.entities.ExtraServiceEntity;
import co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.galeriaarte.persistence.ExtraServicePersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.junit.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
/**
 *Pruebas de logica de extraService
 * @author ja.penat
 */
@RunWith(Arquillian.class)
public class ExtraServiceLogicTest
{
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private ExtraServiceLogic extraServiceLogic;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;
    
    private List<ExtraServiceEntity> data = new ArrayList<ExtraServiceEntity>();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ExtraServiceEntity.class.getPackage())
                .addPackage(ExtraServiceLogic.class.getPackage())
                .addPackage(ExtraServicePersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    /**
     * Configuración inicial de la prueba.
     *
     */
    @Before
    public void configTest()
    {
        try {
            utx.begin();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
    
    /**
     * Limpia las tablas que están implicadas en la prueba.
     *
     */
    private void clearData()
    {
        em.createQuery("delete from ExtraServiceEntity").executeUpdate();
    }
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     *
     */
    private void insertData()
    {
        for (int i = 0; i < 3; i++) {
            ExtraServiceEntity entity = factory.manufacturePojo(ExtraServiceEntity.class);
            
            em.persist(entity);
            data.add(entity);
        }
    }
    
    
    @Test(expected = BusinessLogicException.class)
    public void createExtraServiceTestFail() throws BusinessLogicException
    {
        ExtraServiceEntity newEntity = factory.manufacturePojo(ExtraServiceEntity.class);
        newEntity.setName(null);
        ExtraServiceEntity result = extraServiceLogic.createExtraService(newEntity);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createExtraServiceTestFail2() throws BusinessLogicException
    {
        ExtraServiceEntity newEntity = factory.manufacturePojo(ExtraServiceEntity.class);
        newEntity.setPrice(-10);
        ExtraServiceEntity result = extraServiceLogic.createExtraService(newEntity);
    }
    /**
     * Prueba para crear un extra service.
     *
     */
    @Test
    public void createExtraServiceTest() throws BusinessLogicException
    {
        ExtraServiceEntity newEntity = factory.manufacturePojo(ExtraServiceEntity.class);
        ExtraServiceEntity result = extraServiceLogic.createExtraService(newEntity);
        Assert.assertNotNull(result);
        ExtraServiceEntity entity = em.find(ExtraServiceEntity.class, result.getId());
        Assert.assertEquals(newEntity.getAvailability(), entity.getAvailability());
        Assert.assertEquals(newEntity.getDescription(), entity.getDescription());
        Assert.assertEquals(newEntity.getName(), entity.getName());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getPrice(), entity.getPrice(),0.0002);
        
        
    }
    
    /**
     * Prueba para consultar la lista de servicios extra.
     */
    @Test
    public void getExtraServicesTest()
    {
        List<ExtraServiceEntity> list = extraServiceLogic.getExtraServices();
        Assert.assertEquals(data.size(), list.size());
        for (ExtraServiceEntity entity : list)
        {
            boolean found = false;
            for (ExtraServiceEntity storedEntity : data)
            {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    /**
     * Prueba para consultar un servicio extra.
     */
    @Test
    public void getExtraServiceTest()
    {
        ExtraServiceEntity entity = data.get(0);
        ExtraServiceEntity resultEntity = extraServiceLogic.getExtraService(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getAvailability(), resultEntity.getAvailability());
        Assert.assertEquals(entity.getDescription(), resultEntity.getDescription());
        Assert.assertEquals(entity.getName(), resultEntity.getName());
        Assert.assertEquals(entity.getPrice(), resultEntity.getPrice(),0.0002);
        
    }
    
    /**
     * Prueba para actualizar un servicio extra.
     */
    @Test
    public void updateExtraServiceTest() throws BusinessLogicException
    {
        ExtraServiceEntity entity = data.get(0);
        ExtraServiceEntity pojoEntity = factory.manufacturePojo(ExtraServiceEntity.class);
        
        pojoEntity.setId(entity.getId());
        
        extraServiceLogic.updateExtraService(pojoEntity.getId(), pojoEntity);
        
        ExtraServiceEntity resp = em.find(ExtraServiceEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getAvailability(), resp.getAvailability());
        Assert.assertEquals(pojoEntity.getDescription(), resp.getDescription());
        Assert.assertEquals(pojoEntity.getName(), resp.getName());
        
    }
    
    @Test(expected = BusinessLogicException.class)
    public void updateExtraServiceTestFail() throws BusinessLogicException
    {
        ExtraServiceEntity entity = data.get(0);
        ExtraServiceEntity pojoEntity = factory.manufacturePojo(ExtraServiceEntity.class);
        pojoEntity.setName(null);
        pojoEntity.setId(entity.getId());
        
        extraServiceLogic.updateExtraService(pojoEntity.getId(), pojoEntity);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void updateExtraServiceTestFail2() throws BusinessLogicException
    {
         ExtraServiceEntity entity = data.get(0);
        ExtraServiceEntity pojoEntity = factory.manufacturePojo(ExtraServiceEntity.class);
        pojoEntity.setPrice(-1);
        
        pojoEntity.setId(entity.getId());
        
        extraServiceLogic.updateExtraService(pojoEntity.getId(), pojoEntity);
    }
    
    /**
     * Prueba para eliminar un servicio extra.
     */
    @Test
    public void deleteExtraServiceTest()
    {
        ExtraServiceEntity entity = data.get(0);
        extraServiceLogic.deleteExtraService(entity.getId());
        ExtraServiceEntity deleted = em.find( ExtraServiceEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}

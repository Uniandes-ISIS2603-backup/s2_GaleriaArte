package co.edu.uniandes.csw.galeriaarte.test.persistence;

/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
import co.edu.uniandes.csw.galeriaarte.entities.ExtraServiceEntity;
import java.util.ArrayList;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import java.util.List;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import co.edu.uniandes.csw.galeriaarte.persistence.ExtraServicePersistence;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.runner.RunWith;
/**
 *
 * @author ja.penat
 */
@RunWith(Arquillian.class)
public class ExtraServicePersistenceTest
{
    
    /**
     * Inyección de la dependencia a la clase ExtraServicePersistence cuyos métodos
     * se van a probar.
     */
    @Inject
    private ExtraServicePersistence extraServicePersistence;
    
    /**
     * Contexto de Persistencia que se va a utilizar para acceder a la Base de
     * datos por fuera de los métodos que se están probando.
     */
    @PersistenceContext
    private EntityManager em;
    
    /**
     * Variable para martcar las transacciones del em anterior cuando se
     * crean/borran datos para las pruebas.
     */
    @Inject
            UserTransaction utx;
    
    /**
     * lista que tiene los datos de prueba.
     */
    private List<ExtraServiceEntity> data = new ArrayList<ExtraServiceEntity>();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en el Glassfish
     * embebido. El jar contiene las clases de extraService, el descriptor de la
     * base de datos y el archivo beans.xml para resolver la inyección de
     * dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ExtraServiceEntity.class.getPackage())
                .addPackage(ExtraServicePersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    /**
     * Configuración inicial de la prueba.
     */
    @Before
    public void configTest()
    {
        try {
            utx.begin();
            em.joinTransaction();
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
     */
    private void clearData()
    {
        em.createQuery("delete from ExtraServiceEntity").executeUpdate();
    }
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData()
    {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++)
        {
            
            ExtraServiceEntity entity = factory.manufacturePojo(ExtraServiceEntity.class);
            
            em.persist(entity);
            
            data.add(entity);
        }
    }
    
    /**
     * Prueba para crear un servicio extra.
     */
    @Test
    public void createExtraServiceTest()
    {
        PodamFactory factory = new PodamFactoryImpl();
        ExtraServiceEntity newEntity = factory.manufacturePojo(ExtraServiceEntity.class);
        ExtraServiceEntity result = extraServicePersistence.create(newEntity);
        
        Assert.assertNotNull(result);
        
        ExtraServiceEntity entity = em.find(ExtraServiceEntity.class, result.getId());
        
        Assert.assertEquals(newEntity.getDescription(), entity.getDescription());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getName(), entity.getName());
        Assert.assertEquals(newEntity.getAvailability(), entity.getAvailability());
        Assert.assertEquals(newEntity.getPrice(), entity.getPrice(),0.0002);
    }
    
    /**
     * Prueba para consultar la lista de Extra Service.
     */
    @Test
    public void getExtraServicesTest()
    {
        List<ExtraServiceEntity> list = extraServicePersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (ExtraServiceEntity ent : list)
        {
            boolean found = false;
            for (ExtraServiceEntity entity : data)
            {
                if (ent.getId().equals(entity.getId()))
                {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    /**
     * Prueba para consultar un servicio extra por su nombre
     */
    @Test
    public void getExtraServiceByName()
    {
        ExtraServiceEntity entity = data.get(0);
        ExtraServiceEntity newEntity = extraServicePersistence.findByName(entity.getName());
         Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getDescription(), newEntity.getDescription());
        Assert.assertEquals(entity.getId(), newEntity.getId());
        Assert.assertEquals(entity.getPrice(), newEntity.getPrice(),0.0002);
        Assert.assertEquals(entity.getAvailability(), newEntity.getAvailability());
        Assert.assertEquals(entity.getName(), newEntity.getName());
    }
    /**
     * Prueba para consultar un ExtraService.
     */
    @Test
    public void getExtraServiceTest()
    {
        ExtraServiceEntity entity = data.get(0);
        ExtraServiceEntity newEntity = extraServicePersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getDescription(), newEntity.getDescription());
        Assert.assertEquals(entity.getId(), newEntity.getId());
        Assert.assertEquals(entity.getPrice(), newEntity.getPrice(),0.0002);
        Assert.assertEquals(entity.getAvailability(), newEntity.getAvailability());
        Assert.assertEquals(entity.getName(), newEntity.getName());
    }
    
    /**
     * Prueba para eliminar un ExtraService.
     */
    @Test
    public void deleteExtraServiceTest()
    {
        ExtraServiceEntity entity = data.get(0);
        extraServicePersistence.delete(entity.getId());
        ExtraServiceEntity deleted = em.find(ExtraServiceEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
    /**
     * Prueba para actualizar un Medio de pago.
     */
    @Test
    public void updateExtraServiceTest() 
    {
        ExtraServiceEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        ExtraServiceEntity newEntity = factory.manufacturePojo(ExtraServiceEntity.class);
        
        newEntity.setId(entity.getId());
        
        extraServicePersistence.update(newEntity);
        
        ExtraServiceEntity resp = em.find(ExtraServiceEntity.class, entity.getId());
        
        Assert.assertEquals(newEntity.getPrice(),resp.getPrice(),0.0002);
        Assert.assertEquals(newEntity.getDescription(), resp.getDescription());
        Assert.assertEquals(newEntity.getAvailability(), resp.getAvailability());
        Assert.assertEquals(newEntity.getId(), resp.getId());
        Assert.assertEquals(newEntity.getName(), resp.getName());
    }
}

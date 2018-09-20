/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package co.edu.uniandes.csw.galeriaarte.test.logic;
import co.edu.uniandes.csw.galeriaarte.ejb.MedioPagoLogic;
import co.edu.uniandes.csw.galeriaarte.entities.MedioPagoEntity;
import co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.galeriaarte.persistence.MedioPagoPersistence;
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
 *Pruebas de logica de medioPago
 * @author ja.penat
 */
@RunWith(Arquillian.class)
public class MedioPagoLogicTest
{
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private MedioPagoLogic medioPagoLogic;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;
    
    private List<MedioPagoEntity> data = new ArrayList<MedioPagoEntity>();
    
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(MedioPagoEntity.class.getPackage())
                .addPackage(MedioPagoLogic.class.getPackage())
                .addPackage(MedioPagoPersistence.class.getPackage())
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
        em.createQuery("delete from MedioPagoEntity").executeUpdate();
    }
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     *
     */
    private void insertData()
    {
        for (int i = 0; i < 3; i++) {
            MedioPagoEntity entity = factory.manufacturePojo(MedioPagoEntity.class);
            
            em.persist(entity);
            data.add(entity);
        }
    }
    
    /**
     * Prueba para crear un medio de pago
     *
     */
    @Test
    public void createMedioPagoTest() throws BusinessLogicException
    {
        MedioPagoEntity newEntity = factory.manufacturePojo(MedioPagoEntity.class);
        MedioPagoEntity result = medioPagoLogic.createMedioPago(newEntity);
        Assert.assertNotNull(result);
        MedioPagoEntity entity = em.find(MedioPagoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getBank(), entity.getBank());
        Assert.assertEquals(newEntity.getDescription(), entity.getDescription());
        Assert.assertEquals(newEntity.getNumber(), entity.getNumber());
        Assert.assertEquals(newEntity.getId(), entity.getId());
    }
    
    /**
     * Prueba para crear un medio de pago  con el mismo numero de tarjeta de otro medio de pago que ya
     * existe.
     *
     * @throws
     */
    @Test(expected = BusinessLogicException.class)
    public void createMedioPagoConMismoNumberTest() throws BusinessLogicException
    {
        MedioPagoEntity newEntity = factory.manufacturePojo(MedioPagoEntity.class);
        newEntity.setNumber(data.get(0).getNumber());
        medioPagoLogic.createMedioPago(newEntity);
    }
    
    /**
     * Prueba para consultar la lista de medios de pago.
     */
    @Test
    public void getMediosPagoTest()
    {
        List<MedioPagoEntity> list = medioPagoLogic.getMediosPago();
        Assert.assertEquals(data.size(), list.size());
        for (MedioPagoEntity entity : list)
        {
            boolean found = false;
            for (MedioPagoEntity storedEntity : data)
            {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    /**
     * Prueba para consultar un medio de pago.
     */
    @Test
    public void getMedioPagoTest()
    {
        MedioPagoEntity entity = data.get(0);
        MedioPagoEntity resultEntity = medioPagoLogic.getMedioPago(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getBank(), resultEntity.getBank());
        Assert.assertEquals(entity.getDescription(), resultEntity.getDescription());
        Assert.assertEquals(entity.getNumber(), resultEntity.getNumber());
        
    }
    
    /**
     * Prueba para actualizar un MedioPago.
     */
    @Test
    public void updateMedioPagoTest() throws BusinessLogicException
    {
        MedioPagoEntity entity = data.get(0);
        MedioPagoEntity pojoEntity = factory.manufacturePojo(MedioPagoEntity.class);
        
        pojoEntity.setId(entity.getId());
        
        medioPagoLogic.updateMedioPago(pojoEntity.getId(), pojoEntity);
        
        MedioPagoEntity resp = em.find(MedioPagoEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getBank(), resp.getBank());
        Assert.assertEquals(pojoEntity.getDescription(), resp.getDescription());
        Assert.assertEquals(pojoEntity.getNumber(), resp.getNumber());
        
    }
    
    /**
     * Prueba para eliminar un medio de pago.
     */
    @Test
    public void deleteMedioPagoTest() 
    {
        MedioPagoEntity entity = data.get(0);
        medioPagoLogic.deleteMedioPago(entity.getId());
        MedioPagoEntity deleted = em.find(MedioPagoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}




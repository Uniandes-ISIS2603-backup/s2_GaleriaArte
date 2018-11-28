package co.edu.uniandes.csw.galeriaarte.test.persistence;

/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
import co.edu.uniandes.csw.galeriaarte.entities.KindEntity;
import co.edu.uniandes.csw.galeriaarte.persistence.KindPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Before;
import org.junit.runner.RunWith;
/**
 *
 * @author Ja.penat
 */
@RunWith(Arquillian.class)
public class KindPersistenceTest
{
    /**
     * Inyección de la dependencia a la clase KindPersistence cuyos métodos
     * se van a probar.
     */
    @Inject
    private KindPersistence kindPersistence;
    
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
    private  List<KindEntity> data = new ArrayList();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en el Glassfish
     * embebido. El jar contiene las clases de Kind, el descriptor de la
     * base de datos y el archivo beans.xml para resolver la inyección de
     * dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(KindEntity.class.getPackage())
                .addPackage(KindPersistence.class.getPackage())
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
        }
        catch (Exception e)
        {
            e.printStackTrace();
            try
            {
                utx.rollback();
            }
            catch (Exception e1)
            {
                e1.printStackTrace();
            }
        }
    }
    
    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData()
    {
        em.createQuery("delete from KindEntity").executeUpdate();
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
            KindEntity entity = factory.manufacturePojo(KindEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
    
    /**
     * Prueba para crear un Kind.
     */
    @Test
    public void createKindTest()
    {
        PodamFactory factory = new PodamFactoryImpl();
        KindEntity newEntity = factory.manufacturePojo(KindEntity.class);
        KindEntity result = kindPersistence.create(newEntity);
        
        Assert.assertNotNull(result);
        
        KindEntity entity = em.find(KindEntity.class, result.getId());
        
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getName(), entity.getName());
        Assert.assertEquals(newEntity.getDescription(), entity.getDescription());
    }
    
    /**
     * Prueba para consultar la lista de Kind.
     */
    @Test
    public void getKindsTest()
    {
        List<KindEntity> list =  kindPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (KindEntity ent : list)
        {
            boolean found = false;
            for (KindEntity entity : data)
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
     * Prueba para consultar un kind por su nombre
     */
    
    @Test
    public void getKindByNameTest()
    {
        KindEntity entity = data.get(0);
        KindEntity newEntity = kindPersistence.findByName(entity.getName()); 
         Assert.assertNotNull(newEntity);
        
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getName(), entity.getName());
        Assert.assertEquals(newEntity.getDescription(), entity.getDescription());
    }
    /**
     * Prueba para consultar un Kind.
     */
    @Test
    public void getKindTest()
    {
        KindEntity entity = data.get(0);
        KindEntity newEntity = kindPersistence.find(entity.getId());
        
        Assert.assertNotNull(newEntity);
        
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getName(), entity.getName());
        Assert.assertEquals(newEntity.getDescription(), entity.getDescription());
 
    }
    
    /**
     * Prueba para eliminar un Kind.
     */
    @Test
    public void deleteCVTest()
    {
        KindEntity entity = data.get(0);
        kindPersistence.delete(entity.getId());
        KindEntity deleted = em.find(KindEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
    /**
     * Prueba para actualizar un Kind.
     */
    @Test
    public void updateCVTest()
    {
        KindEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        KindEntity newEntity = factory.manufacturePojo(KindEntity.class);
        
        newEntity.setId(entity.getId());
        
        kindPersistence.update(newEntity);
        
        KindEntity resp = em.find(KindEntity.class, entity.getId());
        
        Assert.assertEquals(newEntity.getId(), resp.getId());
        Assert.assertEquals(newEntity.getName(), resp.getName());
        Assert.assertEquals(newEntity.getDescription(), resp.getDescription());
    }
}
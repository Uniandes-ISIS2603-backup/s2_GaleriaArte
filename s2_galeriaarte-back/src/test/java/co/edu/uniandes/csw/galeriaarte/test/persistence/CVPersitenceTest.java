/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package co.edu.uniandes.csw.galeriaarte.test.persistence;
import co.edu.uniandes.csw.galeriaarte.entities.CVEntity;
import co.edu.uniandes.csw.galeriaarte.persistence.CVPersistence;
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
 * @author LauraManrique y Ja.penat
 */

@RunWith(Arquillian.class)
public class CVPersitenceTest
{
    /**
     * Inyección de la dependencia a la clase CVPersistence cuyos métodos
     * se van a probar.
     */
    @Inject
    private CVPersistence cvPersistence;
    
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
    private  List<CVEntity> data = new ArrayList();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en el Glassfish
     * embebido. El jar contiene las clases de MedioPago, el descriptor de la
     * base de datos y el archivo beans.xml para resolver la inyección de
     * dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CVEntity.class.getPackage())
                .addPackage(CVPersistence.class.getPackage())
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
        em.createQuery("delete from CVEntity").executeUpdate();
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
            CVEntity entity = factory.manufacturePojo(CVEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
    
    /**
     * Prueba para crear un CV.
     */
    @Test
    public void createCVTest()
    {
        PodamFactory factory = new PodamFactoryImpl();
        CVEntity newEntity = factory.manufacturePojo(CVEntity.class);
        CVEntity result = cvPersistence.create(newEntity);
        
        Assert.assertNotNull(result);
        
        CVEntity entity = em.find(CVEntity.class, result.getId());
        
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getEducation(), entity.getEducation());
        Assert.assertEquals(newEntity.getFechaNacimiento(), entity.getFechaNacimiento());
        Assert.assertEquals(newEntity.getInformacionAdicional(), entity.getInformacionAdicional());
        Assert.assertEquals(newEntity.getNombreObraMasConocida(), entity.getNombreObraMasConocida());
        // No comprueba si el artista no es nulo
    }
    
    /**
     * Prueba para consultar la lista de CVs.
     */
    @Test
    public void getCVsTest()
    {
        List<CVEntity> list =  cvPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (CVEntity ent : list)
        {
            boolean found = false;
            for (CVEntity entity : data)
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
     * Prueba para consultar un CV.
     */
    @Test
    public void getCVTest()
    {
        CVEntity entity = data.get(0);
        CVEntity newEntity = cvPersistence.find(entity.getId());
        
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getEducation(), entity.getEducation());
        Assert.assertEquals(newEntity.getFechaNacimiento(), entity.getFechaNacimiento());
        Assert.assertEquals(newEntity.getInformacionAdicional(), entity.getInformacionAdicional());
        Assert.assertEquals(newEntity.getNombreObraMasConocida(), entity.getNombreObraMasConocida());
        //No verifica que el Artista no sea nulo.
    }
    
    /**
     * Prueba para eliminar un CV.
     */
    @Test
    public void deleteCVTest()
    {
        CVEntity entity = data.get(0);
        cvPersistence.delete(entity.getId());
        CVEntity deleted = em.find(CVEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
    /**
     * Prueba para actualizar un CV.
     */
    @Test
    public void updateCVTest()
    {
        CVEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        CVEntity newEntity = factory.manufacturePojo(CVEntity.class);
        
        newEntity.setId(entity.getId());
        
        cvPersistence.update(newEntity);
        
        CVEntity resp = em.find(CVEntity.class, entity.getId());
        
        Assert.assertEquals(newEntity.getId(), resp.getId());
        Assert.assertEquals(newEntity.getEducation(), resp.getEducation());
        Assert.assertEquals(newEntity.getFechaNacimiento(), resp.getFechaNacimiento());
        Assert.assertEquals(newEntity.getInformacionAdicional(), resp.getInformacionAdicional());
        Assert.assertEquals(newEntity.getNombreObraMasConocida(), resp.getNombreObraMasConocida());
    }
}
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package co.edu.uniandes.csw.galeriaarte.test.logic;
import co.edu.uniandes.csw.galeriaarte.ejb.CVLogic;
import co.edu.uniandes.csw.galeriaarte.entities.CVEntity;
import co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.galeriaarte.persistence.CVPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
/**
 *
 * @author LauraManrique y Ja.penat
 */
@RunWith(Arquillian.class)
public class CVLogicTest
{
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private CVLogic cvLogic;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;
    
    private List<CVEntity> data = new ArrayList<>();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CVEntity.class.getPackage())
                .addPackage(CVLogic.class.getPackage())
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
        try
        {
            utx.begin();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e)
        {
            e.printStackTrace();
            try
            {
                utx.rollback();
            } catch (Exception e1)
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
        em.createQuery("delete from CVEntity").executeUpdate();
        em.createQuery("delete from ArtistEntity").executeUpdate();
    }
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData()
    {
        for (int i = 0; i < 3; i++)
        {
            CVEntity entity = factory.manufacturePojo(CVEntity.class);
            
            em.persist(entity);
            data.add(entity);
        }
        
    }
    
    /**
     * Prueba para crear un CV.
     * @throws co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException
     */
    @Test
    public void createCVTest() throws BusinessLogicException
    {
        CVEntity newEntity = factory.manufacturePojo(CVEntity.class);;
        CVEntity result = cvLogic.createCV(newEntity);
        
        Assert.assertNotNull(result);
        CVEntity entity = em.find(CVEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getEducation(), entity.getEducation());
        Assert.assertEquals(newEntity.getFechaNacimiento(), entity.getFechaNacimiento());
        Assert.assertEquals(newEntity.getInformacionAdicional(), entity.getInformacionAdicional());
        Assert.assertEquals(newEntity.getNombreObraMasConocida(), entity.getNombreObraMasConocida());
        
        
    }
    
    /**
     * Prueba para consultar la lista de CVs.
     */
    @Test
    public void getCVsTest()
    {
        
        List<CVEntity> list = cvLogic.getCVs();
        Assert.assertEquals(data.size(), list.size());
        for(CVEntity entity : list)
        {
            boolean found = false;
            for (CVEntity storedEntity : data)
            {
                if (entity.getId().equals(storedEntity.getId()))
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
        CVEntity resultEntity = cvLogic.getCV(entity.getId());
        
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getEducation(), resultEntity.getEducation());
        Assert.assertEquals(entity.getFechaNacimiento(), resultEntity.getFechaNacimiento());
        Assert.assertEquals(entity.getInformacionAdicional(), resultEntity.getInformacionAdicional());
        Assert.assertEquals(entity.getId(), resultEntity.getId());
    }
    
    /**
     * Prueba para actualizar un CV.
     */
    @Test
    public void updateCVTest()
    {
        CVEntity entity = data.get(0);
        CVEntity pojoEntity = factory.manufacturePojo(CVEntity.class);
        
        
        pojoEntity.setId(entity.getId());
        
        cvLogic.updateCV(pojoEntity.getId(), pojoEntity);
        
        CVEntity resultEntity = em.find(CVEntity.class, entity.getId());
        
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(pojoEntity.getId(), resultEntity.getId());
        Assert.assertEquals(pojoEntity.getEducation(), resultEntity.getEducation());
        Assert.assertEquals(pojoEntity.getFechaNacimiento(), resultEntity.getFechaNacimiento());
        Assert.assertEquals(pojoEntity.getInformacionAdicional(), resultEntity.getInformacionAdicional());
        
    }
    /**
     * Prueba para eliminar un cv
     *
     * @throws co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException
     */
    @Test
    public void deleteCVTest() throws BusinessLogicException
    {
        CVEntity entity = data.get(0);
        cvLogic.deleteCV(entity.getId());
        CVEntity deleted = em.find(CVEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}
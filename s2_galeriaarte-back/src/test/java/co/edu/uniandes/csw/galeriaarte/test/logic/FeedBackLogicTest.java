/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package co.edu.uniandes.csw.galeriaarte.test.logic;

import co.edu.uniandes.csw.galeriaarte.ejb.FeedBackLogic;
import co.edu.uniandes.csw.galeriaarte.entities.FeedBackEntity;
import co.edu.uniandes.csw.galeriaarte.entities.PaintworkEntity;
import co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.galeriaarte.persistence.FeedBackPersistence;
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
 * @author s.restrepos1
 */
@RunWith(Arquillian.class)
public class FeedBackLogicTest
{
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private FeedBackLogic feedBackLogic;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;
    
    private List<FeedBackEntity> data = new ArrayList<FeedBackEntity>();
    
    private List<PaintworkEntity> dataPaintwork = new ArrayList<PaintworkEntity>();
    
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(FeedBackEntity.class.getPackage())
                .addPackage(FeedBackLogic.class.getPackage())
                .addPackage(FeedBackPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    /**
     * Configuración inicial de la prueba.
     */
    @Before
    public void configTest() {
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
     */
    private void clearData()
    {
        em.createQuery("delete from FeedBackEntity").executeUpdate();
        em.createQuery("delete from PaintworkEntity").executeUpdate();
        
    }
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData()
    {
        for (int i = 0; i < 3; i++)
        {
            PaintworkEntity entity = factory.manufacturePojo(PaintworkEntity.class);
            
            em.persist(entity);
            dataPaintwork.add(entity);
        }
        
        for (int i = 0; i < 3; i++)
        {
            FeedBackEntity entity = factory.manufacturePojo(FeedBackEntity.class);
            entity.setObra(dataPaintwork.get(1));
            em.persist(entity);
            data.add(entity);
        }
    }
    
    /**
     * Prueba para crear un feedBack.
     *
     * @throws co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException
     */
    @Test
    public void createFeedBackTest() throws BusinessLogicException
    {
        FeedBackEntity newEntity = factory.manufacturePojo(FeedBackEntity.class);
        newEntity.setObra(dataPaintwork.get(1));
        FeedBackEntity result = feedBackLogic.createFeedBack(dataPaintwork.get(1).getId(), newEntity);
        Assert.assertNotNull(result);
        FeedBackEntity entity = em.find(FeedBackEntity.class, result.getId());
        
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getName(), entity.getName());
        Assert.assertEquals(newEntity.getComentario(), entity.getComentario());
        Assert.assertEquals(newEntity.getObra(), entity.getObra());
    }
    
    /**
     * Prueba para consultar la lista de feedBacks.
     *
     * @throws co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException
     */
    @Test
    public void getFeedBacksTest() throws BusinessLogicException
    {
        List<FeedBackEntity> list = feedBackLogic.getFeedBacks(dataPaintwork.get(1).getId());
        Assert.assertEquals(data.size(), list.size());
        for (FeedBackEntity entity : list)
        {
            boolean found = false;
            for (FeedBackEntity storedEntity : data)
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
     * Prueba para consultar un feedBack.
     */
    @Test
    public void getfeedBackTest() 
    {
        FeedBackEntity entity = data.get(0);
        FeedBackEntity resultEntity = feedBackLogic.getFeedBack(dataPaintwork.get(1).getId(), entity.getId());
        Assert.assertNotNull(resultEntity);
        
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getName(), resultEntity.getName());
        Assert.assertEquals(entity.getComentario(), resultEntity.getComentario());
        Assert.assertEquals(entity.getObra(), resultEntity.getObra());
        
    }
    
    /**
     * Prueba para actualizar un feedBack.
     */
    @Test
    public void updateFeedBackTest()
    {
        FeedBackEntity entity = data.get(0);
        FeedBackEntity pojoEntity = factory.manufacturePojo(FeedBackEntity.class);
        
        pojoEntity.setId(entity.getId());
        
        feedBackLogic.updateFeedBack(dataPaintwork.get(1).getId(), pojoEntity);
        
        FeedBackEntity resp = em.find(FeedBackEntity.class, entity.getId());
        
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getName(), resp.getName());
        Assert.assertEquals(pojoEntity.getComentario(), resp.getComentario());
        Assert.assertEquals(pojoEntity.getObra(), resp.getObra());
                
                }
    
    /**
     * Prueba para eliminar un feedBack.
     *
     * @throws co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException
     */
    @Test
    public void deleteFeedBackTest() throws BusinessLogicException
    {
        FeedBackEntity entity = data.get(0);
        feedBackLogic.deleteFeedBack(dataPaintwork.get(1).getId(), entity.getId());
        FeedBackEntity deleted = em.find(FeedBackEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}
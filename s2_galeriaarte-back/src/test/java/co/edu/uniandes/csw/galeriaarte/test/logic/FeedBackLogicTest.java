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
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author s.restrepos1
 */
@Stateless
public class FeedBackLogicTest 
{
     private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private FeedBackLogic feedLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<FeedBackEntity> data = new ArrayList<>();
    
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
    private void clearData() {
        em.createQuery("delete from PaintworkEntity").executeUpdate();
        em.createQuery("delete from CVEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            FeedBackEntity entity = factory.manufacturePojo(FeedBackEntity.class);
            em.persist(entity);
            PaintworkEntity obra =new PaintworkEntity();
            entity.setObra(obra);
            data.add(entity);
        }
       
    }
    
    /**
     * Prueba para crear un FeedBack.
     * @throws co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException
     */ 
    @Test
    public void createFeedBackTest() throws BusinessLogicException 
    {
        FeedBackEntity newEntity = factory.manufacturePojo(FeedBackEntity.class);
        FeedBackEntity result = feedLogic.createFeedBack(newEntity);
        Assert.assertNotNull(result);
        FeedBackEntity entity = em.find(FeedBackEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getName(), entity.getName());
    }
    
    /**
     * Prueba para consultar la lista de FeedBack.
     */
    @Test
    public void getFeedBacksTest() {
        List<FeedBackEntity> list = feedLogic.getFeedBacks();
        Assert.assertEquals(data.size(), list.size());
        for (FeedBackEntity entity : list) {
            boolean found = false;
            for (FeedBackEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
     /**
     * Prueba para consultar un FeedBack.
     */
    @Test
    public void getFeedBackTest() {
        FeedBackEntity entity = data.get(0);
        FeedBackEntity resultEntity = feedLogic.getFeedBack(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getName(), resultEntity.getName());
    }
    
     /**
     * Prueba para actualizar un FeedBack.
     * @throws co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException
     */
    @Test
    public void updateFeedBackTest() throws BusinessLogicException 
    {
        FeedBackEntity entity = data.get(0);
        FeedBackEntity pojoEntity = factory.manufacturePojo(FeedBackEntity.class);

        pojoEntity.setId(entity.getId());

        feedLogic.updateFeedBack(pojoEntity.getId(), pojoEntity);

        FeedBackEntity resp = em.find(FeedBackEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getName(), resp.getName());
    }
    /**
     * Prueba para eliminar un FeedBack
     *
     * @throws co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException
     */
    @Test
    public void deleteFeedBackTest() throws BusinessLogicException {
       FeedBackEntity entity = data.get(0);
       feedLogic.deleteFeedBack(entity.getId());
        FeedBackEntity deleted = em.find(FeedBackEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

}
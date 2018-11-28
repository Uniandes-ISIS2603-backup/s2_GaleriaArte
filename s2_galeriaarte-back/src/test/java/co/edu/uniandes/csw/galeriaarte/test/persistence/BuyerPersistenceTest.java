/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.test.persistence;

import co.edu.uniandes.csw.galeriaarte.entities.BuyerEntity;
import co.edu.uniandes.csw.galeriaarte.persistence.BuyerPersistence;
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
 * @author s.acostav sara acosta villegas 
 * 
 */
@RunWith(Arquillian.class)
public class BuyerPersistenceTest {
    
    
    @Inject
    private BuyerPersistence buyerPersistence;
    
    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;

    private List<BuyerEntity> data = new ArrayList<>();
    
    @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(BuyerEntity.class.getPackage())
                .addPackage(BuyerPersistence.class.getPackage())
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
    private void clearData() {
        em.createQuery("delete from BuyerEntity").executeUpdate();      
    }
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            BuyerEntity entity = factory.manufacturePojo(BuyerEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
    
    /**
     * Prueba para crear un comprador
     */
    @Test
    public void createBuyerTest() {
        PodamFactory factory = new PodamFactoryImpl();
        BuyerEntity newEntity = factory.manufacturePojo(BuyerEntity.class);
        BuyerEntity result = buyerPersistence.create(newEntity);

        Assert.assertNotNull(result);

        BuyerEntity entity = em.find(BuyerEntity.class, result.getId());

        Assert.assertEquals(newEntity.getName(), entity.getName());
        Assert.assertEquals(entity.getAdress(), newEntity.getAdress());
            Assert.assertEquals(entity.getCreditCard(), newEntity.getCreditCard());
                Assert.assertEquals(entity.getEmail(), newEntity.getEmail());
                    Assert.assertEquals(entity.getPassword(), newEntity.getPassword());
                        Assert.assertEquals(entity.getPhone(), newEntity.getPhone());
                            Assert.assertEquals(entity.getCountry(), newEntity.getCountry());
                               Assert.assertEquals(entity.getId(), newEntity.getId());
                            
    }

    /**
     * Prueba para consultar la lista de compradores.
     */
    @Test
    public void getBuyersTest() {
        List<BuyerEntity> list = buyerPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (BuyerEntity ent : list) {
            boolean found = false;
            for (BuyerEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Comprador.
     */
    @Test
    public void getBuyerTest() {
        BuyerEntity entity = data.get(0);
        BuyerEntity newEntity = buyerPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getName(), newEntity.getName());
        Assert.assertEquals(entity.getAdress(), newEntity.getAdress());
            Assert.assertEquals(entity.getCreditCard(), newEntity.getCreditCard());
                Assert.assertEquals(entity.getEmail(), newEntity.getEmail());
                    Assert.assertEquals(entity.getPassword(), newEntity.getPassword());
                        Assert.assertEquals(entity.getPhone(), newEntity.getPhone());
                            Assert.assertEquals(entity.getCountry(), newEntity.getCountry());
                               Assert.assertEquals(entity.getId(), newEntity.getId());
                            
    }

    /**
     * Prueba para actualizar un comprador.
     */
    @Test
    public void updateBuyerTest() {
        BuyerEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        BuyerEntity newEntity = factory.manufacturePojo(BuyerEntity.class);

        newEntity.setId(entity.getId());

        buyerPersistence.update(newEntity);

        BuyerEntity resp = em.find(BuyerEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getName(), resp.getName());
        Assert.assertEquals(resp.getAdress(), newEntity.getAdress());
            Assert.assertEquals(resp.getCreditCard(), newEntity.getCreditCard());
                Assert.assertEquals(resp.getEmail(), newEntity.getEmail());
                    Assert.assertEquals(resp.getPassword(), newEntity.getPassword());
                        Assert.assertEquals(resp.getPhone(), newEntity.getPhone());
                            Assert.assertEquals(resp.getCountry(), newEntity.getCountry());
                               Assert.assertEquals(resp.getId(), newEntity.getId());
                            
    }

    /**
     * Prueba para eliminar un comprador.
     */
    @Test
    public void deleteBuyerTest() {
        BuyerEntity entity = data.get(0);
        buyerPersistence.delete(entity.getId());
        BuyerEntity deleted = em.find(BuyerEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}



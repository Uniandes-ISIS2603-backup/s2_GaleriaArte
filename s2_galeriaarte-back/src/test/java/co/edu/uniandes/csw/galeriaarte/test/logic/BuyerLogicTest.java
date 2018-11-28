/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.test.logic;

import co.edu.uniandes.csw.galeriaarte.ejb.BuyerLogic;
import co.edu.uniandes.csw.galeriaarte.entities.BuyerEntity;
import co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.galeriaarte.persistence.BuyerPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
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
 * @author s.acostav
 */

@RunWith(Arquillian.class)
public class BuyerLogicTest
{
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private BuyerLogic buyerLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<BuyerEntity> data = new ArrayList<>();

    /**
     * 
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(BuyerEntity.class.getPackage())
                .addPackage(BuyerLogic.class.getPackage())
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
        em.createQuery("delete from PaintworkEntity").executeUpdate();
        em.createQuery("delete from SaleEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            BuyerEntity entity = factory.manufacturePojo(BuyerEntity.class);
            em.persist(entity);
            entity.setPaintworks(new ArrayList());
            data.add(entity);
        }
        
    }

    /**
     * Prueba para crear un comprador.
     */
    @Test
    public void createBuyerTest() {
        BuyerEntity newEntity = factory.manufacturePojo(BuyerEntity.class);
        BuyerEntity result = buyerLogic.createBuyer(newEntity);
        Assert.assertNotNull(result);
        BuyerEntity entity = em.find(BuyerEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getName(), entity.getName());
        Assert.assertEquals(newEntity.getPassword(), entity.getPassword());
    }

    /**
     * Prueba para consultar la lista de compradores.
     */
    @Test
    public void getBuyersTest() {
        List<BuyerEntity> list = buyerLogic.getBuyers();
        Assert.assertEquals(data.size(), list.size());
        for (BuyerEntity entity : list) {
            boolean found = false;
            for (BuyerEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
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
        BuyerEntity resultEntity = buyerLogic.getBuyer(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getName(), resultEntity.getName());
    }

    /**
     * Prueba para actualizar un Comprador.
     */
    @Test
    public void updateBuyerTest() {
        BuyerEntity entity = data.get(0);
        BuyerEntity pojoEntity = factory.manufacturePojo(BuyerEntity.class);

        pojoEntity.setId(entity.getId());

        buyerLogic.updateBuyer(pojoEntity.getId(), pojoEntity);

        BuyerEntity resp = em.find(BuyerEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getName(), resp.getName());
    }

   
}

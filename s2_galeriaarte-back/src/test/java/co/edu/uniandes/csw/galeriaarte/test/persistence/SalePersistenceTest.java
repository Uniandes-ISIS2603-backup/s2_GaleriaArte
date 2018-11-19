package co.edu.uniandes.csw.galeriaarte.test.persistence;


import co.edu.uniandes.csw.galeriaarte.entities.BuyerEntity;
import co.edu.uniandes.csw.galeriaarte.entities.SaleEntity;
import co.edu.uniandes.csw.galeriaarte.persistence.SalePersistence;
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author s.restrepos1
 */
@RunWith(Arquillian.class)
public class SalePersistenceTest
{
   @Inject
    private SalePersistence salePersistence;
    
    private List<SaleEntity> data = new ArrayList<>();
    @PersistenceContext
    private EntityManager em;
    
     @Inject
    UserTransaction utx;

    
    @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(SaleEntity.class.getPackage())
                .addPackage(SalePersistence.class.getPackage())
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
            SaleEntity entity = factory.manufacturePojo(SaleEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    
    }
    
    @Test
    public void createSaleTest()
    {
        PodamFactory factory = new PodamFactoryImpl();
        SaleEntity newEntity= factory.manufacturePojo(SaleEntity.class);
        SaleEntity result= salePersistence.create(newEntity);
        Assert.assertNotNull(result);
        
        SaleEntity entity= em.find(SaleEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
    } 
    
     /**
     * Prueba para consultar un Comprador.
     */
    @Test
    public void getSaleTest() {
        SaleEntity entity = data.get(0);
        SaleEntity newEntity = salePersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getId(), newEntity.getId());
    }

    /**
     * Prueba para actualizar un comprador.
     */
    @Test
    public void updateSaleTest() {
        SaleEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        SaleEntity newEntity = factory.manufacturePojo(SaleEntity.class);

        newEntity.setId(entity.getId());

        salePersistence.update(newEntity);

        SaleEntity resp = em.find(SaleEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getId(), resp.getId());
    }

    /**
     * Prueba para eliminar un comprador.
     */
    @Test
    public void deleteSaleTest() {
        SaleEntity entity = data.get(0);
        salePersistence.delete(entity.getId());
        SaleEntity deleted = em.find(SaleEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}

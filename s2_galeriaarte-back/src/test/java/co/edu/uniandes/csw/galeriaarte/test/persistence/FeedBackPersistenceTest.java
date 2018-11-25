package co.edu.uniandes.csw.galeriaarte.test.persistence;

/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/


import co.edu.uniandes.csw.galeriaarte.entities.FeedBackEntity;
import co.edu.uniandes.csw.galeriaarte.entities.PaintworkEntity;
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
import org.junit.Test;
import org.junit.Assert;
import org.junit.Before;
import uk.co.jemos.podam.api.PodamFactory;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author s.restrepos1(solo metodo create) y ja.penat
 */
@RunWith(Arquillian.class)
public class FeedBackPersistenceTest
{
    /**
     * Inyección de la dependencia a la clase FeedbackPersistence cuyos métodos
     * se van a probar.
     */
    @Inject
    private FeedBackPersistence feedBackPersistence;
    
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
    private List<FeedBackEntity> data = new ArrayList<FeedBackEntity>();
    
   /**
    * Lista que tiene las obras con datos de preuba.
    */
    private List<PaintworkEntity> dataObra = new ArrayList<PaintworkEntity>();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en el Glassfish
     * embebido. El jar contiene las clases de FeedBackEntity, el descriptor de la
     * base de datos y el archivo beans.xml para resolver la inyección de
     * dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(FeedBackEntity.class.getPackage())
                .addPackage(FeedBackPersistence.class.getPackage())
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
        em.createQuery("delete from FeedBackEntity").executeUpdate();
        em.createQuery("delete from PaintworkEntity").executeUpdate();
    }
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData()
    {
        PodamFactory factory = new PodamFactoryImpl();
        
        for(int i = 0; i<3; i++)
        {
            PaintworkEntity entity = factory.manufacturePojo(PaintworkEntity.class);
            em.persist(entity);
            dataObra.add(entity);
        }
        for (int i = 0; i < 3; i++) 
        {
            
            FeedBackEntity entity = factory.manufacturePojo(FeedBackEntity.class);
            if (i == 0) 
            {
                entity.setObra(dataObra.get(0));
            }
            em.persist(entity);
            
            data.add(entity);
        }
    }
    
    /**
     * Prueba para crear un feedBack.
     */
    @Test
    public void createFeedBackTest()
    {
        PodamFactory factory = new PodamFactoryImpl();
        FeedBackEntity newEntity = factory.manufacturePojo(FeedBackEntity.class);
        FeedBackEntity result = feedBackPersistence.create(newEntity);
        
        Assert.assertNotNull(result);
        
        FeedBackEntity entity = em.find(FeedBackEntity.class, result.getId());
        
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getName(), entity.getName());
        Assert.assertEquals(newEntity.getComentario(), entity.getComentario());  
        Assert.assertEquals(newEntity.getObra(), entity.getObra()); 
        
    }
    
    /**
     * Prueba para consultar un FeedBack.
     */
    @Test
    public void getFeedBackTest()
    {
        FeedBackEntity entity = data.get(0);
        FeedBackEntity newEntity = feedBackPersistence.find(dataObra.get(0).getId(), entity.getId());

        Assert.assertNotNull(newEntity);

        Assert.assertEquals(entity.getId(), newEntity.getId());
        Assert.assertEquals(newEntity.getName(), entity.getName());
        Assert.assertEquals(newEntity.getComentario(), entity.getComentario());
    }
    
    /**
     * Prueba para eliminar un FeedBack.
     */
    @Test
    public void deleteFeedBackTest()
    {
        FeedBackEntity entity = data.get(0);
        feedBackPersistence.delete(entity.getId());
        FeedBackEntity deleted = em.find(FeedBackEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
    /**
     * Prueba para actualizar un FeedBack.
     */
    @Test
    public void updateFeedBackTest() 
    {
        FeedBackEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        FeedBackEntity newEntity = factory.manufacturePojo(FeedBackEntity.class);
        
        newEntity.setId(entity.getId());
        
        feedBackPersistence.update(newEntity);
        
        FeedBackEntity resp = em.find(FeedBackEntity.class, entity.getId());
        
        Assert.assertEquals(newEntity.getName(), resp.getName());
        Assert.assertEquals(newEntity.getComentario(), resp.getComentario());
        Assert.assertEquals(newEntity.getId(), resp.getId());
        Assert.assertEquals(newEntity.getObra(), resp.getObra());
        
    }
}
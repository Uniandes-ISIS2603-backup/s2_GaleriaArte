/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.test.logic;

import co.edu.uniandes.csw.galeriaarte.ejb.PaintworkLogic;
import co.edu.uniandes.csw.galeriaarte.entities.PaintworkEntity;
import co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.galeriaarte.persistence.PaintworkPersistence;
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
 *Pruebas de logica de Paintwork
 * @author ja.penat
 */
@RunWith(Arquillian.class)
public class PaintworkLogicTest
{
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private PaintworkLogic paintworkLogic;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;
    
    private List<PaintworkEntity> data = new ArrayList<PaintworkEntity>();
    
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(PaintworkEntity.class.getPackage())
                .addPackage(PaintworkLogic.class.getPackage())
                .addPackage(PaintworkPersistence.class.getPackage())
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
        em.createQuery("delete from PaintworkEntity").executeUpdate();
    }
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     *
     */
    private void insertData()
    {
        for (int i = 0; i < 3; i++)
        {
            PaintworkEntity entity = factory.manufacturePojo(PaintworkEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
    
    /**
     * Prueba para crear una obra
     *
     * @throws co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException
     */
    @Test
    public void createPaintworkTest() throws BusinessLogicException 
    {
        PaintworkEntity newEntity = factory.manufacturePojo(PaintworkEntity.class);
        PaintworkEntity result = paintworkLogic.createPaintWork(newEntity);
       
        Assert.assertNotNull(result);
        
        PaintworkEntity entity = em.find(PaintworkEntity.class, result.getId());

        Assert.assertEquals(newEntity.getId(), entity.getId());
         Assert.assertEquals(newEntity.getName(), entity.getName());
         Assert.assertEquals(newEntity.getCountry(), entity.getCountry());
         Assert.assertEquals(newEntity.getDescription(), entity.getDescription());
         Assert.assertEquals(newEntity.getValor(), entity.getValor());
           Assert.assertEquals(newEntity.getVerificacionObra(), entity.getVerificacionObra() );
           Assert.assertEquals(newEntity.getImagePath(), entity.getImagePath());
           Assert.assertEquals(newEntity.getVideoPath(), entity.getVideoPath());
    }
    
    /**
    
    
    /**
     * Prueba para consultar la lista de obras.
     */
    @Test
    public void getPaintworksTest()
    {
        List<PaintworkEntity> list = paintworkLogic.getPaintworks();
        Assert.assertEquals(data.size(), list.size());
        for (PaintworkEntity entity : list)
        {
            boolean found = false;
            for (PaintworkEntity storedEntity : data)
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
     * Prueba para consultar un paintwork.
     */
    @Test
    public void getPaintworkTest()
    {
        PaintworkEntity entity = data.get(0);
        PaintworkEntity resultEntity = paintworkLogic.getPaintWork(entity.getId());
       
        Assert.assertNotNull(resultEntity);
          
         Assert.assertEquals(resultEntity.getId(), entity.getId());
         Assert.assertEquals(resultEntity.getName(), entity.getName());
         Assert.assertEquals(resultEntity.getCountry(), entity.getCountry());
         Assert.assertEquals(resultEntity.getDescription(), entity.getDescription());
         Assert.assertEquals(resultEntity.getValor(), entity.getValor());
           Assert.assertEquals(resultEntity.getVerificacionObra(), entity.getVerificacionObra() );
           Assert.assertEquals(resultEntity.getImagePath(), entity.getImagePath());
           Assert.assertEquals(resultEntity.getVideoPath(), entity.getVideoPath());
    }
    
    /**
     * Prueba para actualizar un Paintwork.
     * @throws co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException
     */
    @Test
    public void updatePaintworkTest() throws BusinessLogicException
    {
        PaintworkEntity entity = data.get(0);
        PaintworkEntity pojoEntity = factory.manufacturePojo(PaintworkEntity.class);
        
        pojoEntity.setId(entity.getId());
        
        paintworkLogic.updatePaintWork(pojoEntity.getId(), pojoEntity);
        
        PaintworkEntity resp = em.find(PaintworkEntity.class, entity.getId());
        
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
         Assert.assertEquals(pojoEntity.getName(), resp.getName());
         Assert.assertEquals(pojoEntity.getCountry(), resp.getCountry());
         Assert.assertEquals(pojoEntity.getDescription(), resp.getDescription());
         Assert.assertEquals(pojoEntity.getValor(), resp.getValor());
           Assert.assertEquals(pojoEntity.getVerificacionObra(), resp.getVerificacionObra() );
           Assert.assertEquals(pojoEntity.getImagePath(), resp.getImagePath());
           Assert.assertEquals(pojoEntity.getVideoPath(), resp.getVideoPath());
        
        
    }
    
    /**
     * Prueba para eliminar una obra.
     */
    @Test
    public void deletePaintworkTest() 
    {
        PaintworkEntity entity = data.get(0);
        paintworkLogic.deletePaintWork(entity.getId());
        PaintworkEntity deleted = em.find(PaintworkEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}





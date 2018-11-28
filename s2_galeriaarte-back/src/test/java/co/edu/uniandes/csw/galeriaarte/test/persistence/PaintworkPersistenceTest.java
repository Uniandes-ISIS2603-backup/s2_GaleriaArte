package co.edu.uniandes.csw.galeriaarte.test.persistence;


import co.edu.uniandes.csw.galeriaarte.entities.PaintworkEntity;
import co.edu.uniandes.csw.galeriaarte.persistence.PaintworkPersistence;
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
 * @author Anderson Barragan
 */
@RunWith(Arquillian.class)
public class PaintworkPersistenceTest {
    @Inject
    private PaintworkPersistence paintworkPersistence;
    
    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;

    private List<PaintworkEntity> data = new ArrayList<>();
    
    @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(PaintworkEntity.class.getPackage())
                .addPackage(PaintworkPersistence.class.getPackage())
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
        em.createQuery("delete from PaintworkEntity").executeUpdate();
    }
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            PaintworkEntity entity = factory.manufacturePojo(PaintworkEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
    
    /**
     * Prueba para crear un Paintwork.
     */
    @Test
    public void createPaintworkTest() {
        PodamFactory factory = new PodamFactoryImpl();
        PaintworkEntity newEntity = factory.manufacturePojo(PaintworkEntity.class);
        PaintworkEntity result = paintworkPersistence.create(newEntity);

        Assert.assertNotNull(result);

        PaintworkEntity entity = em.find(PaintworkEntity.class, result.getId());

        Assert.assertEquals(newEntity.getName(), entity.getName());
    }

    
    /**
     * Prueba para consultar la lista de Paintworks.
     */
    @Test
    public void getPaintworksTest() {
        List<PaintworkEntity> list = paintworkPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (PaintworkEntity ent : list) {
            boolean found = false;
            for (PaintworkEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar una obra por su nombre
     */
    @Test
    public void getPaintworkByNameTest()
    {
         PaintworkEntity entity = data.get(0);
        PaintworkEntity newEntity = paintworkPersistence.findByName(entity.getName());
        Assert.assertNotNull(newEntity);
       
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
     * Prueba para consultar un Paintwork.
     */
    @Test
    public void getPaintworkTest() {
        PaintworkEntity entity = data.get(0);
        PaintworkEntity newEntity = paintworkPersistence.find(entity.getId());
        
        Assert.assertNotNull(newEntity);
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
     * Prueba para actualizar un Paintwork.
     */
    @Test
    public void updatePaintworkTest() {
        PaintworkEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        PaintworkEntity newEntity = factory.manufacturePojo(PaintworkEntity.class);

        newEntity.setId(entity.getId());

        paintworkPersistence.update(newEntity);

        PaintworkEntity resp = em.find(PaintworkEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getName(), resp.getName());
          Assert.assertEquals(newEntity.getId(), resp.getId());
         Assert.assertEquals(newEntity.getName(), resp.getName());
         Assert.assertEquals(newEntity.getCountry(), resp.getCountry());
         Assert.assertEquals(newEntity.getDescription(), resp.getDescription());
         Assert.assertEquals(newEntity.getValor(), resp.getValor());
           Assert.assertEquals(newEntity.getVerificacionObra(), resp.getVerificacionObra() );
           Assert.assertEquals(newEntity.getImagePath(), resp.getImagePath());
           Assert.assertEquals(newEntity.getVideoPath(), resp.getVideoPath());
    }

    /**
     * Prueba para eliminar un Paintwork.
     */
    @Test
    public void deletePaintworkTest() {
        PaintworkEntity entity = data.get(0);
        paintworkPersistence.delete(entity.getId());
        PaintworkEntity deleted = em.find(PaintworkEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}

package co.edu.uniandes.csw.galeriaarte.test.persistence;


import co.edu.uniandes.csw.galeriaarte.entities.ArtistEntity;
import co.edu.uniandes.csw.galeriaarte.persistence.ArtistPersistence;
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
public class ArtistPersistenceTest {
    @Inject
    private ArtistPersistence artistPersistence;
    
    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;

    private List<ArtistEntity> data = new ArrayList<>();
    
    @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ArtistEntity.class.getPackage())
                .addPackage(ArtistPersistence.class.getPackage())
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
        em.createQuery("delete from ArtistEntity").executeUpdate();
        em.createQuery("delete from CVEntity").executeUpdate();
    }
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            ArtistEntity entity = factory.manufacturePojo(ArtistEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
    
    /**
     * Prueba para crear un Artista.
     */
    @Test
    public void createArtistTest() {
        PodamFactory factory = new PodamFactoryImpl();
        ArtistEntity newEntity = factory.manufacturePojo(ArtistEntity.class);
        ArtistEntity result = artistPersistence.create(newEntity);

        Assert.assertNotNull(result);

        ArtistEntity entity = em.find(ArtistEntity.class, result.getId());

        Assert.assertEquals(newEntity.getName(), entity.getName());
    }

    /**
     * Prueba para consultar la lista de Artistas.
     */
    @Test
    public void getArtistsTest() {
        List<ArtistEntity> list = artistPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (ArtistEntity ent : list) {
            boolean found = false;
            for (ArtistEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Artista.
     */
    @Test
    public void getArtistTest() {
        ArtistEntity entity = data.get(0);
        ArtistEntity newEntity = artistPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getName(), newEntity.getName());
    }

    /**
     * Prueba para actualizar un Artista.
     */
    @Test
    public void updateArtistTest() {
        ArtistEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        ArtistEntity newEntity = factory.manufacturePojo(ArtistEntity.class);

        newEntity.setId(entity.getId());

        artistPersistence.update(newEntity);

        ArtistEntity resp = em.find(ArtistEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getName(), resp.getName());
    }

    /**
     * Prueba para eliminar un Artista.
     */
    @Test
    public void deleteArtistTest() {
        ArtistEntity entity = data.get(0);
        artistPersistence.delete(entity.getId());
        ArtistEntity deleted = em.find(ArtistEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}

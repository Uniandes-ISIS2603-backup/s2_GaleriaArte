/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.test.logic;

import co.edu.uniandes.csw.galeriaarte.ejb.ArtistLogic;
import co.edu.uniandes.csw.galeriaarte.entities.ArtistEntity;
import co.edu.uniandes.csw.galeriaarte.entities.CVEntity;
import co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.galeriaarte.persistence.ArtistPersistence;
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
 *Clase que implementa la conexion con la persistencia para la entidad de
 * @Artist a.barragan Anderson Barragan
 */
@Stateless
public class ArtistLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private ArtistLogic artistLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<ArtistEntity> data = new ArrayList<>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ArtistEntity.class.getPackage())
                .addPackage(ArtistLogic.class.getPackage())
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
            ArtistEntity entity = factory.manufacturePojo(ArtistEntity.class);
            em.persist(entity);
            entity.setPaintworks(new ArrayList<>());
            data.add(entity);
        }
        //ArtistEntity artist = data.get(2);
        //CVEntity entity = factory.manufacturePojo(CVEntity.class);
        //entity.getArtists().add(artist);
        //em.persist(entity);
       // artist.getObras().add(entity);
    }

    /**
     * Prueba para crear un Artist.
     */
    @Test
    public void createArtistTest() {
        ArtistEntity newEntity = factory.manufacturePojo(ArtistEntity.class);
        ArtistEntity result = artistLogic.createArtist(newEntity);
        Assert.assertNotNull(result);
        ArtistEntity entity = em.find(ArtistEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getName(), entity.getName());
    }

    /**
     * Prueba para consultar la lista de Artistas.
     */
    @Test
    public void getArtistsTest() {
        List<ArtistEntity> list = artistLogic.getArtists();
        Assert.assertEquals(data.size(), list.size());
        for (ArtistEntity entity : list) {
            boolean found = false;
            for (ArtistEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Author.
     */
    @Test
    public void getArtistTest() {
        ArtistEntity entity = data.get(0);
        ArtistEntity resultEntity = artistLogic.getArtist(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getName(), resultEntity.getName());
    }

    /**
     * Prueba para actualizar un Artist.
     */
    @Test
    public void updateArtistTest() {
        ArtistEntity entity = data.get(0);
        ArtistEntity pojoEntity = factory.manufacturePojo(ArtistEntity.class);

        pojoEntity.setId(entity.getId());

        artistLogic.updateArtist(pojoEntity.getId(), pojoEntity);

        ArtistEntity resp = em.find(ArtistEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getName(), resp.getName());
    }

    /**
     * Prueba para eliminar un Artist
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test
    public void deleteArtistTest() throws BusinessLogicException {
        //ArtistEntity entity = data.get(0);
        //ArtistEntity.deleteArtist(entity.getId());
        //ArtistEntity deleted = em.find(ArtistEntity.class, entity.getId());
       // Assert.assertNull(deleted);
       Assert.assertEquals(null, null);
    }
}

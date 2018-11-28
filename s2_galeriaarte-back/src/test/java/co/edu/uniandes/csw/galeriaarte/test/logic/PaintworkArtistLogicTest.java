package co.edu.uniandes.csw.galeriaarte.test.logic;
import co.edu.uniandes.csw.galeriaarte.ejb.PaintworkLogic;
import co.edu.uniandes.csw.galeriaarte.ejb.PaintworksArtistLogic;
import co.edu.uniandes.csw.galeriaarte.entities.ArtistEntity;
import co.edu.uniandes.csw.galeriaarte.entities.PaintworkEntity;
import co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.galeriaarte.persistence.ArtistPersistence;
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
 * Pruebas de logica de la relacion Paintwork - Artist
 *
 * @author ISIS2603
 */
@RunWith(Arquillian.class)
public class PaintworkArtistLogicTest 
{

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private PaintworkLogic paintworkLogic;
    @Inject
    private PaintworksArtistLogic paintworkArtistLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<ArtistEntity> data = new ArrayList<ArtistEntity>();

    private List<PaintworkEntity> paintworksData = new ArrayList();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ArtistEntity.class.getPackage())
                .addPackage(PaintworkLogic.class.getPackage())
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
    private void clearData()
    {
        em.createQuery("delete from PaintworkEntity").executeUpdate();
        em.createQuery("delete from ArtistEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() 
    {
        for (int i = 0; i < 3; i++) 
        {
            PaintworkEntity Paintworks = factory.manufacturePojo(PaintworkEntity.class);
            em.persist(Paintworks);
            paintworksData.add(Paintworks);
        }
        for (int i = 0; i < 3; i++) 
        {
            ArtistEntity entity = factory.manufacturePojo(ArtistEntity.class);
            em.persist(entity);
            data.add(entity);
            if (i == 0) 
            {
                paintworksData.get(i).setArtist(entity);
            }
        }
    }

    /**
     * Prueba para remplazar las instancias de Paintworks asociadas a una instancia
     * de Artist.
     */
    @Test
    public void replaceArtistTest()
    {
        PaintworkEntity entity = paintworksData.get(0);
        paintworkArtistLogic.replaceArtist(entity.getId(), data.get(1).getId());
        entity = paintworkLogic.getPaintWork(entity.getId());
        Assert.assertEquals(entity.getArtist(), data.get(1));
    }

    /**
     * Prueba para desasociar un Paintwork existente de un Artist existenteException
     * @throws co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException
     */
    @Test
    public void removePaintworksTest() throws BusinessLogicException
    {
        paintworkArtistLogic.removeArtist(paintworksData.get(0).getId());
        PaintworkEntity response = paintworkLogic.getPaintWork(paintworksData.get(0).getId());
        Assert.assertNull(response.getArtist());
    }
}

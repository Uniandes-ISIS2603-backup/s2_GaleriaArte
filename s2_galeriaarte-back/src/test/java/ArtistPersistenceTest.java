import co.edu.uniandes.csw.galeriaarte.entities.ArtistEntity;
import co.edu.uniandes.csw.galeriaarte.persistence.ArtistPersistence;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
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
 * @author Anderson Barragan
 */
@RunWith(Arquillian.class)
public class ArtistPersistenceTest {
    @Inject
    private ArtistPersistence artistPersistence;
    
    @PersistenceContext
    private EntityManager em;
    
    @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ArtistEntity.class.getPackage())
                .addPackage(ArtistPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");

    }
    
    @Test
    public void createArtistTest()
    {
        PodamFactory factory = new PodamFactoryImpl();
        ArtistEntity newEntity= factory.manufacturePojo(ArtistEntity.class);
        ArtistEntity result= artistPersistence.create(newEntity);
        Assert.assertNotNull(result);
        
        ArtistEntity entity= em.find(ArtistEntity.class, result.getId());
        Assert.assertEquals(newEntity.getName(), entity.getName());
    }
}

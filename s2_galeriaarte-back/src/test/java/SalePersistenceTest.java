
import co.edu.uniandes.csw.galeriaarte.entities.FeedBackEntity;
import co.edu.uniandes.csw.galeriaarte.entities.SaleEntity;
import co.edu.uniandes.csw.galeriaarte.persistence.FeedBackPersistence;
import co.edu.uniandes.csw.galeriaarte.persistence.SalePersistence;
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
 * @author s.restrepos1
 */
@RunWith(Arquillian.class)

public class SalePersistenceTest
{
   @Inject
    private SalePersistence salePersistence;
    
    @PersistenceContext
    private EntityManager em;
    
    @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(SaleEntity.class.getPackage())
                .addPackage(SalePersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");

    }
    
    @Test
    public void createFeedBackTest()
    {
        PodamFactory factory = new PodamFactoryImpl();
        SaleEntity newEntity= factory.manufacturePojo(SaleEntity.class);
        SaleEntity result= salePersistence.create(newEntity);
        Assert.assertNotNull(result);
        
        SaleEntity entity= em.find(SaleEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
    } 
}

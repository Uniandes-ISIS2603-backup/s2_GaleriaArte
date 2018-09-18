package co.edu.uniandes.csw.galeriaarte.test.persistence;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import co.edu.uniandes.csw.galeriaarte.entities.FeedBackEntity;
import co.edu.uniandes.csw.galeriaarte.persistence.FeedBackPersistence;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.Assert;
import uk.co.jemos.podam.api.PodamFactory;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author s.restrepos1
 */
@RunWith(Arquillian.class)
public class FeedBackPersistenceTest
{
    @Inject
    private FeedBackPersistence feedbackPersistence;
    
    @PersistenceContext
    private EntityManager em;
    
    @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(FeedBackEntity.class.getPackage())
                .addPackage(FeedBackPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");

    }
    
    @Test
    public void createFeedBackTest()
    {
        PodamFactory factory = new PodamFactoryImpl();
        FeedBackEntity newEntity= factory.manufacturePojo(FeedBackEntity.class);
        FeedBackEntity result= feedbackPersistence.create(newEntity);
        Assert.assertNotNull(result);
        
        FeedBackEntity entity= em.find(FeedBackEntity.class, result.getId());
        Assert.assertEquals(newEntity.getName(), entity.getName());
    }
    

}
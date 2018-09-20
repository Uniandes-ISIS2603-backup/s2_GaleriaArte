/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.test.persistence;
import co.edu.uniandes.csw.galeriaarte.entities.CVEntity;
import co.edu.uniandes.csw.galeriaarte.persistence.CVPersistence;
import java.util.ArrayList;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import java.util.List;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import co.edu.uniandes.csw.galeriaarte.persistence.ExtraServicePersistence;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.runner.RunWith;
/**
 *
 * @author LauraManrique
 */

@RunWith(Arquillian.class)
public class CVPersitenceTest {
    
     /**
     * Inyección de la dependencia a la clase CVPersistence cuyos métodos
     * se van a probar.
     */
    @Inject
    private CVPersistence cvPersistence;
    
    @PersistenceContext
    private EntityManager em;
    
        @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CVEntity.class.getPackage())
                .addPackage(CVPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");

    }
    
        @Test
    public void createFeedBackTest()
    {
        PodamFactory factory = new PodamFactoryImpl();
        CVEntity newEntity= factory.manufacturePojo(CVEntity.class);
        CVEntity result= cvPersistence.create(newEntity);
        Assert.assertNotNull(result);
        
        CVEntity entity= em.find(CVEntity.class, result.getId());
        Assert.assertEquals(newEntity.getName(), entity.getName());
    }
}

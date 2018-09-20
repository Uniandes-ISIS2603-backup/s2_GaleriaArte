package co.edu.uniandes.csw.galeriaarte.test.persistence;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import co.edu.uniandes.csw.galeriaarte.entities.KindEntity;
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
import co.edu.uniandes.csw.galeriaarte.persistence.KindPersistence;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.runner.RunWith;
/**
 *
 * @author LauraManrique
 */
@RunWith(Arquillian.class)
public class KindPersistenceTest {
    
        /**
     * Inyección de la dependencia a la clase KindPersistence cuyos métodos
     * se van a probar.
     */
    @Inject
    private KindPersistence kindPersistence;
    
    @PersistenceContext
    private EntityManager em;
    
        @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(KindEntity.class.getPackage())
                .addPackage(KindPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");

    }
    
        @Test
    public void createKindTest()
    {
        PodamFactory factory = new PodamFactoryImpl();
        KindEntity newEntity= factory.manufacturePojo(KindEntity.class);
        KindEntity result= kindPersistence.create(newEntity);
        Assert.assertNotNull(result);
        
        KindEntity entity= em.find(KindEntity.class, result.getId());
        Assert.assertEquals(newEntity.getName(), entity.getName());
    }
}

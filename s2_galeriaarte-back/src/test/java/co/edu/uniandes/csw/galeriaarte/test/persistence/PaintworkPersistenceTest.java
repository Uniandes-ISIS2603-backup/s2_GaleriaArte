/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.test.persistence;

import co.edu.uniandes.csw.galeriaarte.entities.PaintworkEntity;
import co.edu.uniandes.csw.galeriaarte.persistence.PaintworkPersistence;
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

/**
 *
 * @author jf.copete
 */
@RunWith(Arquillian.class)

public class PaintworkPersistenceTest {
    @Inject
    private PaintworkPersistence paintworkPersistence;
    
    @PersistenceContext
    private EntityManager em;
    
    @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(PaintworkEntity.class.getPackage())
                .addPackage(PaintworkPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");

    }
    
    @Test
    public void createFeedBackTest()
    {
        PodamFactory factory = new PodamFactoryImpl();
        PaintworkEntity newEntity= factory.manufacturePojo(PaintworkEntity.class);
        PaintworkEntity result= paintworkPersistence.create(newEntity);
        Assert.assertNotNull(result);
        
        PaintworkEntity entity= em.find(PaintworkEntity.class, result.getId());
        Assert.assertEquals(newEntity.getName(), entity.getName());
    }
    

}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.test.logic;

import co.edu.uniandes.csw.galeriaarte.ejb.KindLogic;
import co.edu.uniandes.csw.galeriaarte.entities.KindEntity;
import co.edu.uniandes.csw.galeriaarte.entities.PaintworkEntity;
import co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.galeriaarte.persistence.KindPersistence;
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
 *
 * @author LauraManrique
 */
@Stateless
public class KindLogicTest {
    
      private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private KindLogic kindLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<KindEntity> data = new ArrayList<>();
    
     /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(KindEntity.class.getPackage())
                .addPackage(KindLogic.class.getPackage())
                .addPackage(KindPersistence.class.getPackage())
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
        em.createQuery("delete from PaintworkEntityEntity").executeUpdate();
        em.createQuery("delete from KindEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            KindEntity entity = factory.manufacturePojo(KindEntity.class);
            em.persist(entity);
            PaintworkEntity obra =new PaintworkEntity();
            entity.setPaintwor(obra);
            data.add(entity);
        }
       
    }
    
    /**
     * Prueba para crear un kind.
     * @throws co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException
     */ 
    @Test
    public void createKindTest() throws BusinessLogicException 
    {
        KindEntity newEntity = factory.manufacturePojo(KindEntity.class);
        KindEntity result = kindLogic.createKind(newEntity);
        Assert.assertNotNull(result);
        KindEntity entity = em.find(KindEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getName(), entity.getName());
    }
    
    /**
     * Prueba para consultar la lista de kinds.
     */
    @Test
    public void getKindsTest() {
        List<KindEntity> list = kindLogic.getKInds();
        Assert.assertEquals(data.size(), list.size());
        for(KindEntity entity : list) {
            boolean found = false;
            for (KindEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
     /**
     * Prueba para consultar un kind.
     */
    @Test
    public void getKindTest() {
        KindEntity entity = data.get(0);
        KindEntity resultEntity = kindLogic.getKindV(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getName(), resultEntity.getName());
    }
    
     /**
     * Prueba para actualizar un kind.
     * @throws co.edu.uniandes.csw.galeriaarSte.exceptions.BusinessLogicException
     */
    @Test
    public void updateKIndTest() throws BusinessLogicException 
    {
        KindEntity entity = data.get(0);
        KindEntity pojoEntity = factory.manufacturePojo(KindEntity.class);

        pojoEntity.setId(entity.getId());

        kindLogic.updateKind(pojoEntity.getId(), pojoEntity);

        KindEntity resp = em.find(KindEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getName(), resp.getName());
    }
    /**
     * Prueba para eliminar un cv
     *
     * @throws co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException
     */
    @Test
    public void deleteKindTest() throws BusinessLogicException {
       KindEntity entity = data.get(0);
       kindLogic.deleteKind(entity.getId());
        KindEntity deleted = em.find(KindEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}

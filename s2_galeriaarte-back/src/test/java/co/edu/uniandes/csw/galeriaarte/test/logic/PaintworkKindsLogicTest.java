/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.test.logic;
import co.edu.uniandes.csw.galeriaarte.ejb.KindLogic;
import co.edu.uniandes.csw.galeriaarte.ejb.PaintworkKindsLogic;
import co.edu.uniandes.csw.galeriaarte.entities.KindEntity;
import co.edu.uniandes.csw.galeriaarte.entities.PaintworkEntity;
import co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.galeriaarte.persistence.PaintworkPersistence;
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
 * Pruebas de logica de la relacion paintwork - kinds
 *
 * @author ja.penat
 *
 */
@RunWith(Arquillian.class)
public class PaintworkKindsLogicTest
{
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private PaintworkKindsLogic paintworkKindLogic;

    @Inject
    private KindLogic kindLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private PaintworkEntity paintwork = new PaintworkEntity();
    private List<KindEntity> data = new ArrayList<>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(PaintworkEntity.class.getPackage())
                .addPackage(KindEntity.class.getPackage())
                .addPackage(PaintworkKindsLogic.class.getPackage())
                .addPackage(PaintworkPersistence.class.getPackage())
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
        em.createQuery("delete from KindEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() 
    {
        

        paintwork = factory.manufacturePojo(PaintworkEntity.class);
        paintwork.setId(1L);
        paintwork.setKind(new ArrayList<>());
        em.persist(paintwork);

        for (int i = 0; i < 3; i++) 
        {
            KindEntity entity = factory.manufacturePojo(KindEntity.class);
            entity.setObra(new ArrayList<>());
            entity.getObra().add(paintwork);
            em.persist(entity);
            data.add(entity);
            paintwork.getKind().add(entity);
        }
    }

    /**
     * Prueba para asociar un obra a un kind.
     *
     *
     * @throws co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException
     */
    @Test
    public void addKindTest() throws BusinessLogicException 
    {
        KindEntity newKind = factory.manufacturePojo(KindEntity.class);
        kindLogic.createKind(newKind);
        KindEntity kindEntity = paintworkKindLogic.addKind(paintwork.getId(), newKind.getId());
        Assert.assertNotNull(kindEntity);

        Assert.assertEquals(kindEntity.getId(), newKind.getId());
        Assert.assertEquals(kindEntity.getName(), newKind.getName());
        Assert.assertEquals(kindEntity.getDescription(), newKind.getDescription());
      

        KindEntity lastkind = paintworkKindLogic.getKind(paintwork.getId(), newKind.getId());

        Assert.assertEquals(lastkind.getId(), newKind.getId());
        Assert.assertEquals(lastkind.getName(), newKind.getName());
        Assert.assertEquals(lastkind.getDescription(), newKind.getDescription());
      
    }

    /**
     * Prueba para consultar la lista de kinds de un obra.
     */
    @Test
    public void getKindsTest() 
    {
        List<KindEntity> kindEntities = paintworkKindLogic.getKinds(paintwork.getId());

        Assert.assertEquals(data.size(), kindEntities.size());

        for (int i = 0; i < data.size(); i++) 
        {
            Assert.assertTrue(kindEntities.contains(data.get(0)));
        }
    }

    /**
     * Prueba para cpnsultar un kind de un obra.
     *
     * @throws co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException
     */
    @Test
    public void getkindTest() throws BusinessLogicException 
    {
        KindEntity kindEntity = data.get(0);
        KindEntity kind = paintworkKindLogic.getKind(paintwork.getId(), kindEntity.getId());
        Assert.assertNotNull(kind);

        Assert.assertEquals(kindEntity.getId(), kind.getId());
        Assert.assertEquals(kindEntity.getName(), kind.getName());
        Assert.assertEquals(kindEntity.getDescription(), kind.getDescription());
    }

    /**
     * Prueba para actualizar los kinds de un obra.
     *
     *
     * @throws co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException
     */
    @Test
    public void replacekindsTest() throws BusinessLogicException
    {
        List<KindEntity> nuevaLista = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            KindEntity entity = factory.manufacturePojo(KindEntity.class);
            entity.setObra(new ArrayList<>());
            entity.getObra().add(paintwork);
            kindLogic.createKind(entity);
            nuevaLista.add(entity);
        }
        paintworkKindLogic.replaceKinds(paintwork.getId(), nuevaLista);
        List<KindEntity> kindEntities = paintworkKindLogic.getKinds(paintwork.getId());
        for (KindEntity aNuevaLista : nuevaLista) {
            Assert.assertTrue(kindEntities.contains(aNuevaLista));
        }
    }

    /**
     * Prueba desasociar un kind con un obra.
     *
     */
    @Test
    public void removekindTest()
    {
        for (KindEntity kind : data) 
        {
            paintworkKindLogic.removeKind(paintwork.getId(), kind.getId());
        }
        Assert.assertTrue(paintworkKindLogic.getKinds(paintwork.getId()).isEmpty());
    }    
}

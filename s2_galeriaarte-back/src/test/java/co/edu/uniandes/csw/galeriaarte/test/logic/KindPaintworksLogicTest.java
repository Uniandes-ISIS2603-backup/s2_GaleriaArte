package co.edu.uniandes.csw.galeriaarte.test.logic;
import co.edu.uniandes.csw.galeriaarte.ejb.KindPaintworksLogic;
import co.edu.uniandes.csw.galeriaarte.ejb.PaintworkLogic;
import co.edu.uniandes.csw.galeriaarte.entities.KindEntity;
import co.edu.uniandes.csw.galeriaarte.entities.PaintworkEntity;
import co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.galeriaarte.persistence.KindPersistence;
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
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import java.util.ArrayList;
import java.util.List;

/**
 * Pruebas de logica de la relacion Kind - Paintworks
 *
 * @author ja.penat
 */
@RunWith(Arquillian.class)
public class KindPaintworksLogicTest
{
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private KindPaintworksLogic kindPaintworkLogic;
    
    @Inject
    private PaintworkLogic paintworkLogic;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;
    
    private KindEntity Kind = new KindEntity();
    private List<PaintworkEntity> data = new ArrayList<>();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(KindEntity.class.getPackage())
                .addPackage(PaintworkEntity.class.getPackage())
                .addPackage(KindPaintworksLogic.class.getPackage())
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
    private void clearData()
    {
        em.createQuery("delete from KindEntity").executeUpdate();
        em.createQuery("delete from PaintworkEntity").executeUpdate();
    }
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData()
    {
        
        Kind = factory.manufacturePojo(KindEntity.class);
        Kind.setId(1L);
        Kind.setObra(new ArrayList<>());
        em.persist(Kind);
        
        for (int i = 0; i < 3; i++)
        {
            PaintworkEntity entity = factory.manufacturePojo(PaintworkEntity.class);
            entity.setKind(new ArrayList<>());
            entity.getKind().add(Kind);
            em.persist(entity);
            data.add(entity);
            Kind.getObra().add(entity);
        }
    }
    
    /**
     * Prueba para asociar un tipo a un obra.
     *
     *
     * @throws co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException
     */
    @Test
    public void addPaintworkTest() throws BusinessLogicException
    {
        PaintworkEntity newPaintwork = factory.manufacturePojo(PaintworkEntity.class);
        paintworkLogic.createPaintWork(newPaintwork);
        PaintworkEntity paintworkEntity = kindPaintworkLogic.addPaintwork(Kind.getId(), newPaintwork.getId());
        Assert.assertNotNull(paintworkEntity);
        
        Assert.assertEquals(paintworkEntity.getId(), newPaintwork.getId());
        Assert.assertEquals(paintworkEntity.getName(), newPaintwork.getName());
        Assert.assertEquals(paintworkEntity.getDescription(), newPaintwork.getDescription());
        
        Assert.assertEquals(paintworkEntity.getCountry(), newPaintwork.getCountry());
        Assert.assertEquals(paintworkEntity.getValor(), newPaintwork.getValor());
        Assert.assertEquals(paintworkEntity.getVerificacionObra(), newPaintwork.getVerificacionObra() );
        Assert.assertEquals(paintworkEntity.getImagePath(), newPaintwork.getImagePath());
        Assert.assertEquals(paintworkEntity.getVideoPath(), newPaintwork.getVideoPath());
        
        PaintworkEntity lastPaintwork = kindPaintworkLogic.getPaintwork(Kind.getId(), newPaintwork.getId());
        
        Assert.assertEquals(lastPaintwork.getId(), newPaintwork.getId());
        Assert.assertEquals(lastPaintwork.getName(), newPaintwork.getName());
        Assert.assertEquals(lastPaintwork.getDescription(), newPaintwork.getDescription());
        
        Assert.assertEquals(lastPaintwork.getCountry(), newPaintwork.getCountry());
        Assert.assertEquals(lastPaintwork.getValor(), newPaintwork.getValor());
        Assert.assertEquals(lastPaintwork.getVerificacionObra(), newPaintwork.getVerificacionObra() );
        Assert.assertEquals(lastPaintwork.getImagePath(), newPaintwork.getImagePath());
        Assert.assertEquals(lastPaintwork.getVideoPath(), newPaintwork.getVideoPath());
    }
    
    /**
     * Prueba para consultar la lista de Paintworks de un tipo.
     */
    @Test
    public void getPaintworksTest()
    {
        List<PaintworkEntity> PaintworkEntities = kindPaintworkLogic.getPaintworks(Kind.getId());
        
        Assert.assertEquals(data.size(), PaintworkEntities.size());
        
        for (int i = 0; i < data.size(); i++) {
            Assert.assertTrue(PaintworkEntities.contains(data.get(0)));
        }
    }
    
    /**
     * Prueba para cpnsultar un obra de un tipo.
     *
     * @throws co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException
     */
    @Test
    public void getPaintworkTest() throws BusinessLogicException 
    {
        PaintworkEntity paintworkEntity = data.get(0);
        PaintworkEntity paintwork = kindPaintworkLogic.getPaintwork(Kind.getId(), paintworkEntity.getId());
        Assert.assertNotNull(paintwork);
        
        Assert.assertEquals(paintworkEntity.getId(), paintwork.getId());
    
        Assert.assertEquals(paintworkEntity.getName(), paintwork.getName());
        Assert.assertEquals(paintworkEntity.getDescription(), paintwork.getDescription());
         Assert.assertEquals(paintworkEntity.getCountry(), paintwork.getCountry());
         Assert.assertEquals(paintworkEntity.getValor(), paintwork.getValor());
           Assert.assertEquals(paintworkEntity.getVerificacionObra(), paintwork.getVerificacionObra() );
           Assert.assertEquals(paintworkEntity.getImagePath(), paintwork.getImagePath());
           Assert.assertEquals(paintworkEntity.getVideoPath(), paintwork.getVideoPath());
     
    }
    
    /**
     * Prueba para actualizar los obras de un tipo.
     *
     * @throws co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException
     */
    @Test
    public void replacePaintworksTest() throws BusinessLogicException 
    {
        List<PaintworkEntity> nuevaLista = new ArrayList<>();
        for (int i = 0; i < 3; i++) 
        {
            PaintworkEntity entity = factory.manufacturePojo(PaintworkEntity.class);
            entity.setKind(new ArrayList<>());
            entity.getKind().add(Kind);
            paintworkLogic.createPaintWork(entity);
            nuevaLista.add(entity);
        }
        kindPaintworkLogic.replacePaintworks(Kind.getId(), nuevaLista);
        List<PaintworkEntity> paintworksEntities = kindPaintworkLogic.getPaintworks(Kind.getId());
        for (PaintworkEntity aNuevaLista : nuevaLista) 
        {
            Assert.assertTrue(paintworksEntities.contains(aNuevaLista));
        }
    }
    
    /**
     * Prueba desasociar un obra con un tipo.
     *
     */
    @Test
    public void removePaintworksTest() 
    {
        for (PaintworkEntity paintworks : data)
        {
            kindPaintworkLogic.removePaintwork(Kind.getId(), paintworks.getId());
        }
        Assert.assertTrue(kindPaintworkLogic.getPaintworks(Kind.getId()).isEmpty());
    }
}

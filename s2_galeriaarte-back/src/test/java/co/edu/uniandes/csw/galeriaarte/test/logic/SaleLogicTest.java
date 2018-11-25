/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.test.logic;
import co.edu.uniandes.csw.galeriaarte.ejb.SaleLogic;
import co.edu.uniandes.csw.galeriaarte.entities.ArtistEntity;
import co.edu.uniandes.csw.galeriaarte.entities.BuyerEntity;
import co.edu.uniandes.csw.galeriaarte.entities.CVEntity;
import co.edu.uniandes.csw.galeriaarte.entities.PaintworkEntity;
import co.edu.uniandes.csw.galeriaarte.entities.MedioPagoEntity;
import co.edu.uniandes.csw.galeriaarte.entities.SaleEntity;
import co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.galeriaarte.persistence.SalePersistence;
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
 *
 * @author s.restrepos1
 */
@RunWith(Arquillian.class)
public class SaleLogicTest 
{
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private SaleLogic saleLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;
    
        private List<SaleEntity> data;
        private List<ArtistEntity> artistData ;
        private List<BuyerEntity> buyerData ;
        private List<PaintworkEntity> paintworkData ;
        


     /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(SaleEntity.class.getPackage())
                .addPackage(SaleLogic.class.getPackage())
                .addPackage(SalePersistence.class.getPackage())
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
        em.createQuery("delete from CVEntity").executeUpdate();
    }
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        
        data = new ArrayList<>();
        artistData = new ArrayList<>();
        buyerData = new ArrayList<>();
        paintworkData = new ArrayList<>();
        for(int i = 0; i<3 ; i ++)
        {
            ArtistEntity entityA = factory.manufacturePojo(ArtistEntity.class);
            em.persist(entityA);
            artistData.add(entityA);
            BuyerEntity entityB = factory.manufacturePojo(BuyerEntity.class);
            em.persist(entityB);
            buyerData.add(entityB);
            PaintworkEntity entityP = factory.manufacturePojo(PaintworkEntity.class);
            em.persist(entityP);
            paintworkData.add(entityP);

            
        }
        for (int i = 0; i < 3; i++)
        {
            SaleEntity entity = factory.manufacturePojo(SaleEntity.class);
            entity.setArtist(artistData.get(i));
            entity.setObra(paintworkData.get(i));
            entity.setBuyer(buyerData.get(i));
            em.persist(entity);
            data.add(entity);
        }
  
    }
    
    /**
     * Prueba para crear una compra.
     * @throws co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException
     */ 
    @Test
    public void createSaleTest() throws BusinessLogicException 
    {
        SaleEntity newEntity = factory.manufacturePojo(SaleEntity.class);
        SaleEntity  result = saleLogic.createSale(newEntity,artistData.get(0).getId(),buyerData.get(0).getId(), paintworkData.get(0).getId());
        
        Assert.assertNotNull(result);
        SaleEntity entity = em.find(SaleEntity.class, result.getId());
        Assert.assertNotNull(result);
        Assert.assertEquals(newEntity.getId(), entity.getId());
    }
   
    @Test
    public void getSaleTest() {
        SaleEntity entity = data.get(0);
        SaleEntity resultEntity = saleLogic.getSale(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getId(), resultEntity.getId());
    }


     @Test
    public void updateSaleTest()
    {
        SaleEntity entity = data.get(0);
        SaleEntity pojoEntity = factory.manufacturePojo(SaleEntity.class);
        
        
        pojoEntity.setId(entity.getId());
        
        saleLogic.updateSale(pojoEntity.getId(), pojoEntity);
        
        SaleEntity resultEntity = em.find(SaleEntity.class, entity.getId());
        
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(pojoEntity.getId(), resultEntity.getId());
        
    }
    
    @Test
    public void deleteSaleTest() throws BusinessLogicException
    {
        SaleEntity entity = data.get(0);
        saleLogic.deleteSale(entity.getId());
        SaleEntity deleted = em.find(SaleEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
}

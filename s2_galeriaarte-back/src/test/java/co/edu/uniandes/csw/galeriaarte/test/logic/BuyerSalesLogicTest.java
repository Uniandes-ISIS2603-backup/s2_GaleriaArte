/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.test.logic;

import co.edu.uniandes.csw.galeriaarte.ejb.BuyerLogic;
import co.edu.uniandes.csw.galeriaarte.ejb.BuyerSalesLogic;
import co.edu.uniandes.csw.galeriaarte.entities.BuyerEntity;
import co.edu.uniandes.csw.galeriaarte.entities.SaleEntity;
import co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.galeriaarte.persistence.BuyerPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.junit.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * Pruebas de logica de la relacion Buyer - Sales
 *
 * @author s.acostav
 */
@RunWith(Arquillian.class)
public class BuyerSalesLogicTest
{
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private BuyerLogic buyerLogic;
    @Inject
    private BuyerSalesLogic buyerSalesLogic;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;
    
    private List<BuyerEntity> data = new ArrayList<BuyerEntity>();
    
    private List<SaleEntity> salesData = new ArrayList();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(BuyerEntity.class.getPackage())
                .addPackage(BuyerLogic.class.getPackage())
                .addPackage(BuyerPersistence.class.getPackage())
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
        em.createQuery("delete from SaleEntity").executeUpdate();
        em.createQuery("delete from BuyerEntity").executeUpdate();
    }
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData()
    {
        for (int i = 0; i < 4; i++)
        {
            SaleEntity sales = factory.manufacturePojo(SaleEntity.class);
            em.persist(sales);
            salesData.add(sales);
        }
        for (int i = 0; i < 3; i++)
        {
            BuyerEntity entity = factory.manufacturePojo(BuyerEntity.class);
            em.persist(entity);
            data.add(entity); 
             salesData.get(i).setBuyer(entity);
             
        }
    }
    
    /**
     * Prueba para asociar un Sales existente a un Buyer.
     */
    @Test
    public void addSalesTest()
    {
        BuyerEntity entity = data.get(0);
        SaleEntity saleEntity = salesData.get(1);
        SaleEntity response = buyerSalesLogic.addSale(saleEntity.getId(), entity.getId());
        
        Assert.assertNotNull(response);
        Assert.assertEquals(saleEntity.getId(), response.getId());
    }
    
    /**
     * Prueba para obtener una colección de instancias de Sales asociadas a una
     * instancia Buyer.
     */
    @Test
    public void getSalesTest()
    {
        List<SaleEntity> list = buyerSalesLogic.getSales(data.get(0).getId());
        Assert.assertEquals(1, list.size());
    }
    
    /**
     * Prueba para obtener una instancia de Sales asociada a una instancia
     * Buyer.
     *
     * @throws co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException
     */
    @Test
    public void getSaleTest() throws BusinessLogicException
    {
        BuyerEntity entity = data.get(0);
        SaleEntity saleEntity = salesData.get(0);
        SaleEntity response = buyerSalesLogic.getSale(entity.getId(), saleEntity.getId());
        
        Assert.assertEquals(saleEntity.getId(), response.getId());
        Assert.assertEquals(saleEntity.getPrice(), response.getPrice(),0.2);
        Assert.assertEquals(saleEntity.getDescription(), response.getDescription());
        Assert.assertEquals(response.getTaxes(), saleEntity.getTaxes(),0.2);
        
    }
    
    /**
     * Prueba para obtener una instancia de Sales asociada a una instancia
     * Buyer que no le pertenece.
     *
     * @throws co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void getSaleNoAsociadoTest() throws BusinessLogicException 
    {
        BuyerEntity entity = data.get(0);
        SaleEntity saleEntity = salesData.get(1);
        buyerSalesLogic.getSale(entity.getId(), saleEntity.getId());
    }
    
    /**
     * Prueba para remplazar las instancias de Sales asociadas a una instancia
     * de Buyer.
     */
    @Test
    public void replaceSalesTest() 
    {
        BuyerEntity entity = data.get(0);
        List<SaleEntity> list = salesData.subList(1, 3);
        buyerSalesLogic.replaceSales(entity.getId(), list);
        
        entity = buyerLogic.getBuyer(entity.getId());
        Assert.assertFalse(entity.getSales().contains(salesData.get(0)));
        Assert.assertTrue(entity.getSales().contains(salesData.get(1)));
        Assert.assertTrue(entity.getSales().contains(salesData.get(2)));
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.test.logic;

import co.edu.uniandes.csw.galeriaarte.ejb.SaleLogic;
import co.edu.uniandes.csw.galeriaarte.ejb.SaleExtraServicesLogic;
import co.edu.uniandes.csw.galeriaarte.entities.SaleEntity;
import co.edu.uniandes.csw.galeriaarte.entities.ExtraServiceEntity;
import co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.galeriaarte.persistence.SalePersistence;
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
 * Pruebas de logica de la relacion Sale - ExtraServices
 *
 * @author s.acostav
 */
@RunWith(Arquillian.class)
public class SaleExtraServicesLogicTest
{
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private SaleLogic saleLogic;
    @Inject
    private SaleExtraServicesLogic saleExtraServicesLogic;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;
    
    private List<SaleEntity> data = new ArrayList<SaleEntity>();
    
    private List<ExtraServiceEntity> extraServicesData = new ArrayList();
    
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
        em.createQuery("delete from ExtraServiceEntity").executeUpdate();
        em.createQuery("delete from SaleEntity").executeUpdate();
    }
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData()
    {
        for (int i = 0; i < 4; i++)
        {
            ExtraServiceEntity extraServices = factory.manufacturePojo(ExtraServiceEntity.class);
            em.persist(extraServices);
            extraServicesData.add(extraServices);
        }
        for (int i = 0; i < 3; i++)
        {
            SaleEntity entity = factory.manufacturePojo(SaleEntity.class);
            em.persist(entity);
            data.add(entity); 
            List<SaleEntity> s =  extraServicesData.get(i).getSale();
            s.add(entity);
             extraServicesData.get(i).setSale(s);
        }
    }
    
    /**
     * Prueba para asociar un ExtraServices existente a un Sale.
     */
    @Test
    public void addExtraServicesTest()
    {
        SaleEntity entity = data.get(0);
        ExtraServiceEntity extraServiceEntity = extraServicesData.get(1);
        ExtraServiceEntity response = saleExtraServicesLogic.addExtraService(extraServiceEntity.getId(), entity.getId());
        
        Assert.assertNotNull(response);
        Assert.assertEquals(extraServiceEntity.getId(), response.getId());
    }
    
    /**
     * Prueba para obtener una colección de instancias de ExtraServices asociadas a una
     * instancia Sale.
     */
    @Test
    public void getExtraServicesTest()
    {
        List<ExtraServiceEntity> list = saleExtraServicesLogic.getExtraServices(data.get(0).getId());
        Assert.assertEquals(1, list.size());
    }
    
    /**
     * Prueba para obtener una instancia de ExtraServices asociada a una instancia
     * Sale.
     *
     * @throws co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException
     */
    @Test
    public void getExtraServiceTest() throws BusinessLogicException
    {
        SaleEntity entity = data.get(0);
        ExtraServiceEntity extraServiceEntity = extraServicesData.get(0);
        ExtraServiceEntity response = saleExtraServicesLogic.getExtraService(entity.getId(), extraServiceEntity.getId());
        
        Assert.assertEquals(extraServiceEntity.getId(), response.getId());
        Assert.assertEquals(extraServiceEntity.getName(), response.getName());
        Assert.assertEquals(extraServiceEntity.getDescription(), response.getDescription());
        Assert.assertEquals(response.getPrice(), extraServiceEntity.getPrice(),0.2);
        
    }
    
    /**
     * Prueba para obtener una instancia de ExtraServices asociada a una instancia
     * Sale que no le pertenece.
     *
     * @throws co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void getExtraServiceNoAsociadoTest() throws BusinessLogicException 
    {
        SaleEntity entity = data.get(0);
        ExtraServiceEntity extraServiceEntity = extraServicesData.get(1);
        saleExtraServicesLogic.getExtraService(entity.getId(), extraServiceEntity.getId());
    }
    
    /**
     * Prueba para remplazar las instancias de ExtraServices asociadas a una instancia
     * de Sale.
     */
    @Test
    public void replaceExtraServicesTest() 
    {
        SaleEntity entity = data.get(0);
        List<ExtraServiceEntity> list = extraServicesData.subList(1, 3);
        saleExtraServicesLogic.replaceExtraServices(entity.getId(), list);
        
        entity = saleLogic.getSale(entity.getId());
        Assert.assertFalse(entity.getServices().contains(extraServicesData.get(0)));
        Assert.assertTrue(entity.getServices().contains(extraServicesData.get(1)));
        Assert.assertTrue(entity.getServices().contains(extraServicesData.get(2)));
    }
}

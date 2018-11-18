/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package co.edu.uniandes.csw.galeriaarte.test.persistence;

import co.edu.uniandes.csw.galeriaarte.entities.CategoryEntity;
import co.edu.uniandes.csw.galeriaarte.persistence.CategoryPersistence;
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
 * @author jf.copete (solo metodo create) y ja.penat
 */
@RunWith(Arquillian.class)
public class CategoryPersistenceTest
{
    /**
     * Inyección de la dependencia a la clase CategoryPersistence cuyos métodos
     * se van a probar.
     */
    @Inject
    private CategoryPersistence categoryPersistence;
    
    /**
     * Contexto de Persistencia que se va a utilizar para acceder a la Base de
     * datos por fuera de los métodos que se están probando.
     */
    @PersistenceContext
    private EntityManager em;
    
    /**
     * Variable para martcar las transacciones del em anterior cuando se
     * crean/borran datos para las pruebas.
     */
    @Inject
            UserTransaction utx;
    
    /**
     * lista que tiene los datos de prueba.
     */
    private List<CategoryEntity> data = new ArrayList<CategoryEntity>();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en el Glassfish
     * embebido. El jar contiene las clases de Category, el descriptor de la
     * base de datos y el archivo beans.xml para resolver la inyección de
     * dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CategoryEntity.class.getPackage())
                .addPackage(CategoryPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    /**
     * Configuración inicial de la prueba.
     */
    @Before
    public void configTest()
    {
        try {
            utx.begin();
            em.joinTransaction();
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
        em.createQuery("delete from CategoryEntity").executeUpdate();
    }
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData()
    {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) 
        {    
            CategoryEntity entity = factory.manufacturePojo(CategoryEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
    
    /**
     * Prueba para crear una categoria.
     */
    @Test
    public void createCategoryTest()
    {
        PodamFactory factory = new PodamFactoryImpl();
        CategoryEntity newEntity = factory.manufacturePojo(CategoryEntity.class);
        CategoryEntity result = categoryPersistence.create(newEntity);
        
        Assert.assertNotNull(result);
        
        CategoryEntity entity = em.find(CategoryEntity.class, result.getId());
        
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getName(), entity.getName());
        Assert.assertEquals(newEntity.getDescription(), entity.getDescription());
    }
    
    /**
     * Prueba para consultar la lista de Categorias.
     */
    @Test
    public void getCategoriesTest()
    {
        List<CategoryEntity> list =  categoryPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (CategoryEntity ent : list)
        {
            boolean found = false;
            for (CategoryEntity entity : data)
            {
                if (ent.getId().equals(entity.getId()))
                {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    /**
     * Prueba para consultar una Categoria.
     */
    @Test
    public void getCategoryTest()
    {
        CategoryEntity entity = data.get(0);
        CategoryEntity newEntity = categoryPersistence.find(entity.getId());
        
        Assert.assertNotNull(newEntity);
        
        Assert.assertEquals(entity.getId(), newEntity.getId());
        Assert.assertEquals(newEntity.getName(), entity.getName());
        Assert.assertEquals(newEntity.getDescription(), entity.getDescription());
    }
    
    /**
     * Prueba para eliminar un Categoria.
     */
    @Test
    public void deleteCategoryTest()
    {
        CategoryEntity entity = data.get(0);
        categoryPersistence.delete(entity.getId());
        CategoryEntity deleted = em.find(CategoryEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
    /**
     * Prueba para actualizar un Category.
     */
    @Test
    public void updateCategoryTest()
    {
        CategoryEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        CategoryEntity newEntity = factory.manufacturePojo(CategoryEntity.class);
        
        newEntity.setId(entity.getId());
        
        categoryPersistence.update(newEntity);
        
        CategoryEntity resp = em.find(CategoryEntity.class, entity.getId());
        
        Assert.assertEquals(newEntity.getId(), resp.getId());
        Assert.assertEquals(resp.getName(), newEntity.getName());
        Assert.assertEquals(resp.getDescription(), newEntity.getDescription());
    }
}

/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package co.edu.uniandes.csw.galeriaarte.test.logic;
import co.edu.uniandes.csw.galeriaarte.ejb.ArtistLogic;
import co.edu.uniandes.csw.galeriaarte.entities.ArtistEntity;
import co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.galeriaarte.persistence.ArtistPersistence;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
 *Pruebas de logica de artist
 * @author a.barragan Anderson Barragán
 */
@RunWith(Arquillian.class)
public class ArtistLogicTest
{
    private final PodamFactory factory = new PodamFactoryImpl();
    
    @Inject private ArtistLogic artistLogic;
    
    @PersistenceContext private EntityManager em;
    
    @Inject private UserTransaction utx;
    
    private final List<ArtistEntity> data = new ArrayList<>();
    
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ArtistEntity.class.getPackage())
                .addPackage(ArtistLogic.class.getPackage())
                .addPackage(ArtistPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    /**
     * Configuración inicial de la prueba.
     */
    @Before public void configTest(){
        try {
            utx.begin();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e) {
            try {   utx.rollback(); }
            catch (Exception e1) { e1.printStackTrace(); }
             e.printStackTrace();
        }
    }
    
    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData(){
        em.createQuery("delete from ArtistEntity").executeUpdate();
    }
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData()  {
        for (int i = 0; i < 3; i++) {
            ArtistEntity entity = factory.manufacturePojo(ArtistEntity.class);
            
            em.persist(entity);//añade el artista a la base de datos
            data.add(entity);//añade el artista a la lista de datos
        }
    }
    
    /**
     * Prueba para la creacion de un artista
     * @throws BusinessLogicException  en caso de que se intenten
     * romper las reglas del negocio
     */
    @Test
    public void createArtistTest() throws BusinessLogicException 
    {
        ArtistEntity newEntity = factory.manufacturePojo(ArtistEntity.class);
        Random r = new Random();
        long newId= r.nextLong();
        newEntity.setId(newId);
        ArtistEntity result = artistLogic.createArtist(newEntity);
       
        Assert.assertNotNull(result);
        
        ArtistEntity entity = em.find(ArtistEntity.class, result.getId());
        Assert.assertEquals(newEntity.getName(), entity.getName());
        Assert.assertEquals(newEntity.getBirthDate(), entity.getBirthDate());
        Assert.assertEquals(newEntity.getDescription(), entity.getDescription());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getImage(), entity.getImage());
    }
    
    /**
     * Prueba para consultar la lista de los artistas en la base de datos.
     */
    @Test
    public void getArtistsTest()
    {
        List<ArtistEntity> list = artistLogic.getArtists();
        Assert.assertEquals(data.size(), list.size());
        for (ArtistEntity entity : list){
            boolean found = false;
            for (ArtistEntity storedEntity : data)
                if (entity.getId().equals(storedEntity.getId()))
                    found = true;
            Assert.assertTrue(found);
        }
    }
    
    /**
     * Prueba para consultar un artista.
     */
    @Test
    public void getArtistTest()
    {
        ArtistEntity entity = data.get(0);
        ArtistEntity resultEntity = artistLogic.getArtist(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getName(), resultEntity.getName());
        Assert.assertEquals(entity.getDescription(), resultEntity.getDescription());
         Assert.assertEquals(entity.getImage(), resultEntity.getImage());
         Assert.assertEquals(entity.getBirthDate(), resultEntity.getBirthDate());
    }
    
    /**
     * Prueba para actualizar un Artist.
     * @throws BusinessLogicException en caso de que se intenten romper las
     * reglas del negocio
     */
    @Test
    public void updateArtistTest() throws BusinessLogicException
    {
        ArtistEntity entity = data.get(0);
        ArtistEntity pojoEntity = factory.manufacturePojo(ArtistEntity.class);
        
        pojoEntity.setId(entity.getId());
        pojoEntity.setName(entity.getName());
        artistLogic.updateArtist(pojoEntity);
        
        ArtistEntity resp = em.find(ArtistEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getName(), resp.getName());
        Assert.assertEquals(pojoEntity.getDescription(), resp.getDescription());
              Assert.assertEquals(pojoEntity.getImage(), resp.getImage());
         Assert.assertEquals(pojoEntity.getBirthDate(), resp.getBirthDate());
        
        
    }
    
    /**
     * Prueba para eliminar artista.
     */
    @Test
    public void deleteArtistTest() 
    {
        ArtistEntity entity = data.get(0);
        artistLogic.deleteArtist(entity.getId());
        ArtistEntity deleted = em.find(ArtistEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}




/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package co.edu.uniandes.csw.galeriaarte.test.logic;

import co.edu.uniandes.csw.galeriaarte.ejb.ArtistLogic;
import co.edu.uniandes.csw.galeriaarte.ejb.ArtistPaintworksLogic;
import co.edu.uniandes.csw.galeriaarte.entities.ArtistEntity;
import co.edu.uniandes.csw.galeriaarte.entities.PaintworkEntity;
import co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.galeriaarte.persistence.ArtistPersistence;
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
 * Pruebas de logica de la relacion Artist - Paintworks
 *
 * @author ja.penat
 */
@RunWith(Arquillian.class)
public class ArtistPaintworksLogicTest
{
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private ArtistLogic artistLogic;
    @Inject
    private ArtistPaintworksLogic artistPaintworksLogic;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;
    
    private List<ArtistEntity> data = new ArrayList<ArtistEntity>();
    
    private List<PaintworkEntity> paintworksData = new ArrayList();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
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
        em.createQuery("delete from ArtistEntity").executeUpdate();
    }
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData()
    {
        for (int i = 0; i < 4; i++)
        {
            PaintworkEntity paintworks = factory.manufacturePojo(PaintworkEntity.class);
            em.persist(paintworks);
            paintworksData.add(paintworks);
        }
        for (int i = 0; i < 3; i++)
        {
            ArtistEntity entity = factory.manufacturePojo(ArtistEntity.class);
            em.persist(entity);
            data.add(entity); 
             paintworksData.get(i).setArtist(entity);
        }
    }
    
    /**
     * Prueba para asociar un Paintworks existente a un Artist.
     */
    @Test
    public void addPaintworksTest()
    {
        ArtistEntity entity = data.get(0);
        PaintworkEntity paintworkEntity = paintworksData.get(1);
        PaintworkEntity response = artistPaintworksLogic.addPaintwork(paintworkEntity.getId(), entity.getId());
        
        Assert.assertNotNull(response);
        Assert.assertEquals(paintworkEntity.getId(), response.getId());
    }
    
    /**
     * Prueba para obtener una colección de instancias de Paintworks asociadas a una
     * instancia Artist.
     */
    @Test
    public void getPaintworksTest()
    {
        List<PaintworkEntity> list = artistPaintworksLogic.getPaintworks(data.get(0).getId());
        Assert.assertEquals(1, list.size());
    }
    
    /**
     * Prueba para obtener una instancia de Paintworks asociada a una instancia
     * Artist.
     *
     * @throws co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException
     */
    @Test
    public void getPaintworkTest() throws BusinessLogicException
    {
        ArtistEntity entity = data.get(0);
        PaintworkEntity paintworkEntity = paintworksData.get(0);
        PaintworkEntity response = artistPaintworksLogic.getPaintwork(entity.getId(), paintworkEntity.getId());
        
        Assert.assertEquals(paintworkEntity.getId(), response.getId());
        Assert.assertEquals(paintworkEntity.getName(), response.getName());
        Assert.assertEquals(paintworkEntity.getDescription(), response.getDescription());
        Assert.assertEquals(response.getCountry(), paintworkEntity.getCountry());
        Assert.assertEquals(paintworkEntity.getValor(), response.getValor());
        Assert.assertEquals(paintworkEntity.getVerificacionObra(), response.getVerificacionObra() );
        Assert.assertEquals(paintworkEntity.getImagePath(), response.getImagePath());
        Assert.assertEquals(paintworkEntity.getVideoPath(), response.getVideoPath());
        
    }
    
    /**
     * Prueba para obtener una instancia de Paintworks asociada a una instancia
     * Artist que no le pertenece.
     *
     * @throws co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void getPaintworkNoAsociadoTest() throws BusinessLogicException 
    {
        ArtistEntity entity = data.get(0);
        PaintworkEntity paintworkEntity = paintworksData.get(1);
        artistPaintworksLogic.getPaintwork(entity.getId(), paintworkEntity.getId());
    }
    
    /**
     * Prueba para remplazar las instancias de Paintworks asociadas a una instancia
     * de Artist.
     */
    @Test
    public void replacePaintworksTest() 
    {
        ArtistEntity entity = data.get(0);
        List<PaintworkEntity> list = paintworksData.subList(1, 3);
        artistPaintworksLogic.replacePaintworks(entity.getId(), list);
        
        entity = artistLogic.getArtist(entity.getId());
        Assert.assertFalse(entity.getPaintworks().contains(paintworksData.get(0)));
        Assert.assertTrue(entity.getPaintworks().contains(paintworksData.get(1)));
        Assert.assertTrue(entity.getPaintworks().contains(paintworksData.get(2)));
    }
}


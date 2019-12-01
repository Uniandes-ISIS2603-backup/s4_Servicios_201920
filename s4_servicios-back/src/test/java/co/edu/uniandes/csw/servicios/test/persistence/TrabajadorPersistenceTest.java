/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.test.persistence;

import co.edu.uniandes.csw.servicios.entities.TrabajadorEntity;
import co.edu.uniandes.csw.servicios.persistence.TrabajadorPersistence;
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
 * @author c.otalora
 */

@RunWith(Arquillian.class)
public class TrabajadorPersistenceTest {
    
    @Inject
    private TrabajadorPersistence ep;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    UserTransaction utx;
    
     private List<TrabajadorEntity> data = new ArrayList<TrabajadorEntity>();
    
    @Deployment
    public static JavaArchive deployment (){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(TrabajadorEntity.class.getPackage())
                .addPackage(TrabajadorPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml","persistence.xml")
                .addAsManifestResource("META-INF/beans.xml","beans.xml");
    }
    
    @Before
    public void configTest() {
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
    
      private void clearData() {
        em.createQuery("delete from TrabajadorEntity").executeUpdate();
    }
    
     private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            TrabajadorEntity entity = factory.manufacturePojo(TrabajadorEntity.class);

            em.persist(entity);
            data.add(entity);
        }
    }
    
    @Test
    public void createTrabajadorTest(){
        PodamFactory factory = new PodamFactoryImpl();
        TrabajadorEntity newEntity = factory.manufacturePojo(TrabajadorEntity.class);
        
        TrabajadorEntity ee = ep.create(newEntity);
        
        Assert.assertNotNull("No crea nada", ee);
        
        TrabajadorEntity entity= em.find(TrabajadorEntity.class, ee.getId());
        
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
    }
    
    @Test
    public void getTrabajadoresTest() {
        List<TrabajadorEntity> list = ep.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (TrabajadorEntity ent : list) {
            boolean found = false;
            for (TrabajadorEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
     @Test
    public void getTrabajadorTest() {
        TrabajadorEntity newEntity = data.get(0);
        TrabajadorEntity resp = ep.find(newEntity.getId());
        
        Assert.assertEquals(newEntity.getCorreo(), resp.getCorreo());
        Assert.assertEquals(newEntity.getContrasena(), resp.getContrasena());
        Assert.assertEquals(newEntity.getNombre(), resp.getNombre());
        Assert.assertEquals(newEntity.getFoto(), resp.getFoto());
        Assert.assertEquals(newEntity.getTelefono(), resp.getTelefono());
        Assert.assertEquals(newEntity.getUsuario(), resp.getUsuario());
        Assert.assertEquals(newEntity.getSeguroSocial(), resp.getSeguroSocial());
        Assert.assertEquals(newEntity.getFoto(),resp.getFoto());
        Assert.assertEquals(newEntity.getRiesgos(), resp.getRiesgos());
    }
    
      @Test
    public void updateTrabajadorTest() {
        TrabajadorEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        TrabajadorEntity newEntity = factory.manufacturePojo(TrabajadorEntity.class);

        newEntity.setId(entity.getId());

         ep.update(newEntity);

        TrabajadorEntity resp = em.find(TrabajadorEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getCorreo(), resp.getCorreo());
        Assert.assertEquals(newEntity.getContrasena(), resp.getContrasena());
        Assert.assertEquals(newEntity.getNombre(), resp.getNombre());
        Assert.assertEquals(newEntity.getFoto(), resp.getFoto());
        Assert.assertEquals(newEntity.getTelefono(), resp.getTelefono());
        Assert.assertEquals(newEntity.getUsuario(), resp.getUsuario());
        Assert.assertEquals(newEntity.getSeguroSocial(), resp.getSeguroSocial());
        Assert.assertEquals(newEntity.getFoto(),resp.getFoto());
        Assert.assertEquals(newEntity.getRiesgos(), resp.getRiesgos());
    
    }
    
     @Test
    public void deleteTrabajadorTest() {
        TrabajadorEntity entity = data.get(0);
        ep.delete(entity.getId());
        TrabajadorEntity deleted = em.find(TrabajadorEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
    /**
     * Prueba para consultasr un Trabajador por usuario.
     */
    @Test
    public void findTrabajadorByUsuarioTest() {
        TrabajadorEntity entity = data.get(0);
        TrabajadorEntity newEntity = ep.findByUsuario(entity.getUsuario());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getUsuario(), newEntity.getUsuario());

        newEntity = ep.findByUsuario(null);
        Assert.assertNull(newEntity);
    }
    
}

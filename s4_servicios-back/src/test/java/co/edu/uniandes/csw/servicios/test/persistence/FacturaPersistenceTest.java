/*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.test.persistence;

import co.edu.uniandes.csw.servicios.entities.FacturaEntity;
import co.edu.uniandes.csw.servicios.persistence.FacturaPersistence;
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
 *
 * @author Juan Lucas Ibarra
 */
@RunWith(Arquillian.class)
public class FacturaPersistenceTest {

    @Inject
    private FacturaPersistence fp;

    @PersistenceContext
    private EntityManager fm;

    private List<FacturaEntity> data = new ArrayList<>();

    @Inject
    UserTransaction utx;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(FacturaEntity.class.getPackage())
                .addPackage(FacturaPersistence.class.getPackage())
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
            fm.joinTransaction();
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
        fm.createQuery("delete from FacturaEntity").executeUpdate();
    }

    public void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            FacturaEntity entity = factory.manufacturePojo(FacturaEntity.class);

            fm.persist(entity);
            data.add(entity);
        }
    }

    @Test
    public void createFacturaTest() {
        PodamFactory factory = new PodamFactoryImpl();
        FacturaEntity newFacturaEntity = factory.manufacturePojo(FacturaEntity.class);
        FacturaEntity fe = fp.create(newFacturaEntity);
        Assert.assertNotNull(fe);
        FacturaEntity entity = fm.find(FacturaEntity.class, fe.getId());
        Assert.assertEquals(newFacturaEntity.getDuracion(), entity.getDuracion());
    }

    @Test
    public void getFacturasTest() {
        List<FacturaEntity> list = fp.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (FacturaEntity entity : list) {
            boolean seEncontro = false;
            for (FacturaEntity entity2 : data) {
                if (entity.getId().equals(entity2.getId())) {
                    seEncontro = true;
                }
            }
            Assert.assertTrue(seEncontro);
        }
    }

    @Test
    public void getFacturaTest() {
        FacturaEntity entity = data.get(0);
        FacturaEntity entity2 = fp.find(entity.getId());
        Assert.assertNotNull(entity2);
        Assert.assertEquals(entity.getId(), entity2.getId());
        Assert.assertEquals(entity.getDuracion(), entity2.getDuracion());
    }

    @Test
    public void deleteFacturaTest(){
        FacturaEntity entity = data.get(0);
        fp.remove(entity.getId());
        FacturaEntity entity2 = fm.find(FacturaEntity.class, entity.getId());
        Assert.assertNull(entity2);
    }
      
         @Test
    public void updateServicioOfrecidoTest()
    {
        PodamFactory factory= new PodamFactoryImpl();
        
        FacturaEntity entity = factory.manufacturePojo(FacturaEntity.class);
        FacturaEntity newEntity = factory.manufacturePojo(FacturaEntity.class);
        
       entity= fp.create(entity);
        
        newEntity= fp.update(newEntity);
        
       FacturaEntity entitySearch = fm.find(FacturaEntity.class, newEntity.getId());  
       Assert.assertNotNull(entitySearch);
      // Assert.assertNull(em.find(ServicioOfrecidoEntity.class, entity.getId()));
       
       Assert.assertEquals(newEntity.getId(), entitySearch.getId());
       Assert.assertEquals(newEntity.getDuracion(), entitySearch.getDuracion());
        
        
    }       
            
    


}

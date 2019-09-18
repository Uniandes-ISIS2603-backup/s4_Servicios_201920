/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.test.persistence;

import co.edu.uniandes.csw.servicios.entities.PagoTarjetaEntity;
import co.edu.uniandes.csw.servicios.persistence.PagoTarjetaPersistence;
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
 * @author Juan Lucas Ibarra
 */
@RunWith(Arquillian.class)
public class PagoTarjetaPersistenceTest {
    
    @Inject
    private PagoTarjetaPersistence fp;

    @PersistenceContext
    private EntityManager fm;

    private List<PagoTarjetaEntity> data = new ArrayList<>();

    @Inject
    UserTransaction utx;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(PagoTarjetaEntity.class.getPackage())
                .addPackage(PagoTarjetaPersistence.class.getPackage())
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
        fm.createQuery("delete from PagoTarjetaEntity").executeUpdate();
    }

    public void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            PagoTarjetaEntity entity = factory.manufacturePojo(PagoTarjetaEntity.class);

            fm.persist(entity);
            data.add(entity);
        }
    }

    @Test
    public void createFacturaTest() {
        PodamFactory factory = new PodamFactoryImpl();
        PagoTarjetaEntity newPagoEntity = factory.manufacturePojo(PagoTarjetaEntity.class);
        PagoTarjetaEntity fe = fp.create(newPagoEntity);
        Assert.assertNotNull(fe);
        PagoTarjetaEntity entity = fm.find(PagoTarjetaEntity.class, fe.getId());
        Assert.assertEquals(newPagoEntity.getCsv(), entity.getCsv());
    }

    @Test
    public void getFacturasTest() {
        List<PagoTarjetaEntity> list = fp.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (PagoTarjetaEntity entity : list) {
            boolean seEncontro = false;
            for (PagoTarjetaEntity entity2 : data) {
                if (entity.getId().equals(entity2.getId())) {
                    seEncontro = true;
                }
            }
            Assert.assertTrue(seEncontro);
        }
    }

    @Test
    public void getFacturaTest() {
        PagoTarjetaEntity entity = data.get(0);
        PagoTarjetaEntity entity2 = fp.find(entity.getId());
        Assert.assertNotNull(entity2);
        Assert.assertEquals(entity.getId(), entity2.getId());
        Assert.assertEquals(entity.getCsv(), entity2.getCsv());
    }

    @Test
    public void deleteFacturaTest(){
        PagoTarjetaEntity entity = data.get(0);
        fp.remove(entity.getId());
        PagoTarjetaEntity entity2 = fm.find(PagoTarjetaEntity.class, entity.getId());
        Assert.assertNull(entity2);
    }
      
         @Test
    public void updateServicioOfrecidoTest()
    {
        PodamFactory factory= new PodamFactoryImpl();
        
        PagoTarjetaEntity entity = factory.manufacturePojo(PagoTarjetaEntity.class);
        PagoTarjetaEntity newEntity = factory.manufacturePojo(PagoTarjetaEntity.class);
        
        entity= fp.create(entity);
        
        newEntity= fp.update(newEntity);
        
       PagoTarjetaEntity entitySearch = fm.find(PagoTarjetaEntity.class, newEntity.getId());  
       Assert.assertNotNull(entitySearch);
       
       Assert.assertEquals(newEntity.getId(), entitySearch.getId());
       Assert.assertEquals(newEntity.getCsv(), entitySearch.getCsv());
        
        
    }       
}

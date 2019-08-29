/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.test.persistence;

import co.edu.uniandes.csw.servicios.entities.SolicitudServicioEntity;
import co.edu.uniandes.csw.servicios.persistence.SolicitudServicioPersistence;
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
 * @author ca.torrese
 */
@RunWith(Arquillian.class)
public class SolicitudServicioPersistenceTest {
    
    @Inject
    private SolicitudServicioPersistence ssp;
    
    @PersistenceContext
    private EntityManager em;
    
     @Inject
    UserTransaction utx;

    private List<SolicitudServicioEntity> data = new ArrayList<SolicitudServicioEntity>();
    
    @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(SolicitudServicioEntity.class.getPackage())
                .addPackage(SolicitudServicioPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
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
        em.createQuery("delete from SolicitudServicioEntity").executeUpdate();
    }
    
     private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            SolicitudServicioEntity entity = factory.manufacturePojo(SolicitudServicioEntity.class);

            em.persist(entity);
            data.add(entity);
        }
    }
    
    @Test
    public void createSolicitudServicioTest()
    {
        
        PodamFactory factory = new PodamFactoryImpl();
        SolicitudServicioEntity newEntity = factory.manufacturePojo(SolicitudServicioEntity.class);
        
        SolicitudServicioEntity sse = ssp.create(newEntity);
        
        Assert.assertNotNull(sse);
        
        SolicitudServicioEntity entity = em.find(SolicitudServicioEntity.class, sse.getId());
        
        Assert.assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(newEntity.getEstado(), entity.getEstado());
        Assert.assertEquals(newEntity.getFoto(), entity.getFoto());
        Assert.assertEquals(newEntity.getFechaInicio(), entity.getFechaInicio());
        
    }
    
    @Test
    public void getSolicitudServiciosTest() {
        List<SolicitudServicioEntity> list = ssp.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (SolicitudServicioEntity ent : list) {
            boolean found = false;
            for (SolicitudServicioEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    @Test
    public void getSolicitudServicioTest() {
        SolicitudServicioEntity entity = data.get(0);
        SolicitudServicioEntity newEntity = ssp.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getDescripcion(), newEntity.getDescripcion());
        Assert.assertEquals(entity.getFechaInicio(), newEntity.getFechaInicio());
        Assert.assertEquals(entity.getEstado(), newEntity.getEstado());
        Assert.assertEquals(entity.getFoto(), newEntity.getFoto());
    }
    
     @Test
    public void updateBookTest() {
        SolicitudServicioEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        SolicitudServicioEntity newEntity = factory.manufacturePojo(SolicitudServicioEntity.class);

        newEntity.setId(entity.getId());

         ssp.update(newEntity);

        SolicitudServicioEntity resp = em.find(SolicitudServicioEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getFechaInicio(), resp.getFechaInicio());
        Assert.assertEquals(newEntity.getDescripcion(), resp.getDescripcion());
        Assert.assertEquals(newEntity.getEstado(), resp.getEstado());
        Assert.assertEquals(newEntity.getFoto(), resp.getFoto());
    }
    
     @Test
    public void deleteSolicitudServicioTest() {
        SolicitudServicioEntity entity = data.get(0);
        ssp.delete(entity.getId());
        SolicitudServicioEntity deleted = em.find(SolicitudServicioEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}

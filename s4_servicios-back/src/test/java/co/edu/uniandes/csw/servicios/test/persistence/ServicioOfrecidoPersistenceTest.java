/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.test.persistence;

import co.edu.uniandes.csw.servicios.entities.ServicioOfrecidoEntity;
import co.edu.uniandes.csw.servicios.persistence.ServicioOfrecidoPersistence;
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
 * @author Violeta Rodríguez
 */
@RunWith(Arquillian.class)
public class ServicioOfrecidoPersistenceTest {
    
    @Inject
    private ServicioOfrecidoPersistence sop;
    
    @PersistenceContext
    private EntityManager em;
    
     private ArrayList<ServicioOfrecidoEntity> data = new ArrayList();
    @Inject
    UserTransaction utx;
    
  
    
    @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ServicioOfrecidoEntity.class.getPackage())
                .addPackage(ServicioOfrecidoPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    @Before
     public void iniciarTest() {
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
        em.createQuery("delete from EditorialEntity").executeUpdate();
    }
     
     private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        data = new ArrayList(3);
        for (int i = 0; i < 3; i++) {

           ServicioOfrecidoEntity entity = factory.manufacturePojo(ServicioOfrecidoEntity.class);

            em.persist(entity);
            data.add(entity);
        }
     }
     
     
     
    @Test
    public void createServicioOfrecidoTest()
    {
        PodamFactory factory= new PodamFactoryImpl();
        ServicioOfrecidoEntity newEntity = factory.manufacturePojo(ServicioOfrecidoEntity.class);
        
        ServicioOfrecidoEntity ee= sop.create(newEntity);
         
        Assert.assertNotNull(ee);
        
        ServicioOfrecidoEntity entity = em.find(ServicioOfrecidoEntity.class, ee.getId());
        
        Assert.assertEquals(newEntity.getTipo(), entity.getTipo());
        Assert.assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(newEntity.getPrecio(), entity.getPrecio(), 0.1);
       
    }
    
    @Test
    public void deleteSerivicioOfrecidoTest()
    {
        PodamFactory factory = new PodamFactoryImpl();
        ServicioOfrecidoEntity entity = factory.manufacturePojo(ServicioOfrecidoEntity.class);
        sop.create(entity);
        sop.delete(entity.getId());
        
       
        Assert.assertNull(em.find(ServicioOfrecidoEntity.class, entity.getId()));
    }

    
    
}

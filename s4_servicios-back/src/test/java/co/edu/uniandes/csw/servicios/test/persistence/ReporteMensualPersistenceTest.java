/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.test.persistence;

import co.edu.uniandes.csw.servicios.entities.ReporteMensualEntity;
import co.edu.uniandes.csw.servicios.persistence.ReporteMensualPersistence;
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
public class ReporteMensualPersistenceTest 
{   
    @Inject
    private ReporteMensualPersistence rmp;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject 
    UserTransaction utx;
    
  
    
    @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ReporteMensualEntity.class.getPackage())
                .addPackage(ReporteMensualPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    @Before
     public void iniciarTest() {
        try {
            utx.begin();
            em.joinTransaction();
            clearData();
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
        em.createQuery("delete from ReporteMensualEntity").executeUpdate();
    } 
     
    @Test
    public void createReposteMensualTest()
    {
        PodamFactory factory= new PodamFactoryImpl();
        ReporteMensualEntity newEntity = factory.manufacturePojo(ReporteMensualEntity.class);
        
        ReporteMensualEntity ee= rmp.create(newEntity);
         
        Assert.assertNotNull(ee);
        
        ReporteMensualEntity entity = em.find(ReporteMensualEntity.class, ee.getId());
        
        Assert.assertEquals(newEntity.getEgresos(), entity.getEgresos(),0.1);
        Assert.assertEquals(newEntity.getIngresos(), entity.getIngresos(),0.1);
        Assert.assertEquals(newEntity.getMes(), entity.getMes());
    }
      
    @Test
    public void findReporteMensualTest()
    {
        PodamFactory factory= new PodamFactoryImpl();
        ReporteMensualEntity newEntity = factory.manufacturePojo(ReporteMensualEntity.class);
        
        //No está sirviendo la parte de persistir desde la clase de pruebas
        ReporteMensualEntity ee= rmp.create(newEntity);
        
        ReporteMensualEntity entity =rmp.find(ee.getId());
        
        Assert.assertNotNull(entity);
        Assert.assertEquals(newEntity.getEgresos(), entity.getEgresos(),0.1);
        Assert.assertEquals(newEntity.getIngresos(), entity.getIngresos(),0.1);
        Assert.assertEquals(newEntity.getMes(), entity.getMes());
    }
   
    @Test 
    public void findAllReporteMensualTest()
    {
        //clearData();
        PodamFactory factory= new PodamFactoryImpl();
        
        ReporteMensualEntity newEntity1 = factory.manufacturePojo(ReporteMensualEntity.class);
        ReporteMensualEntity newEntity2 = factory.manufacturePojo(ReporteMensualEntity.class);
        ReporteMensualEntity newEntity3 = factory.manufacturePojo(ReporteMensualEntity.class);
        
        rmp.create(newEntity1);
        rmp.create(newEntity2);
        rmp.create(newEntity3);
        
       List<ReporteMensualEntity> lista = rmp.findAll();
       
       Assert.assertFalse(lista.isEmpty());
       System.out.println(lista.size());
       Assert.assertEquals(lista.size() , 3);
       
       
       Assert.assertNotNull(lista.get(0));
       Assert.assertEquals(newEntity1.getEgresos(), lista.get(0).getEgresos(), 0.1);
       Assert.assertEquals(newEntity1.getIngresos(), lista.get(0).getIngresos(),0.1);
       Assert.assertEquals(newEntity1.getMes(),lista.get(0).getMes()); 
     
       Assert.assertNotNull(lista.get(1));
       Assert.assertEquals(newEntity2.getEgresos(), lista.get(1).getEgresos(),0.1);
       Assert.assertEquals(newEntity2.getIngresos(), lista.get(1).getIngresos(),0.1);
       Assert.assertEquals(newEntity2.getMes(),lista.get(1).getMes());
       
       Assert.assertNotNull(lista.get(2));
       Assert.assertEquals(newEntity3.getEgresos(), lista.get(2).getEgresos(),0.1);
       Assert.assertEquals(newEntity3.getIngresos(), lista.get(2).getIngresos(),0.1);
       Assert.assertEquals(newEntity3.getMes(),lista.get(2).getMes());
    }
    
    @Test
    public void updateReporteMensualTest()
    {
        PodamFactory factory= new PodamFactoryImpl();
        
        ReporteMensualEntity entity = factory.manufacturePojo(ReporteMensualEntity.class);
        ReporteMensualEntity newEntity = factory.manufacturePojo(ReporteMensualEntity.class);
        
       entity=rmp.create(entity);
        
        newEntity= rmp.update(newEntity);
        
       ReporteMensualEntity entitySearch =em.find(ReporteMensualEntity.class, newEntity.getId());  
       Assert.assertNotNull(entitySearch);

       Assert.assertEquals(newEntity.getEgresos(), entitySearch.getEgresos(), 0.1);
       Assert.assertEquals(newEntity.getIngresos(), entitySearch.getIngresos(),0.1);
       Assert.assertEquals(newEntity.getMes(),entitySearch.getMes());
        
        
    }
    
    @Test
    public void deleteReporteMensualTest()
    {
        PodamFactory factory = new PodamFactoryImpl();
        ReporteMensualEntity entity = factory.manufacturePojo(ReporteMensualEntity.class);
        
        //Validar luego porque no sirve persistence 
        rmp.create(entity);
        rmp.delete(entity.getId());
        
       
        Assert.assertNull(em.find(ReporteMensualEntity.class, entity.getId()));
    }

    
    
}

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
    
     private ArrayList<ReporteMensualEntity> data = new ArrayList();
    
  
    
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
        em.createQuery("delete from ReporteMensualEntity").executeUpdate();
    } 
     
      private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            ReporteMensualEntity newEntity = factory.manufacturePojo(ReporteMensualEntity.class);
            em.persist(newEntity);
            data.add(newEntity);
        }
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
        Assert.assertEquals(newEntity.getNumSerivico(), entity.getNumSerivico());
    }
      
    @Test
    public void findReporteMensualTest()
    {
        ReporteMensualEntity entity =rmp.find(data.get(1).getId());
        
        Assert.assertNotNull(entity);
        Assert.assertEquals(data.get(1).getEgresos(), entity.getEgresos(),0.1);
        Assert.assertEquals(data.get(1).getIngresos(), entity.getIngresos(),0.1);
        Assert.assertEquals(data.get(1).getMes(), entity.getMes());
        Assert.assertEquals(data.get(1).getNumSerivico(), entity.getNumSerivico());
    }
   
    @Test 
    public void findAllReporteMensualTest()
    {        
        List<ReporteMensualEntity> lista = rmp.findAll();
       
       Assert.assertFalse(lista.isEmpty());
       Assert.assertEquals(lista.size(), data.size());
       
       for(ReporteMensualEntity i: lista)
       {
           Assert.assertNotNull(i);
           ReporteMensualEntity expected = em.find(ReporteMensualEntity.class, i.getId());
           Assert.assertNotNull("Debería haber una instancia con el id reportado.", expected);
           Assert.assertEquals(i.getEgresos(), expected.getEgresos(),0.1);
           Assert.assertEquals(i.getIngresos(), expected.getIngresos(),0.1);
           Assert.assertEquals(i.getMes(),expected.getMes()); 
           Assert.assertEquals(i.getNumSerivico(),expected.getNumSerivico()); 
       }
    }
    
    @Test
    public void updateReporteMensualTest()
    {
       PodamFactory factory= new PodamFactoryImpl();
       ReporteMensualEntity newEntity = factory.manufacturePojo(ReporteMensualEntity.class);
        
       newEntity.setId(data.get(0).getId());
       newEntity= rmp.update(newEntity);
        
       ReporteMensualEntity entitySearch =em.find(ReporteMensualEntity.class, newEntity.getId());  
       Assert.assertNotNull(entitySearch);

       Assert.assertEquals(newEntity.getEgresos(), entitySearch.getEgresos(), 0.1);
       Assert.assertEquals(newEntity.getIngresos(), entitySearch.getIngresos(),0.1);
       Assert.assertEquals(newEntity.getMes(),entitySearch.getMes()); 
       Assert.assertEquals(newEntity.getNumSerivico(),entitySearch.getNumSerivico());
    }
    
    @Test
    public void deleteReporteMensualTest()
    {
        rmp.delete(data.get(1).getId());
        
       
        Assert.assertNull(em.find(ReporteMensualEntity.class, data.get(1).getId()));
    }

    
    
}

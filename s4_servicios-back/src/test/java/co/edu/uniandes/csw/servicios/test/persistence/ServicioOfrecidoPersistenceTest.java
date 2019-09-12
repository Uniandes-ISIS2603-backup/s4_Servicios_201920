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
        em.createQuery("delete from ServicioOfrecidoEntity").executeUpdate();
    }
     
     private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        data = new ArrayList(3);
        for (int i = 0; i < 3; i++) {

            ServicioOfrecidoEntity newEntity = factory.manufacturePojo(ServicioOfrecidoEntity.class);

            sop.create(newEntity);
            data.add(newEntity);
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
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
       
    }
    
    @Test
    public void findSerivicioOfrecidoTest()
    {
        PodamFactory factory= new PodamFactoryImpl();
        ServicioOfrecidoEntity newEntity = factory.manufacturePojo(ServicioOfrecidoEntity.class);
        
        //No está sirviendo la parte de persistir desde la clase de pruebas
        ServicioOfrecidoEntity ee= sop.create(newEntity);
        
        ServicioOfrecidoEntity entity =sop.find(ee.getId());
        
        Assert.assertNotNull(entity);
        Assert.assertEquals(newEntity.getTipo(), entity.getTipo());
        Assert.assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(newEntity.getPrecio(), entity.getPrecio(), 0.1);
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
    }
    
       @Test
    public void findByNameTest()       
    {
        PodamFactory factory= new PodamFactoryImpl();
        ServicioOfrecidoEntity newEntity = factory.manufacturePojo(ServicioOfrecidoEntity.class);
        
        //No está sirviendo la parte de persistir desde la clase de pruebas
        ServicioOfrecidoEntity ee= sop.create(newEntity);
        
        ServicioOfrecidoEntity entity =sop.findByName(ee.getNombre());
        
        Assert.assertNotNull(entity);
        Assert.assertEquals(newEntity.getTipo(), entity.getTipo());
        Assert.assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(newEntity.getPrecio(), entity.getPrecio(), 0.1);
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
    }
 
    
    @Test 
    public void findAllServicioOfrecidoTest()
    {
        PodamFactory factory= new PodamFactoryImpl();
        
        ServicioOfrecidoEntity newEntity1 = factory.manufacturePojo(ServicioOfrecidoEntity.class);
        ServicioOfrecidoEntity newEntity2 = factory.manufacturePojo(ServicioOfrecidoEntity.class);
        ServicioOfrecidoEntity newEntity3 = factory.manufacturePojo(ServicioOfrecidoEntity.class);
        
        sop.create(newEntity1);
        sop.create(newEntity2);
        sop.create(newEntity3);
        
       List<ServicioOfrecidoEntity> lista = sop.findAll();
       
       Assert.assertFalse(lista.isEmpty());
       Assert.assertEquals(lista.size() , 3);
       
       Assert.assertNotNull(lista.get(0));
       Assert.assertEquals(newEntity1.getTipo(), lista.get(0).getTipo());
       Assert.assertEquals(newEntity1.getDescripcion(), lista.get(0).getDescripcion());
       Assert.assertEquals(newEntity1.getPrecio(),lista.get(0).getPrecio(), 0.1); 
       Assert.assertEquals(newEntity1.getNombre(), lista.get(0).getNombre());
       
        
       Assert.assertNotNull(lista.get(1));
       Assert.assertEquals(newEntity2.getTipo(), lista.get(1).getTipo());
       Assert.assertEquals(newEntity2.getDescripcion(), lista.get(1).getDescripcion());
       Assert.assertEquals(newEntity2.getPrecio(),lista.get(1).getPrecio(), 0.1); 
       Assert.assertEquals(newEntity2.getNombre(), lista.get(1).getNombre());
       
       Assert.assertNotNull(lista.get(2));
       Assert.assertEquals(newEntity3.getTipo(), lista.get(2).getTipo());
       Assert.assertEquals(newEntity3.getDescripcion(), lista.get(2).getDescripcion());
       Assert.assertEquals(newEntity3.getPrecio(),lista.get(2).getPrecio(), 0.1); 
       Assert.assertEquals(newEntity3.getNombre(), lista.get(2).getNombre());
    }
    
    @Test
    public void updateServicioOfrecidoTest()
    {
        PodamFactory factory= new PodamFactoryImpl();
        
        ServicioOfrecidoEntity entity = factory.manufacturePojo(ServicioOfrecidoEntity.class);
        ServicioOfrecidoEntity newEntity = factory.manufacturePojo(ServicioOfrecidoEntity.class);
        
       entity=sop.create(entity);
        
        newEntity= sop.update(newEntity);
        
       ServicioOfrecidoEntity entitySearch =em.find(ServicioOfrecidoEntity.class, newEntity.getId());  
       Assert.assertNotNull(entitySearch);
      // Assert.assertNull(em.find(ServicioOfrecidoEntity.class, entity.getId()));
       
       Assert.assertEquals(newEntity.getTipo(), entitySearch.getTipo());
       Assert.assertEquals(newEntity.getDescripcion(), entitySearch.getDescripcion());
       Assert.assertEquals(newEntity.getPrecio(),entitySearch.getPrecio(), 0.1);
       Assert.assertEquals(newEntity.getNombre(), entitySearch.getNombre());
        
        
    }
    
    @Test
    public void deleteSerivicioOfrecidoTest()
    {
        PodamFactory factory = new PodamFactoryImpl();
        ServicioOfrecidoEntity entity = factory.manufacturePojo(ServicioOfrecidoEntity.class);
        
        //Validar luego porque no sirve persistence 
        sop.create(entity);
        sop.delete(entity.getId());
        
       
        Assert.assertNull(em.find(ServicioOfrecidoEntity.class, entity.getId()));
    }

    
    
}

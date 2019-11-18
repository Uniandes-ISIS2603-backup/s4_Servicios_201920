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
        for (int i = 0; i < 3; i++) {
            ServicioOfrecidoEntity newEntity = factory.manufacturePojo(ServicioOfrecidoEntity.class);
            em.persist(newEntity);
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
        ServicioOfrecidoEntity entity =sop.find(data.get(1).getId());
        
        Assert.assertNotNull(entity);
        Assert.assertEquals(data.get(1).getTipo(), entity.getTipo());
        Assert.assertEquals(data.get(1).getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(data.get(1).getPrecio(), entity.getPrecio(), 0.1);
        Assert.assertEquals(data.get(1).getNombre(), entity.getNombre());
    }
    
       @Test 
    public void findAllServicioOfrecidoTest()
    {   
       List<ServicioOfrecidoEntity> lista = sop.findAll();
       
       Assert.assertFalse(lista.isEmpty());
       Assert.assertEquals(lista.size(), data.size());
       
       for(ServicioOfrecidoEntity i: lista)
       {
           Assert.assertNotNull(i);
           ServicioOfrecidoEntity expected = em.find(ServicioOfrecidoEntity.class, i.getId());
           Assert.assertNotNull("Debería haber una instancia con el id reportado.", expected);
           Assert.assertEquals(i.getTipo(), expected.getTipo());
           Assert.assertEquals(i.getDescripcion(), expected.getDescripcion());
           Assert.assertEquals(i.getPrecio(),expected.getPrecio(), 0.1); 
           Assert.assertEquals(i.getNombre(), expected.getNombre());
           
       }
       
    }
    
       @Test
    public void findByNameTest()       
    {        
        ServicioOfrecidoEntity entity =sop.findByName(data.get(2).getNombre());
        
        Assert.assertNotNull(entity);
        Assert.assertEquals(data.get(2).getTipo(), entity.getTipo());
        Assert.assertEquals(data.get(2).getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(data.get(2).getPrecio(), entity.getPrecio(), 0.1);
        Assert.assertEquals(data.get(2).getNombre(), entity.getNombre());
    }
    
      @Test
    public void findByTypeTest()       
    {        
        data.get(0).setTipo("Plomeria");
       sop.update((ServicioOfrecidoEntity )data.get(0));
        data.get(1).setTipo("Plomeria");
        sop.update(data.get(1));
        List<ServicioOfrecidoEntity> entities =sop.findByType("Plomeria");
        
        System.out.println("Tamaño" + entities.size());
        Assert.assertNotNull(entities);
        Assert.assertEquals(2, entities.size());
        
        Assert.assertEquals(data.get(0).getTipo(), entities.get(0).getTipo());

        Assert.assertEquals(data.get(1).getNombre(), entities.get(1).getNombre());
    }
    
    @Test
    public void updateServicioOfrecidoTest()
    {
        PodamFactory factory= new PodamFactoryImpl();        
        ServicioOfrecidoEntity newEntity = factory.manufacturePojo(ServicioOfrecidoEntity.class);
        
        newEntity.setId(data.get(0).getId());
        newEntity= sop.update(newEntity);
        
       ServicioOfrecidoEntity entitySearch =em.find(ServicioOfrecidoEntity.class, newEntity.getId());  
       Assert.assertNotNull(entitySearch);
      
       Assert.assertEquals(newEntity.getTipo(), entitySearch.getTipo());
       Assert.assertEquals(newEntity.getDescripcion(), entitySearch.getDescripcion());
       Assert.assertEquals(newEntity.getPrecio(),entitySearch.getPrecio(), 0.1);
       Assert.assertEquals(newEntity.getNombre(), entitySearch.getNombre());        
    }
    
    @Test
    public void deleteSerivicioOfrecidoTest()
    {
        sop.delete(data.get(1).getId()); 
       
        Assert.assertNull(em.find(ServicioOfrecidoEntity.class, data.get(1).getId()));
    }

    
    
}

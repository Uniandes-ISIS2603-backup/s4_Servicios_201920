/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.test.persistence;

import co.edu.uniandes.csw.servicios.entities.ClienteEntity;
import co.edu.uniandes.csw.servicios.persistence.ClientePersistence;
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
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Estudiante
 */
@RunWith(Arquillian.class)
public class ClientePersistenceTest {
    
    /**
     * Injección de la dependencia a la clase ClientePersistence que va a ser probado
     */
    @Inject
    private ClientePersistence persistence;
    
    /**
     * El Persistence context que se va a usar para acceder a la base de daos de prueba
     */
    @PersistenceContext
    private EntityManager em;
    
    /**
     * Variable que se va a usar para marcar las transacciones del EntityManager cuando se modifica la base
     */
    @Inject
    UserTransaction utx;
    
    /**
     * Lista con los datos que se van a contener al principio y facilitan las preubas. 
     */
    private List<ClienteEntity> data = new ArrayList<>();
    
    @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ClienteEntity.class.getPackage())
                .addPackage(ClientePersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml","persistence.xml")
                .addAsManifestResource("META-INF/beans.xml","beans.xml");
    }
    
    public ClientePersistenceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    private void clearData() {
        em.createQuery("delete from ClienteEntity").executeUpdate();
    }
    
    private void insertData(){
        PodamFactory factory = new PodamFactoryImpl();
        for(int i = 0; i< 3; i ++){
            ClienteEntity entidad = factory.manufacturePojo(ClienteEntity.class);
            em.persist(entidad);
            data.add(entidad);
        }
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        try{
            utx.begin();
            em.joinTransaction();
            clearData();
            insertData();
            utx.commit();
        } catch(Exception e){
            e.printStackTrace();
            try{
                utx.rollback();
            } catch(Exception e1){
                e1.printStackTrace();
            }
        }
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of create method, of class ClientePersistence.
     */
    @Test
    public void testCreate() {
        PodamFactory factory = new PodamFactoryImpl();
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        ClienteEntity resultado;
        resultado = persistence.create(newEntity);
        Assert.assertNotNull(resultado);
        ClienteEntity entity = em.find(ClienteEntity.class, resultado.getId());
        Assert.assertNotNull(entity);
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
    }

    /**
     * Test of findAll method, of class ClientePersistence.
     */
    @Test
    public void testFindAll() {
        List<ClienteEntity> lista = persistence.findAll();
        Assert.assertEquals(data.size(), lista.size());
        for(ClienteEntity ent : lista){
            boolean found = false;
            for(ClienteEntity entity : data) {
                if(ent.getId().equals(entity.getId()))
                    found = true;
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Test of find method, of class ClientePersistence.
     */
    @Test
    public void testFind() {
        ClienteEntity entity = data.get(0);
        ClienteEntity newEntity = persistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
    }

    /**
     * Test of update method, of class ClientePersistence.
     */
    @Test
    public void testUpdate() {
        ClienteEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        ClienteEntity newEntity =factory.manufacturePojo(ClienteEntity.class);
        newEntity.setId(entity.getId());
        persistence.update(newEntity);
        ClienteEntity resp = em.find(ClienteEntity.class, entity.getId());
        Assert.assertEquals(newEntity.getNombre(), resp.getNombre());
    }

    /**
     * Test of delete method, of class ClientePersistence.
     */
    @Test
    public void testDelete() {
        ClienteEntity entity = data.get(0);
        ClienteEntity deleted = em.find(ClienteEntity.class, entity.getId());
        persistence.delete(entity);
        Assert.assertNotNull(deleted);
    }
    
}

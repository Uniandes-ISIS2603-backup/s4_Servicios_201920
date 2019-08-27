/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.test.persistence;

import co.edu.uniandes.csw.servicios.entities.ClienteEntity;
import co.edu.uniandes.csw.servicios.persistence.*;
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
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.*;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Estudiante
 */
@RunWith(Arquillian.class)
public class ClientePersistenceTest {
    
    @Inject
    UserTransaction utx;
    
    @PersistenceContext
    private EntityManager em;
    
    private ClientePersistence persistence;
    
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
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of create method, of class ClientePersistence.
     */
    @Test
    public void testCreate() throws Exception {
        PodamFactory factory = new PodamFactoryImpl();
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        ClienteEntity resultado;
        resultado = persistence.create(newEntity);
        //Assert.assertNotNull(result);
        ClienteEntity entity = em.find(ClienteEntity.class, resultado.getId());
        Assert.assertNotNull(entity);
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
    }

    /**
     * Test of findAll method, of class ClientePersistence.
     */
    @Test
    public void testFindAll() throws Exception {
        List<ClienteEntity> lista = persistence.findAll();
        //Assert.assertEquals(data.size(), list.size());
        
    }

    /**
     * Test of find method, of class ClientePersistence.
     */
    @Test
    public void testFind() throws Exception {
        fail("testFind");
    }

    /**
     * Test of update method, of class ClientePersistence.
     */
    @Test
    public void testUpdate() throws Exception {
        fail("testUpdate");
    }

    /**
     * Test of delete method, of class ClientePersistence.
     */
    @Test
    public void testDelete() throws Exception {
        fail("testDelete");
    }
    
}

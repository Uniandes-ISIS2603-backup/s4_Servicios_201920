/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.test.logic;

import co.edu.uniandes.csw.servicios.ejb.ServicioOfrecidoLogic;
import co.edu.uniandes.csw.servicios.entities.ServicioOfrecidoEntity;
import co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException;
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
public class ServicioOfrecidoLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();
     
     @Inject
     private ServicioOfrecidoLogic servicioOfrecidoLogic;
     
     @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;
    
    private List<ServicioOfrecidoEntity> data = new ArrayList<ServicioOfrecidoEntity>();
    
      /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ServicioOfrecidoEntity.class.getPackage())
                .addPackage(ServicioOfrecidoLogic.class.getPackage())
                .addPackage(ServicioOfrecidoPersistence.class.getPackage())
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
        em.createQuery("delete from ServicioOfrecidoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ServicioOfrecidoEntity servicio = factory.manufacturePojo(ServicioOfrecidoEntity.class);
            em.persist(servicio);
            data.add(servicio);
        }
    }
    
    /**
     * Prueba para crear un SerivicioOfrecdo
     *
     * @throws co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException
     */
    @Test
    public void createServicioOfrecidoTest()
    {
        // Probas que la regla de negocio del tipo se cumpla. El serivico a agregar debe ser de uno de los tiposestablecidos. 
        ServicioOfrecidoEntity newEntity = factory.manufacturePojo(ServicioOfrecidoEntity.class);
        newEntity.setTipo("No es un tipo válido");
       
        try 
        {
            ServicioOfrecidoEntity creadoEntity = servicioOfrecidoLogic.createServicioOfrecido(newEntity);
            
            Assert.fail("Se debería lanzar excepción cuando el tipo del servicio no se encuentra dentro de la oferta");
        } 
        catch (BusinessLogicException e)
        { 
            newEntity.setTipo("Aseo");
             try
             {
                ServicioOfrecidoEntity creadoEntity = servicioOfrecidoLogic.createServicioOfrecido(newEntity);
                 
                Assert.assertNotNull(creadoEntity);
                Assert.assertEquals(newEntity.getTipo(), creadoEntity.getTipo());
                Assert.assertEquals(newEntity.getDescripcion(), creadoEntity.getDescripcion());
                Assert.assertEquals(newEntity.getPrecio(), creadoEntity.getPrecio(), 0.1);
                Assert.assertEquals(newEntity.getNombre(), creadoEntity.getNombre());
                 
             }
             catch(BusinessLogicException e1)
             {
                 Assert.fail("No debería lanzar excepción cuando el tipo del servicio se encuentra dentro de la oferta");
                 
             }
        }
        
       //Probamos que la regla de negocio para el nombre se cumpla. No se debe poder agregar un servicio con nombre repetido.
       
        newEntity.setTipo("Aseo");
        newEntity.setNombre("Prueba");
        try
        {
        ServicioOfrecidoEntity creadoEntity = servicioOfrecidoLogic.createServicioOfrecido(newEntity);
        ServicioOfrecidoEntity newEntityName = factory.manufacturePojo(ServicioOfrecidoEntity.class);
        newEntityName.setNombre("Prueba");
        creadoEntity = servicioOfrecidoLogic.createServicioOfrecido(newEntityName);
        
        Assert.fail("Debería lanzar excepción cuando el nombre es repetido");
        }
        catch (BusinessLogicException e2)
        {
           
        } 
        
    }
    
     /**
     * Prueba para consultar la lista de Servicios.
     */
    @Test
    public void getServiciosTest() {
        List<ServicioOfrecidoEntity> list = servicioOfrecidoLogic.getServiciosOfrecidos();
        Assert.assertEquals(data.size(), list.size());
        for (ServicioOfrecidoEntity entity : list) {
            boolean found = false;
            for (ServicioOfrecidoEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
     /**
     * Prueba para consultar un Servicio1.
     */
    @Test
    public void getServicio1Test() {
        ServicioOfrecidoEntity entity = data.get(0);
        ServicioOfrecidoEntity resultEntity = servicioOfrecidoLogic.getServicioOfrecido(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(resultEntity.getId(), entity.getId());
        Assert.assertEquals(resultEntity.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(resultEntity.getNombre(), entity.getNombre());
        Assert.assertEquals(resultEntity.getTipo(), entity.getTipo());
        Assert.assertTrue(resultEntity.getPrecio() == entity.getPrecio());
    }
    
    /**
     * Prueba para consultar un Servicio2.
     */
    @Test
    public void getServicio2Test() {
        ServicioOfrecidoEntity entity = data.get(1);
        ServicioOfrecidoEntity resultEntity = servicioOfrecidoLogic.getServicioOfrecido(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(resultEntity.getId(), entity.getId());
        Assert.assertEquals(resultEntity.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(resultEntity.getNombre(), entity.getNombre());
        Assert.assertEquals(resultEntity.getTipo(), entity.getTipo());
        Assert.assertTrue(resultEntity.getPrecio() == entity.getPrecio());
    }
    
    /**
     * Prueba para consultar un Servicio3.
     */
    @Test
    public void getServicio3Test() {
        ServicioOfrecidoEntity entity = data.get(2);
        ServicioOfrecidoEntity resultEntity = servicioOfrecidoLogic.getServicioOfrecido(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(resultEntity.getId(), entity.getId());
        Assert.assertEquals(resultEntity.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(resultEntity.getNombre(), entity.getNombre());
        Assert.assertEquals(resultEntity.getTipo(), entity.getTipo());
        Assert.assertTrue(resultEntity.getPrecio() == entity.getPrecio());
    }
    
    /**
     * Prueba para actualizar un Servicio.
     * 
     * @throws co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException
     */
    @Test
    public void updateServicioTest()throws BusinessLogicException {
        ServicioOfrecidoEntity entity = data.get(0);
        ServicioOfrecidoEntity pojoEntity = factory.manufacturePojo(ServicioOfrecidoEntity.class);
        pojoEntity.setId(entity.getId());
        servicioOfrecidoLogic.updateServicioOfrecido(pojoEntity.getId(), pojoEntity);
        ServicioOfrecidoEntity resp = em.find(ServicioOfrecidoEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getDescripcion(), resp.getDescripcion());
        Assert.assertEquals(pojoEntity.getNombre(), resp.getNombre());
        Assert.assertEquals(pojoEntity.getTipo(), resp.getTipo());
        Assert.assertTrue(pojoEntity.getPrecio() == resp.getPrecio());
    }
    
      /**
     * Prueba para eliminar un Servicio.
     *
     * @throws co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException
     */
    @Test
    public void deleteServicioTest() throws BusinessLogicException {
        ServicioOfrecidoEntity entity = data.get(1);
        servicioOfrecidoLogic.deleteServicioOfrecido(entity.getId());
        ServicioOfrecidoEntity deleted = em.find(ServicioOfrecidoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.test.logic;

import co.edu.uniandes.csw.servicios.ejb.TrabajadorLogic;
import co.edu.uniandes.csw.servicios.entities.TrabajadorEntity;
import co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.servicios.persistence.TrabajadorPersistence;
import com.google.common.math.BigIntegerMath;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.glassfish.pfl.basic.tools.argparser.ElementParser;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.AllTests;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author c.otalora
 */
@RunWith(Arquillian.class)
public class TrabajadorLogicTest {
    
    @Inject
    private TrabajadorLogic trabajadorLogic;
    
    @Inject
    private TrabajadorPersistence ep;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    UserTransaction utx;
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    private List<TrabajadorEntity> data = new ArrayList<>();
    
    @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(TrabajadorEntity.class.getPackage())
                .addPackage(TrabajadorPersistence.class.getPackage())
                .addPackage(TrabajadorLogic.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml","persistence.xml")
                .addAsManifestResource("META-INF/beans.xml","beans.xml");
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
        em.createQuery("delete from TrabajadorEntity").executeUpdate();
    }
    
   private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            TrabajadorEntity entity = factory.manufacturePojo(TrabajadorEntity.class);

            em.persist(entity);
            data.add(entity);
        }
    }
    
   /**
    * Prueba crear un trabajador
    * @throws BusinessLogicException 
    */
    @Test
    public void createTrabajadorTest() throws BusinessLogicException{
        PodamFactory factory = new PodamFactoryImpl();
        TrabajadorEntity newEntity = factory.manufacturePojo(TrabajadorEntity.class);
        
        TrabajadorEntity newEntityFail = newEntity;
        
        //No se puede agregar un tabajador con el mismo correo
       try
        {
            newEntityFail.setCorreo(data.get(0).getCorreo());
            TrabajadorEntity creado = trabajadorLogic.crearTrabajador(newEntityFail);
            Assert.fail("No se deberia poder crear un Trabajador con un correo que ya existe.");
        }
        catch (BusinessLogicException e){}
          //No se puede agregar un tabajador con el mismo usuario
        try
        {
            newEntityFail=newEntity;
            newEntityFail.setUsuario(data.get(0).getUsuario());
            TrabajadorEntity creado = trabajadorLogic.crearTrabajador(newEntityFail);
            Assert.fail("No se deberia poder crear un Trabajador con un usuario que ya existe.");
        }
        catch (BusinessLogicException e){}
        
        //Se puede crear un trabajador   
        try
        {
        newEntity = factory.manufacturePojo(TrabajadorEntity.class);
        TrabajadorEntity ee = trabajadorLogic.crearTrabajador(newEntity);
        Assert.assertNotNull("No crea nada", ee);        
        Assert.assertEquals(newEntity.getNombre(), ee.getNombre());
        Assert.assertEquals(newEntity.getUsuario(), ee.getUsuario());
        Assert.assertEquals(newEntity.getContrasena(), ee.getContrasena());
        Assert.assertEquals(newEntity.getTelefono(), ee.getTelefono());
        Assert.assertEquals(newEntity.getCorreo(), ee.getCorreo());
        Assert.assertEquals(newEntity.getFoto(), ee.getFoto());
        Assert.assertEquals(newEntity.isDisponibilidad(), ee.isDisponibilidad());
        Assert.assertEquals(newEntity.getHojaVida(), ee.getHojaVida());
        Assert.assertEquals(newEntity.isEsApto(), ee.isEsApto());
        Assert.assertEquals(newEntity.getSeguroSocial(), ee.getSeguroSocial());
        Assert.assertEquals(newEntity.getRiesgos(), ee.getRiesgos());
        }
        catch(BusinessLogicException e)
        {
            Assert.fail(newEntity.getUsuario());

        }  
    }
    
       /**
     * Prueba para consultar la lista deTrabajadores
     */
    @Test
    public void getTrabajadoresTest() 
    {
        List<TrabajadorEntity> list = trabajadorLogic.getTrabajadores();
        Assert.assertEquals(data.size(), list.size());
        for (TrabajadorEntity entity : list) {
            boolean found = false;
            for (TrabajadorEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

      /**
     * Prueba para consultar un trabajodr
     */
    @Test
    public void getServicio1Test() {
        TrabajadorEntity newEntity = data.get(0);
        TrabajadorEntity ee = trabajadorLogic.getTrabajador(newEntity.getId());
        Assert.assertNotNull(ee);
        Assert.assertEquals(newEntity.getNombre(), ee.getNombre());
        Assert.assertEquals(newEntity.getUsuario(), ee.getUsuario());
        Assert.assertEquals(newEntity.getContrasena(), ee.getContrasena());
        Assert.assertEquals(newEntity.getTelefono(), ee.getTelefono());
        Assert.assertEquals(newEntity.getCorreo(), ee.getCorreo());
        Assert.assertEquals(newEntity.getFoto(), ee.getFoto());
        Assert.assertEquals(newEntity.isDisponibilidad(), ee.isDisponibilidad());
        Assert.assertEquals(newEntity.getHojaVida(), ee.getHojaVida());
        Assert.assertEquals(newEntity.isEsApto(), ee.isEsApto());
        Assert.assertEquals(newEntity.getSeguroSocial(), ee.getSeguroSocial());
        Assert.assertEquals(newEntity.getRiesgos(), ee.getRiesgos());
    }
    
     /**
     * Prueba para actualizar un trabajador.
     * 
     * @throws co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException
     */
    @Test
    public void updateServicioTest()throws BusinessLogicException {
        TrabajadorEntity newEntity = data.get(0);
        TrabajadorEntity pojoEntity = factory.manufacturePojo(TrabajadorEntity.class);
        
        newEntity.setCorreo("Nuevo Correo");
        newEntity.setDisponibilidad(false);
        newEntity.setSeguroSocial("Otro seguro social");
        
        TrabajadorEntity ee = trabajadorLogic.updateTrabajador(newEntity.getId(),newEntity);
        
        Assert.assertNotNull(ee);        
        Assert.assertEquals(newEntity.getNombre(), ee.getNombre());
        Assert.assertEquals(newEntity.getUsuario(), ee.getUsuario());
        Assert.assertEquals(newEntity.getContrasena(), ee.getContrasena());
        Assert.assertEquals(newEntity.getTelefono(), ee.getTelefono());
        Assert.assertEquals(newEntity.getCorreo(), ee.getCorreo());
        Assert.assertEquals(newEntity.getFoto(), ee.getFoto());
        Assert.assertEquals(newEntity.isDisponibilidad(), ee.isDisponibilidad());
        Assert.assertEquals(newEntity.getHojaVida(), ee.getHojaVida());
        Assert.assertEquals(newEntity.isEsApto(), ee.isEsApto());
        Assert.assertEquals(newEntity.getSeguroSocial(), ee.getSeguroSocial());
        Assert.assertEquals(newEntity.getRiesgos(), ee.getRiesgos());
        
    }
    
    
    
}
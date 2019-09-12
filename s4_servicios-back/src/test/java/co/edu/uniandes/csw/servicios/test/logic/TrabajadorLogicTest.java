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
    
    @Test
    public void createTrabajadorTest() throws BusinessLogicException{
        PodamFactory factory = new PodamFactoryImpl();
        TrabajadorEntity newEntity = factory.manufacturePojo(TrabajadorEntity.class);
        
        TrabajadorEntity ee = trabajadorLogic.crearTrabajador(newEntity);
        
        Assert.assertNotNull("No crea nada", ee);
        
        TrabajadorEntity entity= em.find(TrabajadorEntity.class, ee.getId());
        
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
    }
    
    @Test(expected = BusinessLogicException.class)
    public void crearTrabaJadorCOnMismoID()throws BusinessLogicException{
        TrabajadorEntity newEntity = factory.manufacturePojo(TrabajadorEntity.class);
        trabajadorLogic.crearTrabajador(newEntity);
        trabajadorLogic.crearTrabajador(newEntity);
    }
}
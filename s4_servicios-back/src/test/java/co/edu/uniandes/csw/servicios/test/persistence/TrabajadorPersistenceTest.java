/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.test.persistence;

import co.edu.uniandes.csw.servicios.entities.TrabajadorEntity;
import co.edu.uniandes.csw.servicios.persistence.TrabajadorPersistence;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import junit.framework.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author c.otalora
 */

@RunWith(Arquillian.class)
public class TrabajadorPersistenceTest {
    
    @Inject
    private TrabajadorPersistence ep;
    
    @PersistenceContext
    private EntityManager em;
    
    @Deployment
    public static JavaArchive deployment (){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(TrabajadorEntity.class.getPackage())
                .addPackage(TrabajadorPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistance.xml","persistance.xml")
                .addAsManifestResource("META-INF/beans.xml","beans.xml");
    }
    
    @Test
    public void createTrabajadorTest(){
        PodamFactory factory = new PodamFactoryImpl();
        TrabajadorEntity newEntity = factory.manufacturePojo(TrabajadorEntity.class);
        
        TrabajadorEntity ee = ep.create(newEntity);
        
        Assert.assertNotNull("No crea nada", ee);
        
        TrabajadorEntity entity= em.find(TrabajadorEntity.class, ee.getId());
        
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
    }
}

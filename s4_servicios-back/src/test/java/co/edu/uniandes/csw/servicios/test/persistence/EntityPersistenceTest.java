/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.test.persistence;

import co.edu.uniandes.csw.servicios.entities.FacturaEntity;
import co.edu.uniandes.csw.servicios.persistence.FacturaPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.Assert;
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
 * @author Juan Lucas Ibarra
 */
@RunWith(Arquillian.class)
public class EntityPersistenceTest {
    
    private List<FacturaEntity> data = new ArrayList<>();
    
    @Inject
    private FacturaPersistence fp;
    
    @PersistenceContext
    private EntityManager fm;
    
    @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(FacturaEntity.class.getPackage())
                .addPackage(FacturaPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    @Test
    public void createFacturaTest(){
        PodamFactory factory = new PodamFactoryImpl();
        FacturaEntity newFacturaEntity = factory.manufacturePojo(FacturaEntity.class);
        FacturaEntity fe = fp.create(newFacturaEntity);
        Assert.assertNotNull(fe);
        FacturaEntity entity = fm.find(FacturaEntity.class, fe.getId());
        Assert.assertEquals(newFacturaEntity.getDuracion(), entity.getDuracion());
    }
    
        
    @Test
    public void findFacturaTest(){
        PodamFactory factory = new PodamFactoryImpl();
        FacturaEntity newFacturaEntity = factory.manufacturePojo(FacturaEntity.class);
        FacturaEntity fe = fp.create(newFacturaEntity);
        Assert.assertNotNull(fe);
        FacturaEntity entity = fm.find(FacturaEntity.class, fe.getId());
        Assert.assertEquals(newFacturaEntity.getDuracion(), entity.getDuracion());
    }
    
    
    
}

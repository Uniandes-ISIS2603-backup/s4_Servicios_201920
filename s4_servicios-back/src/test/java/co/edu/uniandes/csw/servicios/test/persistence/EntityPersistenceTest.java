/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.test.persistence;

import co.edu.uniandes.csw.servicios.entities.SolicitudServicioEntity;
import co.edu.uniandes.csw.servicios.persistence.SolicitudServicioPersistence;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author ca.torrese
 */
@RunWith(Arquillian.class)
public class EntityPersistenceTest {
    
    @Inject
    private SolicitudServicioPersistence ssp;
    
    @PersistenceContext
    private EntityManager em;
    
    @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(SolicitudServicioEntity.class.getPackage())
                .addPackage(SolicitudServicioPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    @Test
    public void createSolicitudServicioTest()
    {
        
        PodamFactory factory = new PodamFactoryImpl();
        SolicitudServicioEntity newEntity = factory.manufacturePojo(SolicitudServicioEntity.class);
        
        SolicitudServicioEntity sse = ssp.create(newEntity);
        
        Assert.assertNotNull(sse);
        
        SolicitudServicioEntity entity = em.find(SolicitudServicioEntity.class, sse.getId());
        
        Assert.assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(newEntity.getEstado(), entity.getEstado());
        Assert.assertEquals(newEntity.getFoto(), entity.getFoto());
        Assert.assertEquals(newEntity.getFechaInicio(), entity.getFechaInicio());
        
    }
}

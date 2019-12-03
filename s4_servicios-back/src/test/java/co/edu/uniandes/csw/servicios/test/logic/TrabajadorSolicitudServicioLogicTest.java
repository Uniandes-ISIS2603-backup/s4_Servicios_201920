/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.test.logic;

import co.edu.uniandes.csw.servicios.ejb.SolicitudServicioLogic;
import co.edu.uniandes.csw.servicios.ejb.TrabajadorSolicitudServicioLogic;
import co.edu.uniandes.csw.servicios.entities.ClienteEntity;
import co.edu.uniandes.csw.servicios.entities.SolicitudServicioEntity;
import co.edu.uniandes.csw.servicios.entities.TrabajadorEntity;
import co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.servicios.persistence.TrabajadorPersistence;
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
 * @author Estudiante
 */
@RunWith(Arquillian.class)
public class TrabajadorSolicitudServicioLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private TrabajadorSolicitudServicioLogic trabajadorSolicitudServicioLogic;
    
    @Inject
    private SolicitudServicioLogic solicitudServicioLogic;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;
    
    private TrabajadorEntity trabajador = new TrabajadorEntity();
    
    private List<SolicitudServicioEntity> data = new ArrayList<>();
    
    private ClienteEntity newCliente = new ClienteEntity();
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(TrabajadorEntity.class.getPackage())
                .addPackage(SolicitudServicioEntity.class.getPackage())
                .addPackage(TrabajadorSolicitudServicioLogic.class.getPackage())
                .addPackage(TrabajadorPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
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
    
    private void clearData() {
        em.createQuery("delete from TrabajadorEntity").executeUpdate();
        em.createQuery("delete from SolicitudServicioEntity").executeUpdate();
    }
    
    private void insertData() {

        trabajador = factory.manufacturePojo(TrabajadorEntity.class);
        trabajador.setId(1L);
        trabajador.setSolicitudes(new ArrayList<>());
        em.persist(trabajador);

        for (int i = 0; i < 3; i++) {
            SolicitudServicioEntity entity = factory.manufacturePojo(SolicitudServicioEntity.class);
            em.persist(entity);
            data.add(entity);
            trabajador.getSolicitudes().add(entity);
        }
        
        newCliente.setNombre("Juan");
        newCliente.setMail("juan@hotmail.com");
        newCliente.setUsuario("juans");
        newCliente.setTelefono(1234);
        em.persist(newCliente);
    }
    
    @Test
    public void addSolicitudServicioTest() throws BusinessLogicException {
        SolicitudServicioEntity newSolicitud = factory.manufacturePojo(SolicitudServicioEntity.class);
        newSolicitud.setCliente(newCliente);
        solicitudServicioLogic.createSolicitudServicio(newSolicitud);
        SolicitudServicioEntity solicitudEntity = trabajadorSolicitudServicioLogic.addSolicitudServicio(trabajador.getId(), newSolicitud.getId());
        
        Assert.assertNotNull(solicitudEntity);
        Assert.assertEquals(solicitudEntity.getId(), newSolicitud.getId());
        Assert.assertEquals(solicitudEntity.getDescripcion(), newSolicitud.getDescripcion());
        Assert.assertEquals(solicitudEntity.getEstado(), newSolicitud.getEstado());
        Assert.assertEquals(solicitudEntity.getCalificacion(), newSolicitud.getCalificacion()); 
        Assert.assertEquals(solicitudEntity.getCliente(), newSolicitud.getCliente()); 
    }
    
    @Test
    public void getSolicitudesServicioTest() {
        List<SolicitudServicioEntity> solicitudEntities = trabajadorSolicitudServicioLogic.getServiciosOfrecidos(trabajador.getId());

        Assert.assertEquals(data.size(), solicitudEntities.size());

        for (int i = 0; i < data.size(); i++) {
            Assert.assertTrue(solicitudEntities.contains(data.get(i)));
        }
    }
    
    @Test
    public void getSolicitudServicioTest() throws BusinessLogicException {
        SolicitudServicioEntity solicitudEntity = data.get(0);
        SolicitudServicioEntity newSolicitud = trabajadorSolicitudServicioLogic.getSolicitudServicio(trabajador.getId(), solicitudEntity.getId());
        Assert.assertNotNull(newSolicitud);

        Assert.assertEquals(solicitudEntity.getId(), newSolicitud.getId());
        Assert.assertEquals(solicitudEntity.getDescripcion(), newSolicitud.getDescripcion());
        Assert.assertEquals(solicitudEntity.getEstado(), newSolicitud.getEstado());
        Assert.assertEquals(solicitudEntity.getCalificacion(), newSolicitud.getCalificacion()); 
        Assert.assertEquals(solicitudEntity.getCliente(), newSolicitud.getCliente()); 
    }
    
    @Test
    public void replaceServicioOfrecidoTest() throws BusinessLogicException {
        List<SolicitudServicioEntity> nuevaLista = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            SolicitudServicioEntity entity = factory.manufacturePojo(SolicitudServicioEntity.class);
            entity.setCliente(newCliente);
            solicitudServicioLogic.createSolicitudServicio(entity);
            nuevaLista.add(entity);
        }
        trabajadorSolicitudServicioLogic.replaceSolicitudServicio(trabajador.getId(), nuevaLista);
        List<SolicitudServicioEntity> solicitudesEntities = trabajadorSolicitudServicioLogic.getServiciosOfrecidos(trabajador.getId());
        for (SolicitudServicioEntity aNuevaLista : nuevaLista) {
            Assert.assertTrue(solicitudesEntities.contains(aNuevaLista));
        }
    }
    
    @Test
    public void removeBookTest() {
        for (SolicitudServicioEntity solicitud : data) {
            trabajadorSolicitudServicioLogic.removeSolicitudServicio(trabajador.getId(), solicitud.getId());
        }
        Assert.assertTrue(trabajadorSolicitudServicioLogic.getServiciosOfrecidos(trabajador.getId()).isEmpty());
    }   
    
    
    
    
    
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.test.logic;

import co.edu.uniandes.csw.servicios.ejb.ServicioOfrecidoLogic;
import co.edu.uniandes.csw.servicios.ejb.SolicitudServicioServicioOfrecidosLogic;
import co.edu.uniandes.csw.servicios.entities.ServicioOfrecidoEntity;
import co.edu.uniandes.csw.servicios.entities.SolicitudServicioEntity;
import co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.servicios.persistence.SolicitudServicioPersistence;
import java.util.ArrayList;
import java.util.Collection;
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
 * @author ca.torrese
 */
@RunWith(Arquillian.class)
public class SolicitudServcioServicioOfrecidosLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private SolicitudServicioServicioOfrecidosLogic solicitudServiciosLogic;
    
     @Inject
    private ServicioOfrecidoLogic servicioLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;
    
    private SolicitudServicioEntity solicitud = new SolicitudServicioEntity();
    private List<ServicioOfrecidoEntity> data = new ArrayList<>();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(SolicitudServicioEntity.class.getPackage())
                .addPackage(ServicioOfrecidoEntity.class.getPackage())
                .addPackage(SolicitudServicioServicioOfrecidosLogic.class.getPackage())
                .addPackage(SolicitudServicioPersistence.class.getPackage())
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
        em.createQuery("delete from SolicitudServicioEntity").executeUpdate();
        em.createQuery("delete from ServicioOfrecidoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {

        solicitud = factory.manufacturePojo(SolicitudServicioEntity.class);
        solicitud.setId(1L);
        solicitud.setServicios(new ArrayList<>());
        em.persist(solicitud);

        for (int i = 0; i < 3; i++) {
            ServicioOfrecidoEntity entity = factory.manufacturePojo(ServicioOfrecidoEntity.class);
            em.persist(entity);
            data.add(entity);
            solicitud.getServicios().add(entity);
        }
    }
    
     /**
     * Prueba para asociar un ServicioOfrecido a una solicitud.
     *
     *
     * @throws co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException
     */
    @Test
    public void addServicioTest() throws BusinessLogicException {
        ServicioOfrecidoEntity newServicio = factory.manufacturePojo(ServicioOfrecidoEntity.class);
        servicioLogic.createServicioOfrecido(newServicio);
        ServicioOfrecidoEntity servicioEntity = solicitudServiciosLogic.addServicio(solicitud.getId(), newServicio.getId());
        Assert.assertNotNull(servicioEntity);

        Assert.assertEquals(servicioEntity.getId(), newServicio.getId());
        Assert.assertEquals(servicioEntity.getNombre(), newServicio.getNombre());
        Assert.assertEquals(servicioEntity.getDescripcion(), newServicio.getDescripcion());
        Assert.assertTrue(servicioEntity.getPrecio()== newServicio.getPrecio());
        Assert.assertEquals(servicioEntity.getTipo(), newServicio.getTipo());

        ServicioOfrecidoEntity lastServicio = solicitudServiciosLogic.getServicio(solicitud.getId(), newServicio.getId());

        Assert.assertEquals(lastServicio.getId(), newServicio.getId());
        Assert.assertEquals(lastServicio.getNombre(), newServicio.getNombre());
        Assert.assertEquals(lastServicio.getDescripcion(), newServicio.getDescripcion());
        Assert.assertTrue(lastServicio.getPrecio()== newServicio.getPrecio());
        Assert.assertEquals(lastServicio.getTipo(), newServicio.getTipo());
       
    }
    
    /**
     * Prueba para consultar la lista de Servicios de una Solicitud.
     */
    @Test
    public void getServiciosTest() {
        Collection<ServicioOfrecidoEntity> servicioEntities = solicitudServiciosLogic.getServicios(solicitud.getId());

        Assert.assertEquals(data.size(), servicioEntities.size());

        for (int i = 0; i < data.size(); i++) {
            Assert.assertTrue(servicioEntities.contains(data.get(i)));
        }
    }
    
    /**
     * Prueba para consultar un Servicio1 de una Solicitud.
     *
     * @throws co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException
     */
    @Test
    public void getServicio1Test() throws BusinessLogicException {
        ServicioOfrecidoEntity servicioEntity = data.get(0);
        ServicioOfrecidoEntity servicio = solicitudServiciosLogic.getServicio(solicitud.getId(), servicioEntity.getId());
        Assert.assertNotNull(servicio);

        Assert.assertEquals(servicioEntity.getId(), servicio.getId());
        Assert.assertEquals(servicioEntity.getNombre(), servicio.getNombre());
        Assert.assertEquals(servicioEntity.getDescripcion(), servicio.getDescripcion());
        Assert.assertTrue(servicioEntity.getPrecio()== servicio.getPrecio());
        Assert.assertEquals(servicioEntity.getTipo(), servicio.getTipo());
    }
    
    /**
     * Prueba para consultar un Servicio2 de una Solicitud.
     *
     * @throws co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException
     */
    @Test
    public void getServicio2Test() throws BusinessLogicException {
        ServicioOfrecidoEntity servicioEntity = data.get(1);
        ServicioOfrecidoEntity servicio = solicitudServiciosLogic.getServicio(solicitud.getId(), servicioEntity.getId());
        Assert.assertNotNull(servicio);

        Assert.assertEquals(servicioEntity.getId(), servicio.getId());
        Assert.assertEquals(servicioEntity.getNombre(), servicio.getNombre());
        Assert.assertEquals(servicioEntity.getDescripcion(), servicio.getDescripcion());
        Assert.assertTrue(servicioEntity.getPrecio()== servicio.getPrecio());
        Assert.assertEquals(servicioEntity.getTipo(), servicio.getTipo());
    }
    
    /**
     * Prueba para consultar un Servicio3 de una Solicitud.
     *
     * @throws co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException
     */
    @Test
    public void getServicio3Test() throws BusinessLogicException {
        ServicioOfrecidoEntity servicioEntity = data.get(2);
        ServicioOfrecidoEntity servicio = solicitudServiciosLogic.getServicio(solicitud.getId(), servicioEntity.getId());
        Assert.assertNotNull(servicio);

        Assert.assertEquals(servicioEntity.getId(), servicio.getId());
        Assert.assertEquals(servicioEntity.getNombre(), servicio.getNombre());
        Assert.assertEquals(servicioEntity.getDescripcion(), servicio.getDescripcion());
        Assert.assertTrue(servicioEntity.getPrecio()== servicio.getPrecio());
        Assert.assertEquals(servicioEntity.getTipo(), servicio.getTipo());
    }
    
     /**
     * Prueba para actualizar los Servicios de una Solicitud.
     *
     * @throws co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException
     */
    @Test
    public void replaceServiciosTest() throws BusinessLogicException {
        List<ServicioOfrecidoEntity> nuevaLista = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ServicioOfrecidoEntity entity = factory.manufacturePojo(ServicioOfrecidoEntity.class);
            servicioLogic.createServicioOfrecido(entity);
            nuevaLista.add(entity);
        }
        solicitudServiciosLogic.replaceServicios(solicitud.getId(), nuevaLista);
        Collection<ServicioOfrecidoEntity> servicioEntities = solicitudServiciosLogic.getServicios(solicitud.getId());
        for (ServicioOfrecidoEntity aNuevaLista : nuevaLista) {
            Assert.assertTrue(servicioEntities.contains(aNuevaLista));
        }
    }
    
    /**
     * Prueba desasociar un Servicio con una Solicitud.
     *
     */
    @Test
    public void removeServicioTest() {
        for (ServicioOfrecidoEntity servicio : data) {
            solicitudServiciosLogic.removeServicio(solicitud.getId(), servicio.getId());
        }
        Assert.assertTrue(solicitudServiciosLogic.getServicios(solicitud.getId()).isEmpty());
    }
}

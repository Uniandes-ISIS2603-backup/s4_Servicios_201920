/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.test.logic;

import co.edu.uniandes.csw.servicios.ejb.SolicitudServicioLogic;
import co.edu.uniandes.csw.servicios.entities.ClienteEntity;
import co.edu.uniandes.csw.servicios.entities.ServicioOfrecidoEntity;
import co.edu.uniandes.csw.servicios.entities.SolicitudServicioEntity;
import co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.servicios.persistence.SolicitudServicioPersistence;
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
 * @author ca.torrese
 */
@RunWith(Arquillian.class)
public class SolicitudServicioLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private SolicitudServicioLogic solicitudLogic;
    
    @PersistenceContext
    private EntityManager em;
    
     @Inject
    private UserTransaction utx;

    private List<SolicitudServicioEntity> data = new ArrayList<SolicitudServicioEntity>();
    
    private List<ClienteEntity> clienteData = new ArrayList<ClienteEntity>();
    
    private List<ServicioOfrecidoEntity> servicioData = new ArrayList<ServicioOfrecidoEntity>();
    
     /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(SolicitudServicioEntity.class.getPackage())
                .addPackage(SolicitudServicioLogic.class.getPackage())
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
        em.createQuery("delete from ClienteEntity").executeUpdate();
    }

     /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
         
        
         for (int i = 0; i < 3; i++) {
           ServicioOfrecidoEntity entity = factory.manufacturePojo(ServicioOfrecidoEntity.class);
            em.persist(entity);
            servicioData.add(entity);
         }  
          for (int i = 0; i < 3; i++) {
           ClienteEntity entity = factory.manufacturePojo(ClienteEntity.class);
            em.persist(entity);
            clienteData.add(entity);
         }
         for (int i = 0; i < 3; i++) {
           SolicitudServicioEntity entity = factory.manufacturePojo(SolicitudServicioEntity.class);
           entity.setCliente(clienteData.get(0));
           entity.setServicios(servicioData);
            em.persist(entity);
            data.add(entity);
         }
    }
    
    /**
     * Prueba para crear una Solicitud.
     *
     * @throws co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException
     */
    @Test
    public void createSolicitudTest() throws BusinessLogicException {
        SolicitudServicioEntity newEntity = factory.manufacturePojo(SolicitudServicioEntity.class);
        newEntity.setCliente(clienteData.get(0));
        newEntity.setServicios(servicioData);
        SolicitudServicioEntity result = solicitudLogic.createSolicitudServicio(newEntity);
        Assert.assertNotNull(result);
        SolicitudServicioEntity entity = em.find(SolicitudServicioEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(newEntity.getFechaInicio(), entity.getFechaInicio());
        Assert.assertEquals(newEntity.getEstado(), entity.getEstado());
        Assert.assertEquals(newEntity.getFoto(), entity.getFoto());
    }
    
    /**
     * Prueba para crear una Solicitud con descripcion nula.
     *
     * @throws co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createSolicitudDescripcionNullTest() throws BusinessLogicException {
        SolicitudServicioEntity newEntity = factory.manufacturePojo(SolicitudServicioEntity.class);
        newEntity.setCliente(clienteData.get(0));
        newEntity.setServicios(servicioData);
        newEntity.setDescripcion(null);
        solicitudLogic.createSolicitudServicio(newEntity);
    }
    
     /**
     * Prueba para crear una Solicitud con descripcion vacia.
     *
     * @throws co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createSolicitudDescripcionVaciaTest() throws BusinessLogicException {
        SolicitudServicioEntity newEntity = factory.manufacturePojo(SolicitudServicioEntity.class);
        newEntity.setCliente(clienteData.get(0));
        newEntity.setServicios(servicioData);
        newEntity.setDescripcion("");
        solicitudLogic.createSolicitudServicio(newEntity);
    }
    
     /**
     * Prueba para crear una Solicitud con estado nula.
     *
     * @throws co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createSolicitudEstadoNullTest() throws BusinessLogicException {
        SolicitudServicioEntity newEntity = factory.manufacturePojo(SolicitudServicioEntity.class);
        newEntity.setCliente(clienteData.get(0));
        newEntity.setServicios(servicioData);
        newEntity.setEstado(null);
        solicitudLogic.createSolicitudServicio(newEntity);
    }
    
     /**
     * Prueba para crear una Solicitud con estado vacio.
     *
     * @throws co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createSolicitudEstadoVacioTest() throws BusinessLogicException {
        SolicitudServicioEntity newEntity = factory.manufacturePojo(SolicitudServicioEntity.class);
        newEntity.setCliente(clienteData.get(0));
        newEntity.setServicios(servicioData);
        newEntity.setEstado("");
        solicitudLogic.createSolicitudServicio(newEntity);
    }
    
     /**
     * Prueba para crear una Solicitud con fecha inicial nula.
     *
     * @throws co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createSolicitudFechaNullTest() throws BusinessLogicException {
        SolicitudServicioEntity newEntity = factory.manufacturePojo(SolicitudServicioEntity.class);
        newEntity.setCliente(clienteData.get(0));
        newEntity.setServicios(servicioData);
        newEntity.setFechaInicio(null);
        solicitudLogic.createSolicitudServicio(newEntity);
    }
    
     /**
     * Prueba para crear una Solicitud con Cliente nula.
     *
     * @throws co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createSolicitudClienteNullTest() throws BusinessLogicException {
        SolicitudServicioEntity newEntity = factory.manufacturePojo(SolicitudServicioEntity.class);
        newEntity.setServicios(servicioData);
        newEntity.setCliente(null);
        solicitudLogic.createSolicitudServicio(newEntity);
    }
    
    
     /**
     * Prueba para consultar la lista de Solicitudes.
     */
    @Test
    public void getSolicitudesTest() {
        List<SolicitudServicioEntity> list = solicitudLogic.getSolicitudServicios();
        Assert.assertEquals(data.size(), list.size());
        for (SolicitudServicioEntity entity : list) {
            boolean found = false;
            for (SolicitudServicioEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
     /**
     * Prueba para consultar una Solicitud.
     */
    @Test
    public void getSolicitudTest() {
        SolicitudServicioEntity entity = data.get(0);
        SolicitudServicioEntity resultEntity = solicitudLogic.getSolicitudServicio(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(resultEntity.getId(), entity.getId());
        Assert.assertEquals(resultEntity.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(resultEntity.getFechaInicio(), entity.getFechaInicio());
        Assert.assertEquals(resultEntity.getEstado(), entity.getEstado());
        Assert.assertEquals(resultEntity.getFoto(), entity.getFoto());
    }
    
    /**
     * Prueba para actualizar una Solicitud.
     * 
     * @throws co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException
     */
    @Test
    public void updateSolicitudTest()throws BusinessLogicException {
        SolicitudServicioEntity entity = data.get(0);
        SolicitudServicioEntity pojoEntity = factory.manufacturePojo(SolicitudServicioEntity.class);
        pojoEntity.setId(entity.getId());
        pojoEntity.setServicios(servicioData);
        solicitudLogic.updateSolicitudServicio(pojoEntity.getId(), pojoEntity);
        SolicitudServicioEntity resp = em.find(SolicitudServicioEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getDescripcion(), resp.getDescripcion());
        Assert.assertEquals(pojoEntity.getFechaInicio(), resp.getFechaInicio());
        Assert.assertEquals(pojoEntity.getEstado(), resp.getEstado());
        Assert.assertEquals(pojoEntity.getFoto(), resp.getFoto());
    }
    
     /**
     * Prueba para actualizar una Solicitud con descripcion nula.
     *
     * @throws co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateSolicitudDescripcionNullTest() throws BusinessLogicException {
        SolicitudServicioEntity entity = data.get(0);
        SolicitudServicioEntity pojoEntity = factory.manufacturePojo(SolicitudServicioEntity.class);
        pojoEntity.setDescripcion(null);
        pojoEntity.setId(entity.getId());
        solicitudLogic.updateSolicitudServicio(pojoEntity.getId(), pojoEntity);
    }
    
     /**
     * Prueba para actualizar una Solicitud con descripcion vacia.
     *
     * @throws co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateSolicitudDescripcionVaciaTest() throws BusinessLogicException {
        SolicitudServicioEntity entity = data.get(0);
        SolicitudServicioEntity pojoEntity = factory.manufacturePojo(SolicitudServicioEntity.class);
        pojoEntity.setDescripcion("");
        pojoEntity.setId(entity.getId());
        solicitudLogic.updateSolicitudServicio(pojoEntity.getId(), pojoEntity);
    }
    
     /**
     * Prueba para actualizar una Solicitud con estado nulo.
     *
     * @throws co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateSolicitudEstadoNullTest() throws BusinessLogicException {
        SolicitudServicioEntity entity = data.get(0);
        SolicitudServicioEntity pojoEntity = factory.manufacturePojo(SolicitudServicioEntity.class);
        pojoEntity.setEstado(null);
        pojoEntity.setId(entity.getId());
        solicitudLogic.updateSolicitudServicio(pojoEntity.getId(), pojoEntity);
    }
    
     /**
     * Prueba para actualizar una Solicitud con estado vacio.
     *
     * @throws co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateSolicitudEstadoVacioTest() throws BusinessLogicException {
        SolicitudServicioEntity entity = data.get(0);
        SolicitudServicioEntity pojoEntity = factory.manufacturePojo(SolicitudServicioEntity.class);
        pojoEntity.setEstado("");
        pojoEntity.setId(entity.getId());
        solicitudLogic.updateSolicitudServicio(pojoEntity.getId(), pojoEntity);
    }
    
     /**
     * Prueba para actualizar una Solicitud con fecha inicial nula.
     *
     * @throws co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateSolicitudFechaInicioNullTest() throws BusinessLogicException {
        SolicitudServicioEntity entity = data.get(0);
        SolicitudServicioEntity pojoEntity = factory.manufacturePojo(SolicitudServicioEntity.class);
        pojoEntity.setFechaInicio(null);
        pojoEntity.setId(entity.getId());
        solicitudLogic.updateSolicitudServicio(pojoEntity.getId(), pojoEntity);
    }

    /**
     * Prueba para eliminar una Solicitud.
     *
     */
    @Test
    public void deleteSolicitudTest()  {
        SolicitudServicioEntity entity = data.get(1);
        solicitudLogic.deleteSolicitudServicio(entity.getId());
        SolicitudServicioEntity deleted = em.find(SolicitudServicioEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.test.logic;

import co.edu.uniandes.csw.servicios.ejb.ServicioOfrecidoLogic;
import co.edu.uniandes.csw.servicios.ejb.TrabajadorServicioOfrecidoLogic;
import co.edu.uniandes.csw.servicios.entities.ServicioOfrecidoEntity;
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
 * @author Violeta Rodríguez
 */

@RunWith(Arquillian.class)
public class TrabajadorServicioOfrecidoLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    
    @Inject
    private TrabajadorServicioOfrecidoLogic trabajadorServicioOfrecidoLogic;

    @Inject
    private ServicioOfrecidoLogic servicioOfrecidoLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;
    
    /**
     * Atributo que alamcena la información de un trabajador. 
     */
    private TrabajadorEntity trabajador = new TrabajadorEntity();
    
    /**
     * Atributo que guarda la lsita de serivico que ofrece el trabjador. 
     */
    private List<ServicioOfrecidoEntity> data = new ArrayList<>();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(TrabajadorEntity.class.getPackage())
                .addPackage(ServicioOfrecidoEntity.class.getPackage())
                .addPackage(TrabajadorServicioOfrecidoLogic.class.getPackage())
                .addPackage(TrabajadorPersistence.class.getPackage())
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
        em.createQuery("delete from TrabajadorEntity").executeUpdate();
        em.createQuery("delete from ServicioOfrecidoEntity").executeUpdate();
    }
    
        /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {

        trabajador = factory.manufacturePojo(TrabajadorEntity.class);
        trabajador.setId(1L);
        trabajador.setServicios(new ArrayList<>());
        em.persist(trabajador);

        for (int i = 0; i < 3; i++) {
            ServicioOfrecidoEntity entity = factory.manufacturePojo(ServicioOfrecidoEntity.class);
            em.persist(entity);
            data.add(entity);
            trabajador.getServicios().add(entity);
        }
    }
    
     /**
     * Prueba para asociar un servicio a un trabajador.
     *
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test
    public void addServicioOfrecidoTest() throws BusinessLogicException {
        ServicioOfrecidoEntity newService = factory.manufacturePojo(ServicioOfrecidoEntity.class);
        newService.setTipo("Plomeria");
        servicioOfrecidoLogic.createServicioOfrecido(newService);
        ServicioOfrecidoEntity servicioEntity=trabajadorServicioOfrecidoLogic.addServicioOfrecido(trabajador.getId(), newService.getId());
        
        Assert.assertNotNull(servicioEntity);
        
        Assert.assertEquals(servicioEntity.getId(), newService.getId());
        Assert.assertEquals(servicioEntity.getNombre(), newService.getNombre());
        Assert.assertEquals(servicioEntity.getDescripcion(), newService.getDescripcion());
        Assert.assertEquals(servicioEntity.getTipo(), newService.getTipo());
        Assert.assertEquals(servicioEntity.getPrecio(), newService.getPrecio(), 0.1); 
    }
    
    /**
     * Prueba para consultar la lista de servicioOfrecidos de un trabajador.
     */
    @Test
    public void getServicisoOfrecidosTest() {
        List<ServicioOfrecidoEntity> serviciosEntities = trabajadorServicioOfrecidoLogic.getServiciosOfrecidos(trabajador.getId());

        Assert.assertEquals(data.size(), serviciosEntities.size());

        for (int i = 0; i < data.size(); i++) {
            Assert.assertTrue(serviciosEntities.contains(data.get(0)));
        }
    }
    
     /**
     * Prueba para consultar un servicio de un trabjador.
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test
    public void getServicioOfrecidoTest() throws BusinessLogicException {
        ServicioOfrecidoEntity servicioEntity = data.get(0);
        ServicioOfrecidoEntity servicio = trabajadorServicioOfrecidoLogic.getServicioOfrecido(trabajador.getId(), servicioEntity.getId());
        Assert.assertNotNull(servicio);

        Assert.assertEquals(servicioEntity.getId(), servicio.getId());
        Assert.assertEquals(servicioEntity.getNombre(), servicio.getNombre());
        Assert.assertEquals(servicioEntity.getDescripcion(), servicio.getDescripcion());
        Assert.assertEquals(servicioEntity.getTipo(), servicio.getTipo());
        Assert.assertEquals(servicioEntity.getPrecio(), servicio.getPrecio(),0.1);
    }
    
    /**
     * Prueba para actualizar los servicioOfrecidos de un trabajador.
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test
    public void replaceServicioOfrecidoTest() throws BusinessLogicException {
        List<ServicioOfrecidoEntity> nuevaLista = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ServicioOfrecidoEntity entity = factory.manufacturePojo(ServicioOfrecidoEntity.class);
            servicioOfrecidoLogic.createServicioOfrecido(entity);
            nuevaLista.add(entity);
        }
        trabajadorServicioOfrecidoLogic.replaceServicioOfrecido(trabajador.getId(), nuevaLista);
        List<ServicioOfrecidoEntity> serviciosEntities = trabajadorServicioOfrecidoLogic.getServiciosOfrecidos (trabajador.getId());
        for (ServicioOfrecidoEntity aNuevaLista : nuevaLista) {
            Assert.assertTrue(serviciosEntities.contains(aNuevaLista));
        }
    }
    
     /**
     * Prueba desasociar un servicioOfrecido con un trabajador.
     *
     */
    @Test
    public void removeBookTest() {
        for (ServicioOfrecidoEntity servicio : data) {
            trabajadorServicioOfrecidoLogic.removeServicioOfrecido(trabajador.getId(), servicio.getId());
        }
        Assert.assertTrue(trabajadorServicioOfrecidoLogic.getServiciosOfrecidos(trabajador.getId()).isEmpty());
    }    
}

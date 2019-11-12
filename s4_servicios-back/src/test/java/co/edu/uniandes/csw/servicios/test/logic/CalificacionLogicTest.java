/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.test.logic;

import co.edu.uniandes.csw.servicios.ejb.CalificacionLogic;
import co.edu.uniandes.csw.servicios.entities.CalificacionEntity;
import co.edu.uniandes.csw.servicios.entities.SolicitudServicioEntity;
import co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.servicios.persistence.CalificacionPersistence;
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
 * Prueba de logica de Calificacion
 * 
 * @author ca.torrese
 */

@RunWith(Arquillian.class)
public class CalificacionLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private CalificacionLogic calificacionLogic;
    
    @PersistenceContext
    private EntityManager em;
    
     @Inject
    private UserTransaction utx;

    private List<CalificacionEntity> data = new ArrayList<CalificacionEntity>();
    
    private List<SolicitudServicioEntity> solicitudData = new ArrayList();
    
     /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CalificacionEntity.class.getPackage())
                .addPackage(CalificacionLogic.class.getPackage())
                .addPackage(CalificacionPersistence.class.getPackage())
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
        em.createQuery("delete from CalificacionEntity").executeUpdate();
        em.createQuery("delete from SolicitudServicioEntity").executeUpdate();
    }

     /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
         
         for(int i = 0; i < 3; i++){
             SolicitudServicioEntity entity = factory.manufacturePojo(SolicitudServicioEntity.class);
             em.persist(entity);
             solicitudData.add(entity);
         }
         for (int i = 0; i < 3; i++) {
            CalificacionEntity entity = factory.manufacturePojo(CalificacionEntity.class);
            entity.setSolicitud(solicitudData.get(0));
            em.persist(entity);
            data.add(entity);
         }  
    }
    
     /**
     * Prueba para crear un Calificacion.
     *
     * @throws co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException
     */
    @Test
    public void createCalificacionTest() throws BusinessLogicException {
        CalificacionEntity newEntity = factory.manufacturePojo(CalificacionEntity.class);
        newEntity.setSolicitud(solicitudData.get(0));
        CalificacionEntity result = calificacionLogic.createCalificacion(newEntity);
        Assert.assertNotNull(result);
        CalificacionEntity entity = em.find(CalificacionEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getComentario(), entity.getComentario());
        Assert.assertTrue(newEntity.getPuntaje() == entity.getPuntaje());
        Assert.assertEquals(newEntity.getSolicitud(), entity.getSolicitud());

    }
    
     /**
     * Prueba para crear un Calificacion con puntaje mayor al máximo permitido.
     *
     * @throws co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createCalificacionPuntajeMayorTest() throws BusinessLogicException {
        CalificacionEntity newEntity = factory.manufacturePojo(CalificacionEntity.class);
        newEntity.setSolicitud(solicitudData.get(0));
        newEntity.setPuntaje((int)(Math.random()*5) + 6 );
        calificacionLogic.createCalificacion(newEntity);
    }
    
     /**
     * Prueba para crear un Calificacion con puntaje menor al mínimo permitido.
     *
     * @throws co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createCalificacionPuntajeMenorTest() throws BusinessLogicException {
        CalificacionEntity newEntity = factory.manufacturePojo(CalificacionEntity.class);
        newEntity.setSolicitud(solicitudData.get(0));
        newEntity.setPuntaje(-(int)(Math.random()*5));
        calificacionLogic.createCalificacion(newEntity);
    }
    
 
      /**
     * Prueba para crear una Calificacion con solicitud en null.
     *
     * @throws co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createCalificacionSolicitudNullTest() throws BusinessLogicException{
        CalificacionEntity newEntity = factory.manufacturePojo(CalificacionEntity.class);
        newEntity.setSolicitud(null);
        calificacionLogic.createCalificacion(newEntity);
    }

    /**
     * Prueba para consultar la lista de Calificacions.
     */
    @Test
    public void getCalificacionesTest() {
        List<CalificacionEntity> list = calificacionLogic.getCalificaciones();
        Assert.assertEquals(data.size(), list.size());
        for (CalificacionEntity entity : list) {
            boolean found = false;
            for (CalificacionEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
     /**
     * Prueba para consultar un Calificacion.
     */
    @Test
    public void getCalificacionTest() {
        CalificacionEntity entity = data.get(0);
        CalificacionEntity resultEntity = calificacionLogic.getCalificacion(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getPuntaje(), resultEntity.getPuntaje());
        Assert.assertEquals(entity.getComentario(), resultEntity.getComentario());
        Assert.assertEquals(entity.getSolicitud(), resultEntity.getSolicitud());

    }
    
    /**
     * Prueba para actualizar un Calificacion.
     * 
     * @throws co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException
     */
    @Test
    public void updateCalificacionTest() throws BusinessLogicException {
        CalificacionEntity entity = data.get(0);
        CalificacionEntity pojoEntity = factory.manufacturePojo(CalificacionEntity.class);
        pojoEntity.setId(entity.getId());
        pojoEntity.setSolicitud(solicitudData.get(0));
        calificacionLogic.updateCalificacion(pojoEntity.getId(), pojoEntity);
        CalificacionEntity resp = em.find(CalificacionEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getComentario(), resp.getComentario());
        Assert.assertEquals(pojoEntity.getPuntaje(), resp.getPuntaje());
        Assert.assertEquals(pojoEntity.getSolicitud(), resp.getSolicitud());
    }
    
    /**
     * Prueba para actualizar una Calificacion con puntaje invalido.
     *
     * @throws co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateCalificacionPuntajeInvalidoTest() throws BusinessLogicException {
        CalificacionEntity entity = data.get(0);
        CalificacionEntity pojoEntity = factory.manufacturePojo(CalificacionEntity.class);
        pojoEntity.setPuntaje(-(int)(Math.random()*5));
        pojoEntity.setId(entity.getId());
        calificacionLogic.updateCalificacion(pojoEntity.getId(), pojoEntity);
    }
    
    /**
     * Prueba para actualizar una Calificacion con puntaje menor al valido.
     *
     * @throws co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateCalificacionPuntajeInvalido2Test() throws BusinessLogicException {
        CalificacionEntity entity = data.get(0);
        CalificacionEntity pojoEntity = factory.manufacturePojo(CalificacionEntity.class);
        pojoEntity.setPuntaje((int)(Math.random()*5) + 6);
        pojoEntity.setId(entity.getId());
        calificacionLogic.updateCalificacion(pojoEntity.getId(), pojoEntity);
    }
    
    /**
     * Prueba para actualizar una Calificacion con Solicitud null.
     *
     * @throws co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateCalificacionSolicitudNullTest() throws BusinessLogicException {
        CalificacionEntity entity = data.get(0);
        CalificacionEntity pojoEntity = factory.manufacturePojo(CalificacionEntity.class);
        pojoEntity.setSolicitud(null);
        pojoEntity.setId(entity.getId());
        calificacionLogic.updateCalificacion(pojoEntity.getId(), pojoEntity);
    }
    
    /**
     * Prueba para eliminar un Calificacion.
     *
     */
    @Test
    public void deleteCalificacionTest()  {
        CalificacionEntity entity = data.get(1);
        calificacionLogic.deleteCalificacion(entity.getId());
        CalificacionEntity deleted = em.find(CalificacionEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
}

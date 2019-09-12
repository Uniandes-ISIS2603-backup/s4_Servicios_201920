/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.test.logic;

import co.edu.uniandes.csw.servicios.ejb.CalificacionLogic;
import co.edu.uniandes.csw.servicios.entities.CalificacionEntity;
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
    }

     /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
         
         for (int i = 0; i < 3; i++) {
            CalificacionEntity entity = factory.manufacturePojo(CalificacionEntity.class);
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
    public void createEditorialTest() throws BusinessLogicException {
        CalificacionEntity newEntity = factory.manufacturePojo(CalificacionEntity.class);
        CalificacionEntity result = calificacionLogic.createCalificacion(newEntity);
        Assert.assertNotNull(result);
        CalificacionEntity entity = em.find(CalificacionEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getComentario(), entity.getComentario());
        Assert.assertTrue(newEntity.getPuntaje() == entity.getPuntaje());
    }
    
     /**
     * Prueba para crear un Calificacion con puntaje mayor al máximo permitido.
     *
     * @throws co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createCalificacionPuntajeMayorTest() throws BusinessLogicException {
        CalificacionEntity newEntity = factory.manufacturePojo(CalificacionEntity.class);
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
        newEntity.setPuntaje(-(int)(Math.random()));
        calificacionLogic.createCalificacion(newEntity);
    }

    
}

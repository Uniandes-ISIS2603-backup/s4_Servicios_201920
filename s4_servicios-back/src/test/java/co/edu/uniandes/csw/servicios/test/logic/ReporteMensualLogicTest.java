/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.test.logic;

import co.edu.uniandes.csw.servicios.ejb.ReporteMensualLogic;
import co.edu.uniandes.csw.servicios.entities.ReporteMensualEntity;
import co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.servicios.persistence.ReporteMensualPersistence;

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
public class ReporteMensualLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();
     
    @Inject
    private ReporteMensualLogic logic ;
    
    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;
    
     private List<ReporteMensualEntity> data = new ArrayList<ReporteMensualEntity>();
    
    
      /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ReporteMensualEntity.class.getPackage())
                .addPackage(ReporteMensualLogic.class.getPackage())
                .addPackage(ReporteMensualPersistence.class.getPackage())
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
        em.createQuery("delete from ReporteMensualEntity").executeUpdate();

    }
    
      /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ReporteMensualEntity editorial = factory.manufacturePojo(ReporteMensualEntity.class);
            em.persist(editorial);
            data.add(editorial);
        }
    }
    
    
    /**
     * Prueba para crear un ReporteMensual
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    
    @Test
    public void createReporteMensualTest()
    {
        
        ReporteMensualEntity newEntity = factory.manufacturePojo(ReporteMensualEntity.class);

    }
}

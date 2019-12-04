/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.test.logic;

import co.edu.uniandes.csw.servicios.ejb.PagoTarjetaLogic;
import co.edu.uniandes.csw.servicios.entities.PagoTarjetaEntity;
import co.edu.uniandes.csw.servicios.persistence.PagoTarjetaPersistence;
import co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.junit.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * Pruebas de logica de los clientes
 *
 * @author ISIS2603
 */
@RunWith(Arquillian.class)
public class PagoTarjetaLogicTest {
    
    private static final Logger LOGGER = Logger.getLogger(PagoTarjetaLogic.class.getName());
    
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private PagoTarjetaLogic pagoTarjetaLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<PagoTarjetaEntity> data = new ArrayList<>();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(PagoTarjetaEntity.class.getPackage())
                .addPackage(PagoTarjetaLogic.class.getPackage())
                .addPackage(PagoTarjetaPersistence.class.getPackage())
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
        em.createQuery("delete from PagoTarjetaEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            PagoTarjetaEntity entity = factory.manufacturePojo(PagoTarjetaEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
    
    /**
     * Prueba para crear un Pago de Tarjeta.
     * @throws co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException
     */
    @Test
    public void createPagoTarjetaTest() throws BusinessLogicException {
        PagoTarjetaEntity newEntity = factory.manufacturePojo(PagoTarjetaEntity.class);
        PagoTarjetaEntity result = pagoTarjetaLogic.createPagoTarjeta(newEntity);
        Assert.assertNotNull(result);
        PagoTarjetaEntity entity = em.find(PagoTarjetaEntity.class, result.getId());
        Assert.assertEquals(newEntity.getBanco(), entity.getBanco());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getNumTarjeta(), entity.getNumTarjeta());
        Assert.assertEquals(newEntity.getCsv(), entity.getCsv());
    }
    
    /**
     * Prueba para consultar la lista de Tarjetas.
     */
    @Test
    public void getPagoTarjetasTest() {
        List<PagoTarjetaEntity> list = pagoTarjetaLogic.getTarjetas();
        Assert.assertEquals(data.size(), list.size());
        for (PagoTarjetaEntity entity : list) {
            boolean found = false;
            for (PagoTarjetaEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    /**
     * Prueba para consultar un PagoTarjeta específico.
     */
    @Test
    public void getPagoTarjetaTest() {
        PagoTarjetaEntity entity = data.get(0);
        PagoTarjetaEntity resultEntity = null;
        try{
            resultEntity = pagoTarjetaLogic.getTarjeta(entity.getId());
        } catch(Exception e) {
            e.printStackTrace();
        }
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getBanco(), resultEntity.getBanco());
        Assert.assertEquals(entity.getNumTarjeta(), resultEntity.getNumTarjeta());
        Assert.assertEquals(entity.getCsv(), resultEntity.getCsv());
    }
    
    /**
     * Prueba para actualizar un PagoTarjeta.
     */
    @Test
    public void updatePagoTarjetaTest() {
        PagoTarjetaEntity entity = data.get(0);
        PagoTarjetaEntity pojoEntity = factory.manufacturePojo(PagoTarjetaEntity.class);
        pojoEntity.setId(entity.getId());
        pagoTarjetaLogic.updatePagoTarjeta(pojoEntity.getId(), pojoEntity);

        PagoTarjetaEntity resp = em.find(PagoTarjetaEntity.class, entity.getId());

        Assert.assertNotNull(resp);
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getBanco(), resp.getBanco());
        Assert.assertEquals(pojoEntity.getNumTarjeta(), resp.getNumTarjeta());
        Assert.assertEquals(pojoEntity.getCsv(), resp.getCsv());
    }

    /**
     * Prueba para eliminar la tarjeta de un cliente
     * @throws co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException
     */
    @Test
    public void deletePagoTarjetaTest() throws BusinessLogicException {
        
        PagoTarjetaEntity entity = data.get(1);
        pagoTarjetaLogic.deletePagoTarjeta(entity.getId());
        PagoTarjetaEntity deleted = em.find(PagoTarjetaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
}

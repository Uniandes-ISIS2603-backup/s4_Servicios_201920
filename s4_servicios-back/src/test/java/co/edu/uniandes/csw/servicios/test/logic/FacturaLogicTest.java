/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.test.logic;

import co.edu.uniandes.csw.servicios.ejb.FacturaLogic;
import co.edu.uniandes.csw.servicios.entities.FacturaEntity;
import co.edu.uniandes.csw.servicios.entities.PagoTarjetaEntity;
import co.edu.uniandes.csw.servicios.entities.SolicitudServicioEntity;
import co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.servicios.persistence.FacturaPersistence;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @author Juan Lucas Ibarra
 */
@RunWith(Arquillian.class)
public class FacturaLogicTest {

    private static final Logger LOGGER = Logger.getLogger(FacturaLogicTest.class.getName());

    private PodamFactory podam = new PodamFactoryImpl();

    @Inject
    private FacturaLogic facturaLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<FacturaEntity> data = new ArrayList<>();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(FacturaLogic.class.getPackage())
                .addPackage(FacturaEntity.class.getPackage())
                .addPackage(FacturaPersistence.class.getPackage())
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

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            FacturaEntity entity = podam.manufacturePojo(FacturaEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }

    private void clearData() {
        em.createQuery("delete from FacturaEntity").executeUpdate();
    }

    @Test
    public void createFactura() throws BusinessLogicException {
        FacturaEntity newFactura = podam.manufacturePojo(FacturaEntity.class);
        newFactura.setDuracion(100);
        newFactura.setPrecioMateriales(100);
        newFactura.setPrimerPago(true);
        newFactura.setPagada(false);
        newFactura.setSolicitud(new SolicitudServicioEntity());
        newFactura.setTarjetaPago(new PagoTarjetaEntity());
        FacturaEntity result = facturaLogic.createFactura(newFactura);
        Assert.assertNotNull(result);
        FacturaEntity entity = em.find(FacturaEntity.class, result.getId());
        Assert.assertEquals(entity.getDuracion(), result.getDuracion());
        Assert.assertEquals(entity.getSolicitud(), entity.getSolicitud());
    }

    @Test(expected = BusinessLogicException.class)
    public void createFacturaDuracionTest() throws BusinessLogicException {
        FacturaEntity newEntity = podam.manufacturePojo(FacturaEntity.class);
        newEntity.setDuracion(-1);
        FacturaEntity result = facturaLogic.createFactura(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createFacturaPrecioTest() throws BusinessLogicException {
        FacturaEntity newEntity = podam.manufacturePojo(FacturaEntity.class);
        newEntity.setPrecioMateriales(-1);
        FacturaEntity result = facturaLogic.createFactura(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createFacturaFechaTest() throws BusinessLogicException {
        FacturaEntity newEntity = podam.manufacturePojo(FacturaEntity.class);
        newEntity.setFecha(null);
        FacturaEntity result = facturaLogic.createFactura(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createFacturaPrimerPagoTest() throws BusinessLogicException {
        FacturaEntity newEntity = podam.manufacturePojo(FacturaEntity.class);
        newEntity.setPrimerPago(false);
        FacturaEntity result = facturaLogic.createFactura(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createFacturaPagadaTest() throws BusinessLogicException {
        FacturaEntity newEntity = podam.manufacturePojo(FacturaEntity.class);
        newEntity.setPagada(true);
        FacturaEntity result = facturaLogic.createFactura(newEntity);
    }

    @Test
    public void getAuthorsTest() {
        List<FacturaEntity> list = facturaLogic.getFacturas();
        Assert.assertEquals(data.size(), list.size());
        for (FacturaEntity entity : list) {
            boolean found = false;
            for (FacturaEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    @Test
    public void getAuthorTest() {
        FacturaEntity entity = data.get(0);
        FacturaEntity resultEntity = facturaLogic.getFactura(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getDuracion(), resultEntity.getDuracion());
        Assert.assertEquals(entity.getSolicitud(), resultEntity.getSolicitud());
    }

    @Test
    public void updateAuthorTest() throws BusinessLogicException {
        FacturaEntity entity = data.get(0);
        FacturaEntity pojoEntity = podam.manufacturePojo(FacturaEntity.class);
        pojoEntity.setId(entity.getId());
        pojoEntity.setSolicitud(new SolicitudServicioEntity());
        pojoEntity.getSolicitud().setEstado("En curso");
        facturaLogic.updateFactura(pojoEntity.getId(), pojoEntity);
        FacturaEntity resp = em.find(FacturaEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getDuracion(), resp.getDuracion());
    }

    @Test
    public void deleteAuthorTest() throws BusinessLogicException {
        FacturaEntity entity = data.get(0);
        facturaLogic.deleteFactura(entity.getId());
        FacturaEntity deleted = em.find(FacturaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    @Test(expected = BusinessLogicException.class)
    public void updateFacturaFinalizadoTest() throws BusinessLogicException {
        FacturaEntity entity = data.get(0);
        FacturaEntity pojoEntity = podam.manufacturePojo(FacturaEntity.class);
        pojoEntity.setSolicitud(new SolicitudServicioEntity());
        pojoEntity.getSolicitud().setEstado("Finalizado");
        pojoEntity.setId(entity.getId());
        facturaLogic.updateFactura(pojoEntity.getId(), pojoEntity);
    }
}

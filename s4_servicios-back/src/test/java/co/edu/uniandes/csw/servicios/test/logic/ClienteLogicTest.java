/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.test.logic;

import co.edu.uniandes.csw.servicios.ejb.ClienteLogic;
import co.edu.uniandes.csw.servicios.entities.ClienteEntity;
import co.edu.uniandes.csw.servicios.persistence.ClientePersistence;
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
public class ClienteLogicTest {
    
    private static final Logger LOGGER = Logger.getLogger(ClienteLogic.class.getName());
    
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private ClienteLogic clienteLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<ClienteEntity> data = new ArrayList<>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ClienteEntity.class.getPackage())
                .addPackage(ClienteLogic.class.getPackage())
                .addPackage(ClientePersistence.class.getPackage())
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
        em.createQuery("delete from ClienteEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ClienteEntity entity = factory.manufacturePojo(ClienteEntity.class);
            em.persist(entity);
            entity.setServicios(new ArrayList<>());
            data.add(entity);
        }
//        ClienteEntity cliente = data.get(2);
//        SolicitudServicioEntity entity = factory.manufacturePojo(SolicitudServicioEntity.class);
//        ArrayList<SolicitudServicioEntity> arreglo = new ArrayList<>();
//        arreglo.add(entity);
//        cliente.setServicios(arreglo);
//        em.merge(cliente);
    }

    /**
     * Prueba para crear un Cliente.
     * @throws co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException
     */
    @Test
    public void createClienteTest() throws BusinessLogicException {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        ClienteEntity result = clienteLogic.createCliente(newEntity);
        Assert.assertNotNull(result);
        ClienteEntity entity = em.find(ClienteEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
        Assert.assertEquals(newEntity.getMail(), entity.getMail());
    }

    /**
     * Prueba para consultar la lista de Clientes.
     */
    @Test
    public void getClientesTest() {
        List<ClienteEntity> list = clienteLogic.getClientes();
        Assert.assertEquals(data.size(), list.size());
        for (ClienteEntity entity : list) {
            boolean found = false;
            for (ClienteEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Cliente.
     */
    @Test
    public void getClienteTest() {
        ClienteEntity entity = data.get(0);
        ClienteEntity resultEntity = null;
        try{
            resultEntity = clienteLogic.getCliente(entity.getId());
        } catch(Exception e) {
            e.printStackTrace();
        }
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getNombre(), resultEntity.getNombre());
        Assert.assertEquals(entity.getDireccion(), resultEntity.getDireccion());
    }

    /**
     * Prueba para actualizar un Cliente.
     */
    @Test
    public void updateClienteTest() {
        ClienteEntity entity = data.get(0);
        ClienteEntity pojoEntity = factory.manufacturePojo(ClienteEntity.class);

        pojoEntity.setId(entity.getId());

        clienteLogic.updateCliente(pojoEntity.getId(), pojoEntity);

        ClienteEntity resp = em.find(ClienteEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getNombre(), resp.getNombre());
        Assert.assertEquals(pojoEntity.getDireccion(), resp.getDireccion());
    }

    /**
     * Prueba para eliminar un Cliente
     *
     * @throws co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException
     */
    @Test
    public void deleteClienteTest() throws BusinessLogicException {
        
        ClienteEntity entity = data.get(1);
        LOGGER.log(Level.INFO, "CANTIDAD SIZE SERVICIOS.SIZE = {0}", entity.getServicios().size());
        clienteLogic.deleteCliente(entity.getId());
        ClienteEntity deleted = em.find(ClienteEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
    
    /**
     * Prueba para crear un cliente con un nombre = null 
     * @throws co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createClienteSinNombreTest() throws BusinessLogicException {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setNombre(null);
        ClienteEntity result = clienteLogic.createCliente(newEntity);
    }
    
    /**
     * Prueba para crear un cliente con un nombre vacío
     * @throws co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createClienteConNombreVacio() throws BusinessLogicException {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setNombre("");
        ClienteEntity result = clienteLogic.createCliente(newEntity);
    }
    
    /**
     * Prueba para crear un cliente con un correo que no cumple el formato 
     * @throws co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createClienteConMalCorreoTest() throws BusinessLogicException {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setMail("mal correito");
        ClienteEntity result = clienteLogic.createCliente(newEntity);
    }
    
    /**
     * Prueba para crear un cliente con un correo = null 
     * @throws co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createClienteSinCorreoTest() throws BusinessLogicException {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setMail(null);
        ClienteEntity result = clienteLogic.createCliente(newEntity);
    }
    
    
    /**
     * Prueba para crear un cliente con un mal telefono
     * @throws co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createClienteConMalTelefonoTest() throws BusinessLogicException {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setTelefono(-1);
        ClienteEntity result = clienteLogic.createCliente(newEntity);
    }
    
    
    /**
     * Prueba para crear un cliente con un teléfono = null
     * @throws co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createClienteSinTelefonoTest() throws BusinessLogicException {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setTelefono(null);
        ClienteEntity result = clienteLogic.createCliente(newEntity);
    }
    
    /**
     * Prueba para crear un cliente con un usuario vacío
     * @throws co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createClienteConMalUsuarioTest() throws BusinessLogicException {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setUsuario("");
        ClienteEntity result = clienteLogic.createCliente(newEntity);
    }
    
    
    /**
     * Prueba para crear un cliente con un usuario = null
     * @throws co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createClienteSinUsuarioTest() throws BusinessLogicException {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setUsuario(null);
        ClienteEntity result = clienteLogic.createCliente(newEntity);
    }
    
     /**
     * Prueba para crear un Cliente con usuario existente.
     *
     * @throws co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createClienteTestConUsuarioExistente() throws BusinessLogicException {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setUsuario(data.get(0).getUsuario());
        clienteLogic.createCliente(newEntity);
    }
    
    /**
     * Prueba para obtener un cliente que no existe. 
     * @throws co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void getClienteQueNoExisteTest() throws BusinessLogicException {
        ClienteEntity resultEntity = clienteLogic.getCliente((long)-1);
    }

    /**
     * Prueba para obtener un cliente por Usuario. 
     * @throws co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException
     */
    @Test
    public void getClientePorUsuarioTest() throws BusinessLogicException {
        ClienteEntity entity = data.get(0);
        ClienteEntity resultEntity = clienteLogic.getClientePorUsuario(entity.getUsuario(), entity.getContrasena());        
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getNombre(), resultEntity.getNombre());
        Assert.assertEquals(entity.getDireccion(), resultEntity.getDireccion());
    }
    
    /**
     * Prueba para obtener un cliente con un usuario que no existe. 
     * @throws co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void getClientePorUsuarioTestConUsuarioNoExistente() throws BusinessLogicException {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setUsuario(null);
        ClienteEntity result = clienteLogic.getClientePorUsuario(newEntity.getUsuario(), newEntity.getContrasena());
    }
    
    /**
     * Prueba para obtener un cliente con una contraseña que no corresponde. 
     * @throws co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void getClientePorUsuarioTestContrasenaIncorrecta() throws BusinessLogicException {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setContrasena("Ahora esta mal: ");
        ClienteEntity result = clienteLogic.getClientePorUsuario(newEntity.getUsuario(), newEntity.getContrasena());
    }
}

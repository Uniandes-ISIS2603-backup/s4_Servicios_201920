/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.ejb;

import co.edu.uniandes.csw.servicios.entities.ServicioOfrecidoEntity;
import co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.servicios.persistence.ServicioOfrecidoPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Violeta Rodriguez
 */
@Stateless
public class ServicioOfrecidoLogic 
{
    @Inject
    private ServicioOfrecidoPersistence persistencia;
    
    private final String[] tipos ={"Pintura", "Plomeria", "Electricidad", "Cerrajeria", "Ventaneria", "Carpinteria", "Aseo"};
    
    
    /**
     * Guardar un nuevo libro
     *
     * @param servicioOfredicokEntity La entidad de tipo serivcioOfrecido del nuevo serivcio a persistir.
     * @return La entidad luego de persistirla
     * @throws BusinessLogicException Si el ISBN es inválido o ya existe en la
     * persistencia.
     */
    public ServicioOfrecidoEntity createServicioOfrecido(ServicioOfrecidoEntity newService) throws BusinessLogicException
    {
        // Validamos que no haya otro servicio con el mismo nnombre:
        ServicioOfrecidoEntity old = null;//persistencia.findByName(newService.getNombre());
        String nombre = newService.getNombre();
        List<ServicioOfrecidoEntity> list = persistencia.findAll();
        for(ServicioOfrecidoEntity i: list)
        {
            if(i.getNombre().equals(nombre))
                old=i;
        }

        String tipo = newService.getTipo();

        if(old != null ) 
        {
            throw new BusinessLogicException("Ya existe un servicio con ese nombre");
        }
        else if(!isInType(tipo))
        {
             throw new BusinessLogicException("El tipo del servicio ofrecido que se queire agregar, no se encuentra dentro de la oferta");   
        }
                
        else
        {
          
            newService=persistencia.create(newService);
        }
        
        return newService;      
    }
    
    /**
     * Método que valida si un valor dado, está dentro de lso tipos de servicios establecidos
     * @param tipo cadena de caracteres de la que se quiere saber si es un tipo.
     * @return true si el valor pertenece a lsotipos dados, false de lo contrario. 
     */
    public boolean isInType(String tipo)
    {
        int i=0;
        boolean isIn= false;
        while ( i < tipos.length  && !isIn)
        {
            if(tipo.equalsIgnoreCase(tipos[i]))
            {
                isIn=true;
            }
            i++;
        }
        return isIn;
    }
    
    /**
     * Método que busca todos los ervicios ofrecidos.
     * @return Lista de todos los servicio ofrecidos existentes.
     */
   public List<ServicioOfrecidoEntity> getServiciosOfrecidos()
   {
       List<ServicioOfrecidoEntity> services = persistencia.findAll();
       return  services;
   }
   
   /**
    * Devuelve el servicio ofrecido con el id dada
    * @param idServicio identificador del servicio que sea dea obtenr
    * @return  Enttidad del serivcioOfrecido
    */
   public ServicioOfrecidoEntity getServicioOfrecido(Long idServicio)
   {
        return persistencia.find(idServicio);
   }
   
    /**
    * Devuelve los serivcio con el tipo dado
    * @param type identificador del servicio que sea dea obtenr
    * @return  Enttidad del serivcioOfrecido
    */
   public List<ServicioOfrecidoEntity> getServiciosOfrecidosByType(String type)
   {
        return persistencia.findByType(type);
   }
   
   /**
    * Actualiza el servicio ofrecido con el id dada con la infroamci'on dada
    * @param servicioId identificador del servicio que se quiere actualizar.
    * @param servicioEntity informaci'on que ser queire fijar
    * @return servicioOfrecidoEntity actualziada
    * @throws BusinessLogicException si no cumple las rglas de negocio
    */
   public ServicioOfrecidoEntity updateServicioOfrecido(Long servicioId, ServicioOfrecidoEntity newService) throws BusinessLogicException
   {
        ServicioOfrecidoEntity old = null;//persistencia.findByName(newService.getNombre());
        String nombre = newService.getNombre();
        List<ServicioOfrecidoEntity> list = persistencia.findAll();
        for(ServicioOfrecidoEntity i: list)
        {
            if(i.getNombre().equals(nombre))
                old=i;
        }

        String tipo = newService.getTipo();

        if(old != null && old.getId()!=servicioId)  
        {
            throw new BusinessLogicException("Ya existe un servicio con ese nombre");
        }
        else if(!isInType(tipo))
        {
             throw new BusinessLogicException("El tipo del servicio ofrecido que se queire agregar, no se encuentra dentro de la oferta");   
        }
                
        else
        {
          
            newService= persistencia.update(newService);
        }

        return newService;
   }
   
   /**
    * Métodos que elimina un servicioOfrecido dado su id. 
    * @param servicioId identificador del servicio a eliminar.
    * @throws BusinessLogicException 
    */
   public void deleteServicioOfrecido(Long servicioId) throws BusinessLogicException
   {
       persistencia.delete(servicioId);
   }
}

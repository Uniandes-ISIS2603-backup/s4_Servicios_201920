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
    
   public   List<ServicioOfrecidoEntity> getServiciosOfrecidos()
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
    * Actualiza el servicio ofrecido con el id dada con la infroamci'on dada
    * @param servicioId identificador del servicio que se quiere actualizar.
    * @param servicioEntity informaci'on que ser queire fijar
    * @return servicioOfrecidoEntity actualziada
    * @throws BusinessLogicException si no cumple las rglas de negocio
    */
   public ServicioOfrecidoEntity updateServicioOfrecido(Long servicioId, ServicioOfrecidoEntity servicioEntity) throws BusinessLogicException
   {
        ServicioOfrecidoEntity newEntity = persistencia.update(servicioEntity);
        return newEntity;
   }
   
   public void deleteServicioOfrecido(Long servicioId) throws BusinessLogicException 
   {
       persistencia.delete(servicioId);
   }
}

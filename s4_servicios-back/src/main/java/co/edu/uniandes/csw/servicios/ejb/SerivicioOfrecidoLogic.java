/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.ejb;

import co.edu.uniandes.csw.servicios.entities.ServicioOfrecidoEntity;
import co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.servicios.persistence.ServicioOfrecidoPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Violeta Rodriguez
 */
@Stateless
public class SerivicioOfrecidoLogic 
{
    @Inject
    private ServicioOfrecidoPersistence persistencia;
    
    private final String[] tipos ={"Pintura", "Plomeria", "Electricidad", "Cerrajeria", "Ventaneria", "Carpinteria", "Aseo"};
    
    
    public ServicioOfrecidoEntity createServicioOfrecido(ServicioOfrecidoEntity newService) throws BusinessLogicException
    {
        // Validamos que no haya otro servicio con el mismo nnombre:
        ServicioOfrecidoEntity old = persistencia.findByName(newService.getNombre());
        String tipo = newService.getTipo();
        if(old == null && isInType(tipo))
        {
            newService=persistencia.create(newService);
        }
        else
        {
            newService=null;
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
}

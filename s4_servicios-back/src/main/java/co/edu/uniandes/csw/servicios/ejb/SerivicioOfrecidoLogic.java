/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.ejb;

import co.edu.uniandes.csw.servicios.entities.ServicioOfrecidoEntity;
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
    private ServicioOfrecidoPersistence persitencia;
    
    public ServicioOfrecidoEntity createService(ServicioOfrecidoEntity servicioOfecidoEntity)
    {
        // Validamo que no haya otro servicio con el mismo nnombre:
        
    }
}

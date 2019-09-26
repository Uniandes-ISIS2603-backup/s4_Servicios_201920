/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.resources;

import co.edu.uniandes.csw.servicios.dtos.ServicioOfrecidoDTO;
import co.edu.uniandes.csw.servicios.ejb.ServicioOfrecidoLogic;
import co.edu.uniandes.csw.servicios.entities.ServicioOfrecidoEntity;
import co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;

/**
 *Clase que implementa el recurso "ServicioOfrecido"
 * 
 * @author Violeta Rodríguez
 */@Path("servicioOfrecido")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ServicoOfrecidoResource 
{
    @Inject
    private ServicioOfrecidoLogic servicioLogic;
    
    
    /**
     * Crea un servicioOfrecido con la iformación dada
     * @param servicio servicio que se desea crear. 
     * @return rerona el serivcio que se ha almacenda en la base de datos con su espectivo ID. 
     * @throws BusinessLogicException  Lanza excepción cuando no se ha cumplido alguno de las reglas de negocio establecidas. 
     */
    @POST
    public ServicioOfrecidoDTO createServicioOfrecido(ServicioOfrecidoDTO servicio) throws BusinessLogicException {
    
        ServicioOfrecidoDTO newService = new ServicioOfrecidoDTO (servicioLogic.createServicioOfrecido(servicio.toEntity()));
        return newService;
    }
    
    @GET 
    public List<ServicioOfrecidoDTO> getServiciosOfrecidos() {
       
        List<ServicioOfrecidoDTO> listaServicioOfrecidos = toDTO(servicioLogic.getServiciosOfrecidos());
       
        return listaServicioOfrecidos;
    }
    
    
   private List<ServicioOfrecidoDTO> toDTO (List<ServicioOfrecidoEntity> entityList)
   {
        List<ServicioOfrecidoDTO> list = new ArrayList<>();
        for (ServicioOfrecidoEntity entity : entityList) {
            list.add(new ServicioOfrecidoDTO(entity));
        }
        return list;
       
       
   }
}

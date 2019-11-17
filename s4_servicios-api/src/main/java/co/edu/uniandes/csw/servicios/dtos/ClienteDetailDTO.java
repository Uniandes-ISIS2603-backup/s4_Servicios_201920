/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.dtos;

import co.edu.uniandes.csw.servicios.entities.ClienteEntity;
import co.edu.uniandes.csw.servicios.entities.SolicitudServicioEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Estudiante
 */
public class ClienteDetailDTO extends ClienteDTO implements Serializable{
    
    private List<SolicitudServicioDTO> servicios;
    
    public ClienteDetailDTO(){
        super();
    }
    
    public ClienteDetailDTO(ClienteEntity clienteEntity){
        super(clienteEntity);
        if(clienteEntity.getServicios() != null){
            servicios = new ArrayList<>();
            for(SolicitudServicioEntity servicio : clienteEntity.getServicios()){
                servicios.add(new SolicitudServicioDTO(servicio));
            }
        }
    }
    
    @Override
    public ClienteEntity toEntity(){
        ClienteEntity clienteEntity = super.toEntity();
        if(getServicios() != null){
            List<SolicitudServicioEntity> serviciosEntities = new ArrayList<>();
            for(SolicitudServicioDTO servicio : servicios){
                serviciosEntities.add(servicio.toEntity());
            }
            clienteEntity.setServicios(serviciosEntities);
        }
        
        return clienteEntity;
    }
    
    public void setServicios(List<SolicitudServicioDTO> pListaServicios){
        servicios = pListaServicios;
    }
    
    public List<SolicitudServicioDTO> getServicios(){
        return servicios;
    }
    
}
